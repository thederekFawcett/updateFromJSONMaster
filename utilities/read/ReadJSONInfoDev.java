/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.utilities.read;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;

public class ReadJSONInfoDev {
  // functionality
  private static int extractNumber(String str) {
    // Remove every non-digit number
    str = str.replaceAll("[^\\d]", "");

    // Remove all white spaces within
    str = str.replaceAll(" ", "");

    // Remove extra spaces from the beginning
    // and the ending of the string
    str = str.trim();

    // Remove all the consecutive white
    str = str.replaceAll(" +", "");

    if (str.equals("")) return -1;

    return parseInt(str.substring(1, 4).replaceAll("^0+", ""));
  }

  private static <E> E readObjectDependingOnInput(
      JSONObject objectData, String stat, Class<E> type) {
    if (type == Double.class) {
      return type.cast(objectData.get(stat));

    } else if (type == Integer.class) {
      long longRead = (Long) objectData.get(stat);
      return type.cast((int) longRead);

    } else if (type == Long.class) {
      return type.cast(objectData.get(stat));

    } else {
      long longRead = (Long) objectData.get(stat);
      return type.cast((byte) longRead);
    }
  }

  static List<String> readArray(JSONObject arrayObject, String arrayKey) {
    JSONArray arrayContents = (JSONArray) arrayObject.get(arrayKey);
    return new ArrayList<>(arrayContents);
  }

  public static boolean isBetween(int x, int lower, int upper) {
    return lower <= x && x <= upper;
  }

  // read pokemans
  static void readPokeInfo(
      JSONObject objectData, String duplicateCheck, List<PokemonUtility> pokemonUtilityList) {
    boolean isLastInPoGoGen = false;
    String myPokeForm, myPokeName, myPokeFamily;
    int myGenNum = 0;
    List<String> myPokeType = new ArrayList<>(),
        myPokeQuickMoves = new ArrayList<>(),
        myPokeCinematicMoves = new ArrayList<>(),
        myPokeEliteQuickMoves = new ArrayList<>(),
        myPokeEliteCinematicMoves = new ArrayList<>(),
        myPokeMovesShadowCinematic = new ArrayList<>(),
        myPokeMovesPurifiedCinematic = new ArrayList<>();
    myPokeFamily = (String) objectData.get("familyId");
    int intMyDexNum = extractNumber(duplicateCheck);

    JSONObject stats = (JSONObject) objectData.get("stats");

    if (isBetween(intMyDexNum, 1, 151)) { // gen1
      myGenNum = 1;
    } else if (isBetween(intMyDexNum, 152, 251)) { // gen 2
      myGenNum = 2;
    } else if (isBetween(intMyDexNum, 252, 386)) { // gen 3
      myGenNum = 3;
    } else if (isBetween(intMyDexNum, 387, 493)) { // gen 4
      myGenNum = 4;
    } else if (isBetween(intMyDexNum, 494, 649)) { // gen 5
      myGenNum = 5;
    } else if (isBetween(intMyDexNum, 650, 721)) { // gen 6
      myGenNum = 6;
    } else if (isBetween(intMyDexNum, 722, 809)) { // gen 7
      myGenNum = 7;
    } else if (intMyDexNum >= 810) { // gen 8
      myGenNum = 8;
    }

    if (intMyDexNum != 1) {
      if ((myGenNum
              != parseInt(pokemonUtilityList.get(pokemonUtilityList.size() - 1).getPokeGenNum()))
          && (!pokemonUtilityList.get(pokemonUtilityList.size() - 1).getIsLastInPoGoGen())) {
        pokemonUtilityList.get(pokemonUtilityList.size() - 1).setIsLastInPoGoGen(true);
      }
    }

    if (objectData.containsKey("form")) {
      myPokeForm = (String) objectData.get("form");
      myPokeName = (String) objectData.get("form");
    } else {
      myPokeForm = "";
      myPokeName = (String) objectData.get("uniqueId");
    }
    int myPokeAttack = readObjectDependingOnInput(stats, "baseAttack", Integer.class);
    int myPokeDefense = readObjectDependingOnInput(stats, "baseDefense", Integer.class);
    int myPokeStamina = readObjectDependingOnInput(stats, "baseStamina", Integer.class);
    myPokeType.add((String) objectData.get("type1"));
    if (objectData.containsKey("type2")) {
      myPokeType.add((String) objectData.get("type2"));
    }
    if (objectData.containsKey("quickMoves")) {
      myPokeQuickMoves.addAll(0, readArray(objectData, "quickMoves"));
    }
    if (objectData.containsKey("cinematicMoves")) {
      myPokeCinematicMoves.addAll(0, readArray(objectData, "cinematicMoves"));
    }
    if (intMyDexNum == 235) {
      myPokeQuickMoves.addAll(0, SmeargleMovesUtility.getSmeargleQuickMoves());
      myPokeCinematicMoves.addAll(0, SmeargleMovesUtility.getSmeargleCinematicMoves());
    }
    if (objectData.containsKey("eliteQuickMove")) {
      myPokeEliteQuickMoves.addAll(0, readArray(objectData, "eliteQuickMove"));
    }
    if (objectData.containsKey("eliteCinematicMove")) {
      myPokeEliteCinematicMoves.addAll(0, readArray(objectData, "eliteCinematicMove"));
    }
    if (objectData.containsKey("shadow")) {
      JSONObject shadowData = (JSONObject) objectData.get("shadow");
      myPokeMovesShadowCinematic.add((String) shadowData.get("shadowChargeMove"));
      myPokeMovesPurifiedCinematic.add((String) shadowData.get("purifiedChargeMove"));
    }

    String stringMyDexNum = String.valueOf(intMyDexNum);
    String stringMyGenNum = String.valueOf(myGenNum);

    pokemonUtilityList.add(
        new PokemonUtility(
            isLastInPoGoGen,
            myPokeAttack,
            myPokeDefense,
            myPokeStamina,
            stringMyDexNum,
            stringMyGenNum,
            myPokeName,
            myPokeForm,
            myPokeFamily,
            myPokeType,
            myPokeQuickMoves,
            myPokeCinematicMoves,
            myPokeEliteQuickMoves,
            myPokeEliteCinematicMoves,
            myPokeMovesShadowCinematic,
            myPokeMovesPurifiedCinematic));
  }

  // read moves
  static void readToMoveClassCinematicPvp(JSONObject objectData, String duplicateCheck) {
    MovesCinematicUtility.setMoveNum(extractNumber(duplicateCheck));
    MovesCinematicUtility.setMoveName((String) objectData.get("uniqueId"));
    MovesCinematicUtility.setMoveType((String) objectData.get("type"));
    if (objectData.containsKey("power")) {
      MovesCinematicUtility.setPvpPower(
          readObjectDependingOnInput(objectData, "power", Double.class));
    } else {
      MovesCinematicUtility.setPvpPower(0.0);
    }
    if (objectData.containsKey("energyDelta")) {
      MovesCinematicUtility.setPvpEnergy(
          readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
    } else {
      MovesCinematicUtility.setPvpEnergy((byte) 0);
    }

    // if PvP Buffs
    if (objectData.containsKey("buffs")) {
      JSONObject buffsData = (JSONObject) objectData.get("buffs");

      if (buffsData.containsKey("attackerAttackStatStageChange")) {
        MovesCinematicUtility.setPvpBuffAttackerAttack(
            readObjectDependingOnInput(buffsData, "attackerAttackStatStageChange", Byte.class));
      } else {
        MovesCinematicUtility.setPvpBuffAttackerAttack((byte) 0);
      }

      if (buffsData.containsKey("attackerDefenseStatStageChange")) {
        MovesCinematicUtility.setPvpBuffAttackerDefense(
            readObjectDependingOnInput(buffsData, "attackerDefenseStatStageChange", Byte.class));
      } else {
        MovesCinematicUtility.setPvpBuffAttackerDefense((byte) 0);
      }

      if (buffsData.containsKey("targetAttackStatStageChange")) {
        MovesCinematicUtility.setPvpBuffTargetAttack(
            readObjectDependingOnInput(buffsData, "targetAttackStatStageChange", Byte.class));
      } else {
        MovesCinematicUtility.setPvpBuffTargetAttack((byte) 0);
      }

      if (buffsData.containsKey("targetDefenseStatStageChange")) {
        MovesCinematicUtility.setPvpBuffTargetDefense(
            readObjectDependingOnInput(buffsData, "targetDefenseStatStageChange", Byte.class));
      } else {
        MovesCinematicUtility.setPvpBuffTargetDefense((byte) 0);
      }

      MovesCinematicUtility.setPvpBuffChance((Double) buffsData.get("buffActivationChance"));

    } else {
      MovesCinematicUtility.setPvpBuffChance(0.0);
      MovesCinematicUtility.setPvpBuffAttackerAttack((byte) 0);
      MovesCinematicUtility.setPvpBuffAttackerDefense((byte) 0);
      MovesCinematicUtility.setPvpBuffTargetAttack((byte) 0);
      MovesCinematicUtility.setPvpBuffTargetDefense((byte) 0);
    }
  }

  static void readToMoveClassFastPvp(JSONObject objectData, String duplicateCheck) {
    MovesFastUtility.setMoveNum(extractNumber(duplicateCheck));
    MovesFastUtility.setMoveName((String) objectData.get("uniqueId"));
    MovesFastUtility.setMoveType((String) objectData.get("type"));
    if (objectData.containsKey("power")) {
      MovesFastUtility.setPvpPower(readObjectDependingOnInput(objectData, "power", Double.class));
    } else {
      MovesFastUtility.setPvpPower(0.0);
    }
    if (objectData.containsKey("energyDelta")) {
      MovesFastUtility.setPvpEnergy(
          readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
    } else {
      MovesFastUtility.setPvpEnergy((byte) 0);
    }
    if (objectData.containsKey("durationTurns")) {
      if (!objectData.get("uniqueId").equals("TRANSFORM_FAST")) {
        MovesFastUtility.setPvpTurns(
            (byte) (readObjectDependingOnInput(objectData, "durationTurns", Byte.class) + 1));
      } else {
        MovesFastUtility.setPvpTurns(
            (byte) (readObjectDependingOnInput(objectData, "durationTurns", Byte.class) + 4));
      }
    } else {
      MovesFastUtility.setPvpTurns((byte) 1);
    }
  }

  static int readToMoveClassCinematicPve(
      JSONObject objectData, String duplicateCheck, int movesCounter) {
    // if (MovesCinematicUtility.getMoveNum().contains(extractNumber(duplicateCheck))) {
    if (objectData.containsKey("power")) {
      MovesCinematicUtility.setPvePower(
          readObjectDependingOnInput(objectData, "power", Double.class));
    } else {
      MovesCinematicUtility.setPvePower(0.0);
    }
    if (objectData.containsKey("energyDelta")) {
      MovesCinematicUtility.setPveEnergy(
          readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
    } else {
      MovesCinematicUtility.setPveEnergy((byte) 0);
    }
    MovesCinematicUtility.setPveDurationMs(
        readObjectDependingOnInput(objectData, "durationMs", Integer.class));
    MovesCinematicUtility.setPveDamageWindowStartMs(
        readObjectDependingOnInput(objectData, "damageWindowStartMs", Integer.class));
    MovesCinematicUtility.setPveDamageWindowEndMs(
        readObjectDependingOnInput(objectData, "damageWindowEndMs", Integer.class));

    movesCounter++;
    return movesCounter;
    // }
    // return movesCounter;
  }

  static int readToMoveClassFastPve(
      JSONObject objectData, String duplicateCheck, int movesCounter) {
    // if (MovesFastUtility.getMoveNum().contains(extractNumber(duplicateCheck))) {
    if (objectData.containsKey("power")) {
      MovesFastUtility.setPvePower(readObjectDependingOnInput(objectData, "power", Double.class));
    } else {
      MovesFastUtility.setPvePower(0.0);
    }
    if (objectData.containsKey("energyDelta")) {
      MovesFastUtility.setPveEnergy(
          readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
    } else {
      MovesFastUtility.setPveEnergy((byte) 0);
    }
    MovesFastUtility.setPveDurationMs(
        readObjectDependingOnInput(objectData, "durationMs", Integer.class));
    MovesFastUtility.setPveDamageWindowStartMs(
        readObjectDependingOnInput(objectData, "damageWindowStartMs", Integer.class));
    MovesFastUtility.setPveDamageWindowEndMs(
        readObjectDependingOnInput(objectData, "damageWindowEndMs", Integer.class));

    movesCounter++;
    return movesCounter;
    // }
    // return movesCounter;
  }

  // read forms
  static int readFormsInfo(
      JSONObject objectData,
      String duplicateCheck,
      List<PokeFormsUtility> formsList,
      int formsCouner) {
    if (objectData.containsKey("forms")) {
      List<String> formName = readArray(objectData, "forms");
      int formDexNum = extractNumber(duplicateCheck);
      formsList.add(new PokeFormsUtility(formDexNum, formName));
    }

    formsCouner++;
    return formsCouner;
  }

  // read types
  static void readTypeInfo(JSONObject objectData, List<TypeUtility> typeUtilityList) {
    String type = (String) objectData.get("attackType");

    JSONArray arrayContents = (JSONArray) objectData.get("attackScalar");
    List<Double> typeEffectiveness = new ArrayList<Double>(arrayContents);

    typeUtilityList.add(new TypeUtility(type, typeEffectiveness));
  }

  // read player level (cpMultiplier)
  static List<BigDecimal> readCPMultiplier(JSONObject objectData, List<BigDecimal> cpMultiplier) {
    JSONArray arrayContents = (JSONArray) objectData.get("cpMultiplier");
    return new ArrayList<>(arrayContents);
  }

  // read battle multipliers
  static TreeMap<String, Double> readBattleMultipliers(
      JSONObject objectData, String duplicateCheck, TreeMap<String, Double> battleMultipliers) {
    objectData
        .keySet()
        .forEach(
            keyStr -> {
              if (objectData.get(keyStr).getClass().equals(Double.class)) {
                battleMultipliers.put(
                    keyStr.toString(),
                    readObjectDependingOnInput(objectData, keyStr.toString(), Double.class));
              } else if (objectData.get(keyStr).getClass().equals(Long.class)) {
                battleMultipliers.put(
                    keyStr.toString(),
                    readObjectDependingOnInput(objectData, keyStr.toString(), Long.class)
                        .doubleValue());
              } else if (objectData.get(keyStr).getClass().equals(Byte.class)) {
                battleMultipliers.put(
                    keyStr.toString(),
                    readObjectDependingOnInput(objectData, keyStr.toString(), Byte.class)
                        .doubleValue());
              }
            });
    return battleMultipliers;
  }

  // read battle multipliers
  static TreeMap<String, List<Double>> readCombatStageSettings(
      JSONObject objectData,
      String duplicateCheck,
      TreeMap<String, List<Double>> battleMultipliers) {
    objectData
        .keySet()
        .forEach(
            keyStr -> {
              if (objectData.get(keyStr).getClass().equals(Long.class)) {
                Long minimumStatStage = (Long) objectData.get(keyStr.toString());
                List<Double> minimumStatStageList = new ArrayList<>();
                minimumStatStageList.add((double) minimumStatStage);
                battleMultipliers.put(keyStr.toString(), minimumStatStageList);

              } else {
                JSONArray arrayContents = (JSONArray) objectData.get(keyStr.toString());
                List<Double> attackBuffMultiplierList = new ArrayList<>(arrayContents);
                battleMultipliers.put(keyStr.toString(), attackBuffMultiplierList);
              }
            });
    return battleMultipliers;
  }
}
