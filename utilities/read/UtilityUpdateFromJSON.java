/************************************************
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 ************************************************/

package pokemans.utilities.read;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pokemans.utilities.write.WriteFiles;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;

public class UtilityUpdateFromJSON {

    public static void main(String[] args) {
        // new parser
        JSONParser jsonParser = new JSONParser();

        String fileName = GameMasterCheck.checkFile();

        // open file "pokeGameMaster.json"
        try (FileReader reader = new FileReader(fileName)) {
            initializeMoveUtilityClasses();

            ArrayList<PokemonUtility> pokemonUtilityList = new ArrayList<>();
            ArrayList<PokeFormsUtility> formsUtilityList = new ArrayList<>();
            ArrayList<TypeUtility> typeUtilityList = new ArrayList<>();
            ArrayList<Double> cpMultiplier = new ArrayList<>();
            String duplicateCheck;
            int formsCounter = 0, movesCounterCinematic = 1, movesCounterFast = 1;

            // Read JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            // read array in json file
            JSONArray pokeArray = (JSONArray) jsonObject.get("itemTemplate");

            for (Object obj : pokeArray) {
                JSONObject firstObject = (JSONObject) obj;
                duplicateCheck = (String) firstObject.get("templateId");

                if (duplicateCheck.contains("_POKEMON_") || duplicateCheck.contains("_MOVE_")) {

                    // read PokemonUtility info (starts at 32566, V001_Bulbasaur)
                    if (firstObject.containsKey("pokemon")) {
                        JSONObject objectData = (JSONObject) firstObject.get("pokemon");
                        ReadJSONInfo.readPokeInfo(objectData, duplicateCheck, pokemonUtilityList);
                    }

                    // read PvP move stats (starts at 12668, wrap)
                    else if (firstObject.containsKey("combatMove")) {
                        JSONObject objectData = (JSONObject) firstObject.get("combatMove");
                        if (duplicateCheck.endsWith("_FAST")) {
                            ReadJSONInfo.readToMoveClassFastPvp(objectData, duplicateCheck);
                        } else {
                            ReadJSONInfo.readToMoveClassCinematicPvp(objectData, duplicateCheck);
                        }
                    }

                    // read PvE move stats
                    else if (firstObject.containsKey("move")) {
                        JSONObject objectData = (JSONObject) firstObject.get("move");
                        if (duplicateCheck.endsWith("_FAST")) {
                            movesCounterFast = ReadJSONInfo.readToMoveClassFastPve(objectData, duplicateCheck, movesCounterFast);
                        } else {
                            movesCounterCinematic = ReadJSONInfo.readToMoveClassCinematicPve(objectData, duplicateCheck, movesCounterCinematic);
                            //System.out.println("Moves Cinematic (Index: " + (movesCounterCinematic - 1) + "): ");
                            //System.out.println(MovesCinematicUtility.printInfo((int) (movesCounterCinematic-1)));
                        }
                    }

                    // read Forms info (starts at 14837)
                    else if (firstObject.containsKey("formSettings")) {
                        JSONObject objectData = (JSONObject) firstObject.get("formSettings");
                        formsCounter = ReadJSONInfo.readFormsInfo(objectData, duplicateCheck, formsUtilityList, formsCounter);
                    }

                    // secondary, less quantity readings
                } else if (duplicateCheck.equals("PLAYER_LEVEL_SETTINGS") || duplicateCheck.contains("SMEARGLE_MOVES_SETTINGS") ||
                        duplicateCheck.startsWith("POKEMON_TYPE_")) {

                    // read Type info (starts at 20755)
                    if (firstObject.containsKey("typeEffective")) {
                        JSONObject objectData = (JSONObject) firstObject.get("typeEffective");
                        ReadJSONInfo.readTypeInfo(objectData, typeUtilityList);
                    }

                    // read Smeargle Moves (starts at 20905, just one entry)
                    else if (firstObject.containsKey("smeargleMovesSettings")) {
                        JSONObject objectData = (JSONObject) firstObject.get("smeargleMovesSettings");
                        SmeargleMovesUtility.setSmeargleQuickMoves(ReadJSONInfo.readArray(objectData, "quickMoves"));
                        SmeargleMovesUtility.setSmeargleCinematicMoves(ReadJSONInfo.readArray(objectData, "cinematicMoves"));
                    }

                    // read player level (cpMultiplier) info (starts at 20699, just one entry)
                    if (firstObject.containsKey("playerLevel")) {
                        JSONObject objectData = (JSONObject) firstObject.get("playerLevel");
                        cpMultiplier.addAll(ReadJSONInfo.readCPMultiplier(objectData, cpMultiplier));
                    }
                }
            }
            // auto-write Classes
            WriteFiles.writePokedexEnum(pokemonUtilityList);
            WriteFiles.writeMovesCinematicEnum();
            WriteFiles.writeMovesFastEnum();
            WriteFiles.writeTypeEnum(typeUtilityList);
            WriteFiles.writeCPMultiplierEnum(cpMultiplier);
            WriteFiles.writeGameMasterCheckClass(fileName);
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
        // JOptionPane.showMessageDialog(null, "PoGo Game Master reading complete!");
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