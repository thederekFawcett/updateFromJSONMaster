/************************************************
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 ************************************************/

package pokemans.utilities.read;

import java.util.ArrayList;

public class PokemonUtility {

    private boolean isLastInPoGoGen;
    private int pokeAttack, pokeDefense, pokeStamina;
    private String pokeDexNum, pokeGenNum, pokeName, pokeForm, pokeFamily;
    private ArrayList<String> pokeType, pokeMovesFast, pokeMovesCinematic,
            pokeMovesEliteFast, pokeMovesEliteCinematic, pokeMovesShadowCinematic, pokeMovesPurifiedCinematic;

    public PokemonUtility(boolean isLastInPoGoGen, int pokeAttack, int pokeDefense, int pokeStamina,
                          String pokeDexNum, String pokeGenNum, String pokeName, String pokeForm, String pokeFamily,
                          ArrayList<String> pokeType, ArrayList<String> pokeMovesFast,
                          ArrayList<String> pokeMovesCinematic, ArrayList<String> pokeMovesEliteFast,
                          ArrayList<String> pokeMovesEliteCinematic, ArrayList<String> pokeMovesShadowCinematic,
                          ArrayList<String> pokeMovesPurifiedCinematic) {
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
        return (//"\n++++++++++++++++++++++++++++++++++++++++++++" +
                "\nGen Num: " + this.getPokeGenNum() +
                        "\nDex Num: " + this.getPokeDexNum() +
                        "\nName: " + this.getPokeName() +
                        "\nForm: " + this.getPokeForm() +
                        "\nType(s): " + this.getPokeType() +
                        "\nAttack: " + this.getPokeAttack() +
                        "\nDefense: " + this.getPokeDefense() +
                        "\nStamina: " + this.getPokeStamina() +
                        "\nMoves Fast: " + this.getPokeMovesFast() +
                        "\nMoves Cinematic: " + this.getPokeMovesCinematic() +
                        "\nMoves Elite Fast: " + this.getPokeMovesEliteFast() +
                        "\nMoves Elite Cinematic: " + this.getPokeMovesEliteCinematic() +
                        "\nPoke Family: " + this.getPokeFamily()) +
                "\nIs last in gen?: " + this.getIsLastInPoGoGen();
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

    //forms
    public String getPokeForm() {
        return pokeForm;
    }

    public void setPokeForm(String pokeForm) {
        this.pokeForm = pokeForm;
    }

    // type(s)
    public ArrayList<String> getPokeType() {
        return pokeType;
    }

    public void setPokeType(ArrayList<String> pokeType) {
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
    public ArrayList<String> getPokeMovesFast() {
        return pokeMovesFast;
    }

    public void setPokeMovesFast(ArrayList<String> pokeMovesFast) {
        this.pokeMovesFast = pokeMovesFast;
    }

    public ArrayList<String> getPokeMovesEliteFast() {
        return pokeMovesEliteFast;
    }

    public void setPokeMovesEliteFast(ArrayList<String> pokeMovesEliteFast) {
        this.pokeMovesEliteFast = pokeMovesEliteFast;
    }

    // moves cinematic
    public ArrayList<String> getPokeMovesCinematic() {
        return pokeMovesCinematic;
    }

    public void setPokeMovesCinematic(ArrayList<String> pokeMovesCinematic) {
        this.pokeMovesCinematic = pokeMovesCinematic;
    }

    public ArrayList<String> getPokeMovesEliteCinematic() {
        return pokeMovesEliteCinematic;
    }

    public void setPokeMovesEliteCinematic(ArrayList<String> pokeMovesEliteCinematic) {
        this.pokeMovesEliteCinematic = pokeMovesEliteCinematic;
    }

    public ArrayList<String> getPokeMovesShadowCinematic() {
        return pokeMovesShadowCinematic;
    }

    public void setPokeMovesShadowCinematic(ArrayList<String> pokeMovesShadowCinematic) {
        this.pokeMovesShadowCinematic = pokeMovesShadowCinematic;
    }

    public ArrayList<String> getPokeMovesPurifiedCinematic() {
        return pokeMovesPurifiedCinematic;
    }

    public void setPokeMovesPurifiedCinematic(ArrayList<String> pokeMovesPurifiedCinematic) {
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
