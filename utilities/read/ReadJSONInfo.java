/************************************************
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 ************************************************/

package pokemans.utilities.read;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ReadJSONInfo {
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

        if (str.equals(""))
            return -1;

        return parseInt(str.substring(1, 4).replaceAll("^0+", ""));
    }
    private static <E> E readObjectDependingOnInput(JSONObject objectData, String stat, Class<E> type) {
        if (type == Double.class) {
            double doubleRead;

            doubleRead = (Double) objectData.get(stat);
            return type.cast(doubleRead);

        } else if (type == Integer.class) {
            long longRead;
            int intRead;

            longRead = (Long) objectData.get(stat);
            intRead = (int) longRead;
            return type.cast(intRead);

        } else {
            long longRead;
            byte byteRead;

            longRead = (Long) objectData.get(stat);
            byteRead = (byte) longRead;
            return type.cast(byteRead);
        }
    }
    static ArrayList<String> readArray(JSONObject arrayObject, String arrayKey) {
        ArrayList<String> contents = new ArrayList<>();
        if (!(arrayKey.equals("empty"))) {
            JSONArray arrayContents = (JSONArray) arrayObject.get(arrayKey);
            contents.addAll(arrayContents);
        } else {
            contents.add("");
        }
        return contents;
    }
    private static ArrayList<String> readKeysInArray(JSONObject arrayObject, String keyValueNeeded) {
        ArrayList<String> contents = new ArrayList<>();
        JSONArray array = (JSONArray) arrayObject.get(keyValueNeeded);
        for (Object o : array) {
            JSONObject jsonObject = (JSONObject) o;
            contents.add((String) jsonObject.get("form"));
        }
        return contents;
    }
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }


    // read pokemans
    static void readPokeInfo(JSONObject objectData, String duplicateCheck, ArrayList<PokemonUtility> pokelist) {
        boolean isLastInPoGoGen = false;
        String myPokeForm, myPokeName, myPokeFamily;
        int myGenNum = 0;
        ArrayList<String> myPokeType = new ArrayList<>(),
                myPokeQuickMoves = new ArrayList<>(), myPokeCinematicMoves = new ArrayList<>(),
                myPokeEliteQuickMoves = new ArrayList<>(), myPokeEliteCinematicMoves = new ArrayList<>(),
                myPokeMovesShadowCinematic = new ArrayList<>(), myPokeMovesPurifiedCinematic = new ArrayList<>();
        JSONObject stats = (JSONObject) objectData.get("stats");

        int intMyDexNum = extractNumber(duplicateCheck);

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
            if ((myGenNum != parseInt(pokelist.get(pokelist.size() - 1).getPokeGenNum())) &&
                    (!pokelist.get(pokelist.size() - 1).getIsLastInPoGoGen())) {
                pokelist.get(pokelist.size() - 1).setIsLastInPoGoGen(true);
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

        if (objectData.containsKey("quickMoves") || objectData.containsKey("cinematicMoves")) {
            myPokeQuickMoves.addAll(0, readArray(objectData, "quickMoves"));
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

        myPokeFamily = (String) objectData.get("familyId");

        String stringMyDexNum = String.valueOf(intMyDexNum);
        String stringMyGenNum = String.valueOf(myGenNum);

        pokelist.add(new PokemonUtility(isLastInPoGoGen, myPokeAttack, myPokeDefense, myPokeStamina, stringMyDexNum,
                stringMyGenNum, myPokeName, myPokeForm, myPokeFamily, myPokeType, myPokeQuickMoves,
                myPokeCinematicMoves, myPokeEliteQuickMoves, myPokeEliteCinematicMoves, myPokeMovesShadowCinematic,
                myPokeMovesPurifiedCinematic));
    }

    // read moves
    static void readToMoveClassCinematicPvp(JSONObject objectData, String duplicateCheck) {
        MovesCinematicUtility.setMoveNum(extractNumber(duplicateCheck));
        MovesCinematicUtility.setMoveName((String) objectData.get("uniqueId"));
        MovesCinematicUtility.setMoveType((String) objectData.get("type"));
        if (objectData.containsKey("power")) {
            MovesCinematicUtility.setPvpPower(readObjectDependingOnInput(objectData, "power", Double.class));
        } else {
            MovesCinematicUtility.setPvpPower(0.0);
        }
        if (objectData.containsKey("energyDelta")) {
            MovesCinematicUtility.setPvpEnergy(readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
        } else {
            MovesCinematicUtility.setPvpEnergy((byte) 0);
        }

        // if PvP Buffs
        if (objectData.containsKey("buffs")) {
            JSONObject buffsData = (JSONObject) objectData.get("buffs");

            if (buffsData.containsKey("attackerAttackStatStageChange")) {
                MovesCinematicUtility.setPvpBuffAttackerAttack(readObjectDependingOnInput(buffsData,
                        "attackerAttackStatStageChange", Byte.class));
            } else {
                MovesCinematicUtility.setPvpBuffAttackerAttack((byte) 0);
            }

            if (buffsData.containsKey("attackerDefenseStatStageChange")) {
                MovesCinematicUtility.setPvpBuffAttackerDefense(readObjectDependingOnInput(buffsData,
                        "attackerDefenseStatStageChange", Byte.class));
            } else {
                MovesCinematicUtility.setPvpBuffAttackerDefense((byte) 0);
            }

            if (buffsData.containsKey("targetAttackStatStageChange")) {
                MovesCinematicUtility.setPvpBuffTargetAttack(readObjectDependingOnInput(buffsData,
                        "targetAttackStatStageChange", Byte.class));
            } else {
                MovesCinematicUtility.setPvpBuffTargetAttack((byte) 0);
            }

            if (buffsData.containsKey("targetDefenseStatStageChange")) {
                MovesCinematicUtility.setPvpBuffTargetDefense(readObjectDependingOnInput(buffsData,
                        "targetDefenseStatStageChange", Byte.class));
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
            MovesFastUtility.setPvpEnergy(readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
        } else {
            MovesFastUtility.setPvpEnergy((byte) 0);
        }
        if (objectData.containsKey("durationTurns")) {
            MovesFastUtility.setPvpTurns(readObjectDependingOnInput(objectData, "durationTurns", Byte.class));
        } else {
            MovesFastUtility.setPvpTurns((byte) 0);
        }
    }
    static int readToMoveClassCinematicPve(JSONObject objectData, String duplicateCheck, int movesCounter) {
        if (MovesCinematicUtility.getMoveNum().contains(extractNumber(duplicateCheck))) ;
        {
            if (objectData.containsKey("power")) {
                MovesCinematicUtility.setPvePower(readObjectDependingOnInput(objectData, "power", Double.class));
            } else {
                MovesCinematicUtility.setPvePower(0.0);
            }
            if (objectData.containsKey("energyDelta")) {
                MovesCinematicUtility.setPveEnergy(readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
            } else {
                MovesCinematicUtility.setPveEnergy((byte) 0);
            }
            MovesCinematicUtility.setPveDurationMs(readObjectDependingOnInput(objectData, "durationMs", Integer.class));
            MovesCinematicUtility
                    .setPveDamageWindowStartMs(readObjectDependingOnInput(objectData, "damageWindowStartMs", Integer.class));
            MovesCinematicUtility.setPveDamageWindowEndMs(readObjectDependingOnInput(objectData, "damageWindowEndMs", Integer.class));

            movesCounter++;
            return movesCounter;
        }
    }
    static int readToMoveClassFastPve(JSONObject objectData, String duplicateCheck, int movesCounter) {
        if (MovesFastUtility.getMoveNum().contains(extractNumber(duplicateCheck))) ;
        {
            if (objectData.containsKey("power")) {
                MovesFastUtility.setPvePower(readObjectDependingOnInput(objectData, "power", Double.class));
            } else {
                MovesFastUtility.setPvePower(0.0);
            }
            if (objectData.containsKey("energyDelta")) {
                MovesFastUtility.setPveEnergy(readObjectDependingOnInput(objectData, "energyDelta", Byte.class));
            } else {
                MovesFastUtility.setPveEnergy((byte) 0);
            }
            MovesFastUtility.setPveDurationMs(readObjectDependingOnInput(objectData, "durationMs", Integer.class));
            MovesFastUtility
                    .setPveDamageWindowStartMs(readObjectDependingOnInput(objectData, "damageWindowStartMs", Integer.class));
            MovesFastUtility.setPveDamageWindowEndMs(readObjectDependingOnInput(objectData, "damageWindowEndMs", Integer.class));

            movesCounter++;
            return movesCounter;
        }
    }


    // read forms
    static int readFormsInfo(JSONObject objectData, String
            duplicateCheck, ArrayList<PokeFormsUtility> formsList, int formsCouner) {
        if (objectData.containsKey("forms")) {
            ArrayList<String> formName = new ArrayList<>();
            int formDexNum = extractNumber(duplicateCheck);
            formName.addAll(0, readKeysInArray(objectData, "forms"));
            formsList.add(new PokeFormsUtility(formDexNum, formName));
        }

        formsCouner++;
        return formsCouner;
    }

    // read types
    static void readTypeInfo(JSONObject objectData, ArrayList<TypeUtility> typeUtilityList) {
        String type = (String) objectData.get("attackType");

        JSONArray arrayContents = (JSONArray) objectData.get("attackScalar");
        ArrayList<Double> typeEffectiveness = new ArrayList<Double>(arrayContents);

        typeUtilityList.add(new TypeUtility(type, typeEffectiveness));
    }

    // read player level (cpMultiplier)
    static ArrayList<Double> readCPMultiplier(JSONObject objectData, ArrayList<Double> cpMultiplier) {
        ArrayList<Double> localCPMultiplierList = new ArrayList<>();
        JSONArray arrayContents = (JSONArray) objectData.get("cpMultiplier");

        localCPMultiplierList.addAll(arrayContents);
        return localCPMultiplierList;
    }
}
