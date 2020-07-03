/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */
package pokemans.utilities.read;

import java.util.ArrayList;

public class MovesCinematicUtility {

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
  private static final ArrayList<Double> pvpPower = new ArrayList<>(),
      pvpBuffChance = new ArrayList<>();
  private static final ArrayList<Byte> pvpEnergy = new ArrayList<>(),
      pvpBuffAttackerAttack = new ArrayList<>(),
      pvpBuffAttackerDefense = new ArrayList<>(),
      pvpBuffTargetAttack = new ArrayList<>(),
      pvpBuffTargetDefense = new ArrayList<>();

  public MovesCinematicUtility(
      int moveNum,
      String moveName,
      String moveType,
      Double pvePower,
      Byte pveEnergy,
      int pveDurationMs,
      int pveDamageWindowStartMs,
      int pveDamageWindowEndMs,
      Double pvpPower,
      Byte pvpEnergy,
      Double pvpBuffChance,
      Byte pvpBuffAttackerAttack,
      Byte pvpBuffAttackerDefense,
      Byte pvpBuffTargetAttack,
      Byte pvpBuffTargetDefense) {

    setMoveNum(moveNum);
    setMoveName(moveName);
    setMoveType(moveType);
    setPvePower(pvePower);
    setPveEnergy(pveEnergy);
    setPveDurationMs(pveDurationMs);
    setPveDamageWindowStartMs(pveDamageWindowStartMs);
    setPveDamageWindowEndMs(pveDamageWindowEndMs);
    setPvpPower(pvpPower);
    setPvpEnergy(pvpEnergy);
    setPvpBuffChance(pvpBuffChance);
    setPvpBuffAttackerAttack(pvpBuffAttackerAttack);
    setPvpBuffAttackerDefense(pvpBuffAttackerDefense);
    setPvpBuffTargetAttack(pvpBuffTargetAttack);
    setPvpBuffTargetDefense(pvpBuffTargetDefense);
  }

  public static String printInfo(int counter) {
    return "\nCinematic Moves-----------------------------"
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
        + "\nPvP Power: "
        + getPvpPower().get(counter)
        + "\nPvP Energy: "
        + getPvpEnergy().get(counter)
        + "\nPvP Buff Chance: "
        + getPvpBuffChance().get(counter)
        + "\nPvP Buff A/A: "
        + getPvpBuffAttackerAttack().get(counter)
        + "\nPvP Buff A/D: "
        + getPvpBuffAttackerDefense().get(counter)
        + "\nPvP Buff T/A: "
        + getPvpBuffTargetAttack().get(counter)
        + "\nPvP Buff T/D: "
        + getPvpBuffAttackerDefense().get(counter);
  }

  // Move Num
  public static ArrayList<Integer> getMoveNum() {
    return moveNum;
  }

  public static void setMoveNum(int moveNum) {
    MovesCinematicUtility.moveNum.add(moveNum);
  }

  // Move Name
  public static ArrayList<String> getMoveName() {
    return moveName;
  }

  public static void setMoveName(String moveName) {
    MovesCinematicUtility.moveName.add(moveName);
  }

  // Move Type
  public static ArrayList<String> getMoveType() {
    return moveType;
  }

  public static void setMoveType(String moveType) {
    MovesCinematicUtility.moveType.add(moveType);
  }

  // PvE Power
  public static ArrayList<Double> getPvePower() {
    return pvePower;
  }

  public static void setPvePower(Double pvePower) {
    MovesCinematicUtility.pvePower.add(pvePower);
  }

  // PvE Energy
  public static ArrayList<Byte> getPveEnergy() {
    return pveEnergy;
  }

  public static void setPveEnergy(Byte pveEnergy) {
    MovesCinematicUtility.pveEnergy.add(pveEnergy);
  }

  // PvE Duration
  public static ArrayList<Integer> getPveDurationMs() {
    return pveDurationMs;
  }

  public static void setPveDurationMs(int pveDurationMs) {
    MovesCinematicUtility.pveDurationMs.add(pveDurationMs);
  }

  // PvE Damage Window Start
  public static ArrayList<Integer> getPveDamageWindowStartMs() {
    return pveDamageWindowStartMs;
  }

  public static void setPveDamageWindowStartMs(int damageWindowStartMs) {
    MovesCinematicUtility.pveDamageWindowStartMs.add(damageWindowStartMs);
  }

  // PvE Damage Window End
  public static ArrayList<Integer> getPveDamageWindowEndMs() {
    return pveDamageWindowEndMs;
  }

  public static void setPveDamageWindowEndMs(int damageWindowEndsMs) {
    MovesCinematicUtility.pveDamageWindowEndMs.add(damageWindowEndsMs);
  }

  // PvP Power
  public static ArrayList<Double> getPvpPower() {
    return pvpPower;
  }

  public static void setPvpPower(Double pvpPower) {
    MovesCinematicUtility.pvpPower.add(pvpPower);
  }

  // PvP Energy
  public static ArrayList<Byte> getPvpEnergy() {
    return pvpEnergy;
  }

  public static void setPvpEnergy(Byte pvpEnergy) {
    MovesCinematicUtility.pvpEnergy.add(pvpEnergy);
  }

  // PvP Buff Chance
  public static ArrayList<Double> getPvpBuffChance() {
    return pvpBuffChance;
  }

  public static void setPvpBuffChance(Double pvpBuffChance) {
    MovesCinematicUtility.pvpBuffChance.add(pvpBuffChance);
  }

  // PvP Buff Attacker Attack
  public static ArrayList<Byte> getPvpBuffAttackerAttack() {
    return pvpBuffAttackerAttack;
  }

  public static void setPvpBuffAttackerAttack(Byte pvpBuffAttackerAttack) {
    MovesCinematicUtility.pvpBuffAttackerAttack.add(pvpBuffAttackerAttack);
  }

  // PvP Buff Attacker Defense
  public static ArrayList<Byte> getPvpBuffAttackerDefense() {
    return pvpBuffAttackerDefense;
  }

  public static void setPvpBuffAttackerDefense(Byte pvpBuffAttackerDefense) {
    MovesCinematicUtility.pvpBuffAttackerDefense.add(pvpBuffAttackerDefense);
  }

  // PvP Buff Target Attack
  public static ArrayList<Byte> getPvpBuffTargetAttack() {
    return pvpBuffTargetAttack;
  }

  public static void setPvpBuffTargetAttack(Byte pvpBuffTargetAttack) {
    MovesCinematicUtility.pvpBuffTargetAttack.add(pvpBuffTargetAttack);
  }

  // PvP Buff Target Defense
  public static ArrayList<Byte> getPvpBuffTargetDefense() {
    return pvpBuffTargetDefense;
  }

  public static void setPvpBuffTargetDefense(Byte pvpBuffTargetDefense) {
    MovesCinematicUtility.pvpBuffTargetDefense.add(pvpBuffTargetDefense);
  }
}
