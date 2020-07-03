/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */
package pokemans.utilities.read;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pokemans.utilities.write.WriteFiles;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class UtilityUpdateFromJSONDev {

  public static void main(String[] args) {
    // new parser
    JSONParser jsonParser = new JSONParser();

    String fileName =
        GameMasterCheck.checkFile("");

    // open & read Game Master
    try (FileReader reader = new FileReader(fileName)) {
      initializeMoveUtilityClasses();

      List<PokemonUtility> pokemonUtilityList = new ArrayList<>();
      List<PokeFormsUtility> formsUtilityList = new ArrayList<>();
      List<TypeUtility> typeUtilityList = new ArrayList<>();
      List<BigDecimal> cpMultiplier = new ArrayList<>();
      TreeMap<String, Double> battleMultipliers = new TreeMap<>();
      TreeMap<String, Double> combatMultipliers = new TreeMap<>();
      TreeMap<String, List<Double>> combatStageMultipliers = new TreeMap<>();
      TreeMap<String, Double> weatherMultipliers = new TreeMap<>();
      String duplicateCheck;
      int formsCounter = 0, movesCounterCinematic = 1, movesCounterFast = 1;

      // for devGameMaster
      // Read JSON file
      JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
      // read array in json file
      JSONArray pokeArray = (JSONArray) jsonObject.get("itemTemplate");

      for (Object obj : pokeArray) {
        JSONObject firstObject = (JSONObject) obj;
        duplicateCheck = (String) firstObject.get("templateId");

        if (duplicateCheck.contains("_POKEMON_")
            || duplicateCheck.contains("_MOVE_")
            || duplicateCheck.startsWith("POKEMON_TYPE_")) {

          // read PokemonUtility info (starts at 32566, V001_Bulbasaur)
          if (firstObject.containsKey("pokemon")) {
            JSONObject objectData = (JSONObject) firstObject.get("pokemon");
            ReadJSONInfoMiners.readPokeInfo(objectData, duplicateCheck, pokemonUtilityList);
          }

          // read PvP move stats (starts at 12668, wrap)
          else if (firstObject.containsKey("combatMove")) {
            JSONObject objectData = (JSONObject) firstObject.get("combatMove");
            if (duplicateCheck.endsWith("_FAST") || duplicateCheck.endsWith("FAST_BLASTOISE")) {
              ReadJSONInfoMiners.readToMoveClassFastPvp(objectData, duplicateCheck);
            } else {
              ReadJSONInfoMiners.readToMoveClassCinematicPvp(objectData, duplicateCheck);
            }
          }

          // read PvE move stats
          else if (firstObject.containsKey("move")) {
            JSONObject objectData = (JSONObject) firstObject.get("move");
            if (duplicateCheck.endsWith("_FAST") || duplicateCheck.endsWith("FAST_BLASTOISE")) {
              movesCounterFast =
                  ReadJSONInfoMiners.readToMoveClassFastPve(
                      objectData, duplicateCheck, movesCounterFast);
            } else {
              movesCounterCinematic =
                  ReadJSONInfoMiners.readToMoveClassCinematicPve(
                      objectData, duplicateCheck, movesCounterCinematic);
            }
          }

          // read Forms info (starts at 14837)
          else if (firstObject.containsKey("formSettings")) {
            JSONObject objectData = (JSONObject) firstObject.get("formSettings");
            formsCounter =
                ReadJSONInfoMiners.readFormsInfo(
                    objectData, duplicateCheck, formsUtilityList, formsCounter);
          }

          // read Type info (starts at 20755)
          else if (firstObject.containsKey("typeEffective")) {
            JSONObject objectData = (JSONObject) firstObject.get("typeEffective");
            ReadJSONInfoMiners.readTypeInfo(objectData, typeUtilityList);
          }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // secondary, less quantity readings

        switch (duplicateCheck) {
          case "BATTLE_SETTINGS":
            firstObject = (JSONObject) firstObject.get("battleSettings");
            battleMultipliers.putAll(
                ReadJSONInfoMiners.readBattleMultipliers(
                    firstObject, duplicateCheck, battleMultipliers));
            break;
          case "COMBAT_SETTINGS":
            firstObject = (JSONObject) firstObject.get("combatSettings");
            combatMultipliers.putAll(
                ReadJSONInfoMiners.readBattleMultipliers(
                    firstObject, duplicateCheck, combatMultipliers));
            break;
          case "COMBAT_STAT_STAGE_SETTINGS":
            firstObject = (JSONObject) firstObject.get("combatStatStageSettings");
            combatStageMultipliers.putAll(
                ReadJSONInfoMiners.readCombatStageSettings(
                    firstObject, duplicateCheck, combatStageMultipliers));
            break;
          case "PLAYER_LEVEL_SETTINGS":
            firstObject = (JSONObject) firstObject.get("playerLevel");
            cpMultiplier.addAll(ReadJSONInfoMiners.readCPMultiplier(firstObject, cpMultiplier));
            break;
          case "SMEARGLE_MOVES_SETTINGS":
            firstObject = (JSONObject) firstObject.get("smeargleMovesSettings");
            SmeargleMovesUtility.setSmeargleQuickMoves(
                ReadJSONInfoMiners.readArray(firstObject, "quickMoves"));
            SmeargleMovesUtility.setSmeargleCinematicMoves(
                ReadJSONInfoMiners.readArray(firstObject, "cinematicMoves"));
            break;
          case "WEATHER_BONUS_SETTINGS":
            firstObject = (JSONObject) firstObject.get("weatherBonusSettings");
            weatherMultipliers.putAll(
                ReadJSONInfoMiners.readBattleMultipliers(
                    firstObject, duplicateCheck, weatherMultipliers));
            break;
        }
      }
      // auto-write Classes
      // writeFiles(pokemonUtilityList, typeUtilityList, cpMultiplier, battleMultipliers,
      // combatMultipliers, combatStageMultipliers, weatherMultipliers, fileName);

    } catch (NullPointerException e) {
      System.out.println("\nNull Pointer Exception!\n");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("\nIO Exception!\n");
      e.printStackTrace();
    } catch (ParseException e) {
      System.out.println("\nParse Exception!\n");
      e.printStackTrace();
    }
  }

  private static void writeFiles(
      List<PokemonUtility> pokemonUtilityList,
      List<TypeUtility> typeUtilityList,
      List<BigDecimal> cpMultiplier,
      TreeMap<String, Double> battleMultipliers,
      TreeMap<String, Double> combatMultipliers,
      TreeMap<String, List<Double>> combatStageMultipliers,
      TreeMap<String, Double> weatherMultipliers,
      String fileName) {
    WriteFiles.writePokedexEnum(pokemonUtilityList);
    WriteFiles.writeMovesCinematicEnum();
    WriteFiles.writeMovesFastEnum();
    WriteFiles.writeTypeEnum(typeUtilityList);
    WriteFiles.writeCPMultiplierEnum(cpMultiplier);
    WriteFiles.writeBattleMultipliers(battleMultipliers);
    WriteFiles.writeCombatMultipliers(combatMultipliers, combatStageMultipliers);
    WriteFiles.writeWeatherMultipliers(weatherMultipliers);
    WriteFiles.writeGameMasterCheckClass(fileName);
  }

  private static void initializeMoveUtilityClasses() {
    MovesCinematicUtility.setMoveNum(0);
    MovesCinematicUtility.setMoveName("");
    MovesCinematicUtility.setMoveType("");
    MovesCinematicUtility.setPvePower((double) 0);
    MovesCinematicUtility.setPveEnergy((byte) 0);
    MovesCinematicUtility.setPveDurationMs(0);
    MovesCinematicUtility.setPveDamageWindowStartMs(0);
    MovesCinematicUtility.setPveDamageWindowEndMs(0);
    MovesCinematicUtility.setPvpPower((double) 0);
    MovesCinematicUtility.setPvpEnergy((byte) 0);
    MovesCinematicUtility.setPvpBuffChance((double) 0);
    MovesCinematicUtility.setPvpBuffAttackerAttack((byte) 0);
    MovesCinematicUtility.setPvpBuffAttackerDefense((byte) 0);
    MovesCinematicUtility.setPvpBuffTargetAttack((byte) 0);
    MovesCinematicUtility.setPvpBuffTargetDefense((byte) 0);

    MovesFastUtility.setMoveNum(0);
    MovesFastUtility.setMoveName("");
    MovesFastUtility.setMoveType("");
    MovesFastUtility.setPvePower((double) 0);
    MovesFastUtility.setPveEnergy((byte) 0);
    MovesFastUtility.setPveDurationMs(0);
    MovesFastUtility.setPveDamageWindowStartMs(0);
    MovesFastUtility.setPveDamageWindowEndMs(0);
    MovesFastUtility.setPvpPower((double) 0);
    MovesFastUtility.setPvpEnergy((byte) 0);
    MovesFastUtility.setPvpTurns((byte) 0);
  }
}
