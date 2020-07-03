/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */
package pokemans.utilities.read;

import java.util.List;

public class PokemonUtility {

  private boolean isLastInPoGoGen;
  private int pokeAttack, pokeDefense, pokeStamina;
  private String pokeDexNum, pokeGenNum, pokeName, pokeForm, pokeFamily;
  private List<String> pokeType,
      pokeMovesFast,
      pokeMovesCinematic,
      pokeMovesEliteFast,
      pokeMovesEliteCinematic,
      pokeMovesShadowCinematic,
      pokeMovesPurifiedCinematic;

  public PokemonUtility(
      boolean isLastInPoGoGen,
      int pokeAttack,
      int pokeDefense,
      int pokeStamina,
      String pokeDexNum,
      String pokeGenNum,
      String pokeName,
      String pokeForm,
      String pokeFamily,
      List<String> pokeType,
      List<String> pokeMovesFast,
      List<String> pokeMovesCinematic,
      List<String> pokeMovesEliteFast,
      List<String> pokeMovesEliteCinematic,
      List<String> pokeMovesShadowCinematic,
      List<String> pokeMovesPurifiedCinematic) {
    this.isLastInPoGoGen = isLastInPoGoGen;
    this.pokeAttack = pokeAttack;
    this.pokeDefense = pokeDefense;
    this.pokeStamina = pokeStamina;
    this.pokeDexNum = pokeDexNum;
    this.pokeGenNum = pokeGenNum;
    this.pokeName = pokeName;
    this.pokeForm = pokeForm;
    this.pokeFamily = pokeFamily;
    this.pokeType = pokeType;
    this.pokeMovesFast = pokeMovesFast;
    this.pokeMovesCinematic = pokeMovesCinematic;
    this.pokeMovesEliteFast = pokeMovesEliteFast;
    this.pokeMovesEliteCinematic = pokeMovesEliteCinematic;
    this.pokeMovesShadowCinematic = pokeMovesShadowCinematic;
    this.pokeMovesPurifiedCinematic = pokeMovesPurifiedCinematic;
  }

  @Override
  public String toString() {
    return ( // "\n++++++++++++++++++++++++++++++++++++++++++++" +
        "\nGen Num: "
            + this.getPokeGenNum()
            + "\nDex Num: "
            + this.getPokeDexNum()
            + "\nName: "
            + this.getPokeName()
            + "\nForm: "
            + this.getPokeForm()
            + "\nType(s): "
            + this.getPokeType()
            + "\nAttack: "
            + this.getPokeAttack()
            + "\nDefense: "
            + this.getPokeDefense()
            + "\nStamina: "
            + this.getPokeStamina()
            + "\nMoves Fast: "
            + this.getPokeMovesFast()
            + "\nMoves Cinematic: "
            + this.getPokeMovesCinematic()
            + "\nMoves Elite Fast: "
            + this.getPokeMovesEliteFast()
            + "\nMoves Elite Cinematic: "
            + this.getPokeMovesEliteCinematic()
            + "\nPoke Family: "
            + this.getPokeFamily())
        + "\nIs last in gen?: "
        + this.getIsLastInPoGoGen();
  }

  // gen number
  public String getPokeGenNum() {
    return pokeGenNum;
  }

  public void setPokeGenNum(String pokeGenNum) {
    this.pokeGenNum = pokeGenNum;
  }

  // dex number
  public String getPokeDexNum() {
    return pokeDexNum;
  }

  public void setPokeDexNum(String pokeDexNum) {
    this.pokeDexNum = pokeDexNum;
  }

  // name
  public String getPokeName() {
    return pokeName;
  }

  public void setPokeName(String pokeName) {
    this.pokeName = pokeName;
  }

  // family
  public String getPokeFamily() {
    return pokeFamily;
  }

  public void setPokeFamily(String pokeFamily) {
    this.pokeFamily = pokeFamily;
  }

  // forms
  public String getPokeForm() {
    return pokeForm;
  }

  public void setPokeForm(String pokeForm) {
    this.pokeForm = pokeForm;
  }

  // type(s)
  public List<String> getPokeType() {
    return pokeType;
  }

  public void setPokeType(List<String> pokeType) {
    this.pokeType = pokeType;
  }

  // stats
  public int getPokeAttack() {
    return pokeAttack;
  }

  public void setPokeAttack(int pokeAttack) {
    this.pokeAttack = pokeAttack;
  }

  public int getPokeDefense() {
    return pokeDefense;
  }

  public void setPokeDefense(int pokeDefense) {
    this.pokeDefense = pokeDefense;
  }

  public int getPokeStamina() {
    return pokeStamina;
  }

  public void setPokeStamina(int pokeStamina) {
    this.pokeStamina = pokeStamina;
  }

  // moves fast
  public List<String> getPokeMovesFast() {
    return pokeMovesFast;
  }

  public void setPokeMovesFast(List<String> pokeMovesFast) {
    this.pokeMovesFast = pokeMovesFast;
  }

  public List<String> getPokeMovesEliteFast() {
    return pokeMovesEliteFast;
  }

  public void setPokeMovesEliteFast(List<String> pokeMovesEliteFast) {
    this.pokeMovesEliteFast = pokeMovesEliteFast;
  }

  // moves cinematic
  public List<String> getPokeMovesCinematic() {
    return pokeMovesCinematic;
  }

  public void setPokeMovesCinematic(List<String> pokeMovesCinematic) {
    this.pokeMovesCinematic = pokeMovesCinematic;
  }

  public List<String> getPokeMovesEliteCinematic() {
    return pokeMovesEliteCinematic;
  }

  public void setPokeMovesEliteCinematic(List<String> pokeMovesEliteCinematic) {
    this.pokeMovesEliteCinematic = pokeMovesEliteCinematic;
  }

  public List<String> getPokeMovesShadowCinematic() {
    return pokeMovesShadowCinematic;
  }

  public void setPokeMovesShadowCinematic(List<String> pokeMovesShadowCinematic) {
    this.pokeMovesShadowCinematic = pokeMovesShadowCinematic;
  }

  public List<String> getPokeMovesPurifiedCinematic() {
    return pokeMovesPurifiedCinematic;
  }

  public void setPokeMovesPurifiedCinematic(List<String> pokeMovesPurifiedCinematic) {
    this.pokeMovesPurifiedCinematic = pokeMovesPurifiedCinematic;
  }

  // last in generation boolean (for writing Pokedex enum)
  public boolean getIsLastInPoGoGen() {
    return isLastInPoGoGen;
  }

  public void setIsLastInPoGoGen(boolean lastInPoGoGen) {
    isLastInPoGoGen = lastInPoGoGen;
  }
}
