/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.utilities.read;

import java.util.ArrayList;

public class MovesFastUtility {

  // Moves Info
  private static final ArrayList<Integer> moveNum = new ArrayList<>();
  private static final ArrayList<String> moveName = new ArrayList<>();
  private static final ArrayList<String> moveType = new ArrayList<>();

  // PvE
  private static final ArrayList<Double> pvePower = new ArrayList<>();
  private static final ArrayList<Integer> pveDurationMs = new ArrayList<>();
  private static final ArrayList<Integer> pveDamageWindowStartMs = new ArrayList<>();
  private static final ArrayList<Integer> pveDamageWindowEndMs = new ArrayList<>();
  private static final ArrayList<Byte> pveEnergy = new ArrayList<>();

  // PvP
  private static final ArrayList<Double> pvpPower = new ArrayList<>();
  private static final ArrayList<Byte> pvpEnergy = new ArrayList<>();
  private static final ArrayList<Byte> pvpTurns = new ArrayList<>();

  public MovesFastUtility(
      int moveNum,
      String moveName,
      String moveType,
      Double pvePower,
      Byte pveEnergy,
      int getPveDamageWindowStartMs,
      int getPveDamageWindowEndMs,
      Double pvpPower,
      Byte pvpEnergy,
      Byte pvpTurns) {

    setMoveNum(moveNum);
    setMoveName(moveName);
    setMoveType(moveType);
    setPvePower(pvePower);
    setPveEnergy(pveEnergy);
    setPveDamageWindowStartMs(getPveDamageWindowStartMs);
    setPveDamageWindowEndMs(getPveDamageWindowEndMs);
    setPvpPower(pvpPower);
    setPvpEnergy(pvpEnergy);
    setPvpTurns(pvpTurns);
  }

  public static String printInfo(int counter) {
    return "\nQuick Moves---------------------------------"
        + "\nMove Num: "
        + getMoveNum().get(counter)
        + "\nMove Name: "
        + getMoveName().get(counter)
        + "\nMove Type: "
        + getMoveType().get(counter)
        + "\n"
        + "\nPvE Power: "
        + getPvePower().get(counter)
        + "\nPvE Energy: "
        + getPveEnergy().get(counter)
        + "\nPvE Duration ms: "
        + getPveDurationMs().get(counter)
        + "\nPvE Damage Window Start ms: "
        + getPveDamageWindowStartMs().get(counter)
        + "\nPvE Damage Window End ms: "
        + getPveDamageWindowEndMs().get(counter)
        + "\n"
        + "\nPvP Power: "
        + getPvpPower().get(counter)
        + "\nPvP Energy: "
        + getPvpEnergy().get(counter)
        + "\nPvP Turns: "
        + getPvpTurns().get(counter);
  }

  // Move Num
  public static ArrayList<Integer> getMoveNum() {
    return moveNum;
  }

  public static void setMoveNum(int moveNum) {
    MovesFastUtility.moveNum.add(moveNum);
  }

  // Move Name
  public static ArrayList<String> getMoveName() {
    return moveName;
  }

  public static void setMoveName(String moveName) {
    MovesFastUtility.moveName.add(moveName);
  }

  // Move Type
  public static ArrayList<String> getMoveType() {
    return moveType;
  }

  public static void setMoveType(String moveType) {
    MovesFastUtility.moveType.add(moveType);
  }

  // PvE Power
  public static ArrayList<Double> getPvePower() {
    return pvePower;
  }

  public static void setPvePower(Double pvePower) {
    MovesFastUtility.pvePower.add(pvePower);
  }

  // PvE Energy
  public static ArrayList<Byte> getPveEnergy() {
    return pveEnergy;
  }

  public static void setPveEnergy(Byte pveEnergy) {
    MovesFastUtility.pveEnergy.add(pveEnergy);
  }

  // PvE Duration
  public static ArrayList<Integer> getPveDurationMs() {
    return pveDurationMs;
  }

  public static void setPveDurationMs(int pveDurationMs) {
    MovesFastUtility.pveDurationMs.add(pveDurationMs);
  }

  // PvE Damage Window Start
  public static ArrayList<Integer> getPveDamageWindowStartMs() {
    return pveDamageWindowStartMs;
  }

  public static void setPveDamageWindowStartMs(int damageWindowStartMs) {
    MovesFastUtility.pveDamageWindowStartMs.add(damageWindowStartMs);
  }

  // PvE Damage Window End
  public static ArrayList<Integer> getPveDamageWindowEndMs() {
    return pveDamageWindowEndMs;
  }

  public static void setPveDamageWindowEndMs(int damageWindowEndsMs) {
    MovesFastUtility.pveDamageWindowEndMs.add(damageWindowEndsMs);
  }

  // PvP Power
  public static ArrayList<Double> getPvpPower() {
    return pvpPower;
  }

  public static void setPvpPower(Double pvpPower) {
    MovesFastUtility.pvpPower.add(pvpPower);
  }

  // PvP Energy
  public static ArrayList<Byte> getPvpEnergy() {
    return pvpEnergy;
  }

  public static void setPvpEnergy(Byte pvpEnergy) {
    MovesFastUtility.pvpEnergy.add(pvpEnergy);
  }

  // PvP Turns
  public static ArrayList<Byte> getPvpTurns() {
    return pvpTurns;
  }

  public static void setPvpTurns(Byte pvpTurns) {
    MovesFastUtility.pvpTurns.add(pvpTurns);
  }
}
