/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */
package pokemans.utilities.write;

import pokemans.core.Type;
import pokemans.utilities.read.MovesCinematicUtility;
import pokemans.utilities.read.MovesFastUtility;
import pokemans.utilities.read.PokemonUtility;
import pokemans.utilities.read.TypeUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class WriteFiles {

  private static final String fileHeader =
      "/*\n * Copyright (c) 2020, Derek Fawcett. All rights reserved."
          + "No usage without permission.\n*/\n\n // **This file is auto-generated** \n\npackage pokemans.core;\n";
  private static final ArrayList<String> firstOfEachGen = new ArrayList<>();
  private static PrintWriter writer;
  private static StringBuilder builder0, builder1;

  // write Pokedex
  public static void writePokedexEnum(List<PokemonUtility> pokemonUtilityList) {
    try {
      writer = new PrintWriter("src\\pokemans\\core\\Pokedex.java");

      writer.print(fileHeader);
      writer.print("\nimport java.util.HashSet;\n" + "import java.util.stream.Stream;\n");
      writer.print("\npublic enum Pokedex {\n\n\t");

      String pokedexVariables =
          "\n\t\tprivate final int POKEATTACK, POKEDEFENSE, POKESTAMINA;\n"
              + "\t\tprivate final String POKEGENNUM, POKEDEXNUM, POKENAME, POKEFORM, POKEFAMILY;\n"
              + "\t\tprivate final MovesFast[] POKEMOVESFAST, POKEMOVESELITEFAST;\n"
              + "\t\tprivate final MovesCinematic[] POKEMOVESCINEMATIC, POKEMOVESELITECINEMATIC, POKEMOVESSHADOWCINEMATIC, POKEMOVESPURIFIEDCINEMATIC;\n"
              + "\t\tprivate final Type[] TYPE;\n";

      // write list of all dex entries (eg BULBASAUR(Generation1.BULBASAUR))
      String checkDexNum = "1";
      for (PokemonUtility pc : pokemonUtilityList) {
        builder1 =
            new StringBuilder(
                pc.getPokeName()
                    + "(Generation"
                    + pc.getPokeGenNum()
                    + "."
                    + pc.getPokeName()
                    + ")");
        if (pc != pokemonUtilityList.get(pokemonUtilityList.size() - 1)
            && pc.getPokeDexNum().equals(checkDexNum)) {
          builder1.append(", ");
        } else if (pc != pokemonUtilityList.get(pokemonUtilityList.size() - 1)
            && !pc.getPokeDexNum().equals(checkDexNum)) {
          builder1.insert(0, "\n\t");
          builder1.append(", ");
        } else if (pc == pokemonUtilityList.get(pokemonUtilityList.size() - 1)) {
          builder1.append(";\n");
        }
        writer.print(builder1);
        checkDexNum = pc.getPokeDexNum();
      }

      writer.print(pokedexVariables);

      // write constructors for each Gen (ie - Generation1, Generation2, etc)
      firstOfEachGen.clear();
      for (PokemonUtility enumPoke : pokemonUtilityList) {
        // boolean alreadyWritten = switchGen(enumPoke.getPokeName());
        boolean alreadyWritten = checkIfGenStartWritten(enumPoke.getPokeGenNum());
        if (alreadyWritten) {
          printPokedexEachConstructor(enumPoke.getPokeGenNum());
        }
      }

      writer.print(
          "\n\tpublic String getPokeGenNum() {\n"
              + "\t\treturn POKEGENNUM;\n"
              + "\t}\n"
              + "\tpublic String getPokeDexNum() {\n"
              + "\t\treturn POKEDEXNUM;\n"
              + "\t}\n"
              + "\tpublic String getPokeName() {\n"
              + "\t\treturn POKENAME;\n"
              + "\t}\n"
              + "\tpublic String getPokeForm() {\n"
              + "\t\treturn POKEFORM;\n"
              + "\t}\n"
              + "\tpublic String getPokeFamily() {\n"
              + "\t\treturn POKEFAMILY;\n"
              + "\t}\n"
              + "\tpublic int getPokeAttack() {\n"
              + "\t\treturn POKEATTACK;\n"
              + "\t}\n"
              + "\tpublic int getPokeDefense() {\n"
              + "\t\treturn POKEDEFENSE;\n"
              + "\t}\n"
              + "\tpublic int getPokeStamina() {\n"
              + "\t\treturn POKESTAMINA;\n"
              + "\t}\n"
              + "\tpublic MovesFast[] getPokeMovesFast() {\n"
              + "\t\treturn POKEMOVESFAST;\n"
              + "\t}\n"
              + "\tpublic MovesCinematic[] getPokeMovesCinematic() {\n"
              + "\t\treturn POKEMOVESCINEMATIC;\n"
              + "\t}\n"
              + "\tpublic MovesFast[] getPokeMovesEliteFast() {\n"
              + "\t\treturn POKEMOVESELITEFAST;\n"
              + "\t}\n"
              + "\tpublic MovesCinematic[] getPokeMovesEliteCinematic() {\n"
              + "\t\treturn POKEMOVESELITECINEMATIC;\n"
              + "\t}\n"
              + "\tpublic MovesCinematic[] getPokeMovesShadowCinematic() {\n"
              + "\t\treturn POKEMOVESSHADOWCINEMATIC;\n"
              + "\t}\n"
              + "\tpublic MovesCinematic[] getPokeMovesPurifiedCinematic() {\n"
              + "\t\treturn POKEMOVESPURIFIEDCINEMATIC;\n"
              + "\t}\n"
              + "\tpublic Type[] getType() {\n"
              + "\t\treturn TYPE;\n"
              + "\t}\n"
              + "\tpublic Type getType1() {\n"
              + "\t\treturn TYPE[0];\n"
              + "\t}\n"
              + "\tpublic Type getType2() {\n"
              + "\t\treturn TYPE[1];\n"
              + "\t}\n"
              + "\tpublic MovesCinematic[] getAllCinematicMoves() {\n"
              + "\t\treturn Stream.of(getPokeMovesCinematic(), getPokeMovesEliteCinematic(), getPokeMovesShadowCinematic(),\n"
              + "\t\t\tgetPokeMovesPurifiedCinematic()).flatMap(Stream::of).toArray(MovesCinematic[]::new);\n"
              + "\t}\n"
              + "\tpublic MovesFast[] getAllFastMoves() {\n"
              + "\t\treturn Stream.of(getPokeMovesFast(), getPokeMovesEliteFast()).flatMap(Stream::of).toArray(MovesFast[]::new);\n"
              + "\t}\n"
              + "\n"
              + "\tpublic static HashSet<String> getAllPokeNames() {\n"
              + "\t\tHashSet<String> values = new HashSet<String>();\n"
              + "\t\tfor (Pokedex poke : Pokedex.values()) {\n"
              + "\t\t\tvalues.add(poke.getPokeName());\n"
              + "\t\t}\n"
              + "\t\treturn values;\n"
              + "\t}\n"
              + "\t\n"
              + "\tpublic static Pokedex getPokeFromString (String userInput) {\n"
              + "\t\tfor (Pokedex poke : Pokedex.values()) {\n"
              + "\t\t\tif (poke.POKENAME.equalsIgnoreCase(userInput)) {\n"
              + "\t\t\t\treturn poke;\n"
              + "\t\t\t}\n"
              + "\t\t}\n"
              + "\t\treturn null;\n"
              + "\t}");

      // writes "main chunk" of enum. Lists each poke and info
      firstOfEachGen.clear();
      for (PokemonUtility enumPoke : pokemonUtilityList) {
        boolean lastEntry = false;
        // Boolean alreadyWritten = switchGen(enumPoke.getPokeName());
        boolean alreadyWritten = checkIfGenStartWritten(enumPoke.getPokeGenNum());

        if (alreadyWritten) {
          writer.print("\n\tprivate enum Generation" + enumPoke.getPokeGenNum() + " {\n");
          writePokedexClassEnumAllPokes(enumPoke, lastEntry);
        } else {
          if (enumPoke.getIsLastInPoGoGen()) {
            lastEntry = true;
            writePokedexClassEnumAllPokes(enumPoke, lastEntry);
            printPokedexVariablesAndConstructor(enumPoke.getPokeGenNum(), pokedexVariables);
          } else {
            lastEntry = enumPoke == pokemonUtilityList.get(pokemonUtilityList.size() - 1);
            writePokedexClassEnumAllPokes(enumPoke, lastEntry);
            if (lastEntry) {
              printPokedexVariablesAndConstructor(enumPoke.getPokeGenNum(), pokedexVariables);
            }
          }
        }
      }

      writer.print("}");

      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void writePokedexClassEnumAllPokes(PokemonUtility pc, Boolean lastEntry) {

    // write Start (dex num, name, etc)
    writer.print(
        "\n\t\t"
            + pc.getPokeName()
            + "("
            + "\""
            + pc.getPokeDexNum()
            + "\", "
            + "\""
            + pc.getPokeName()
            + "\", "
            + "\""
            + pc.getPokeForm()
            + "\", "
            + "\""
            + pc.getPokeFamily()
            + "\", "
            + pc.getPokeAttack()
            + ", "
            + pc.getPokeDefense()
            + ", "
            + pc.getPokeStamina()
            + ", ");

    // write Moves Fast
    writer.print("new MovesFast[]{");
    for (int i = 0; i < pc.getPokeMovesFast().size(); i++) {
      if (!pc.getPokeMovesFast().get(i).contains("HIDDEN_P")) {
        builder1 = new StringBuilder("MovesFast.").append(pc.getPokeMovesFast().get(i));
        if (i != (pc.getPokeMovesFast().size() - 1)) {
          writer.print(builder1.append(", "));
        } else {
          writer.print(builder1.append("}, "));
        }

      } else if (pc.getPokeMovesFast().get(i).contains("HIDDEN_P")) {
        for (Type type : Type.values()) {
          if (!type.getTYPENAME().contains("NORM")) {
            builder1 =
                new StringBuilder("MovesFast.HIDDEN_POWER_")
                    .append(type.getTYPENAME().substring(13))
                    .append("_FAST");
            if (!type.getTYPENAME().equals("POKEMON_TYPE_WATER")) {
              writer.print(builder1.append(", "));
            }
          }
        }
        if ((i == pc.getPokeMovesFast().size() - 1)) {
          writer.print(builder1.append("}, "));
        }
      }
    }

    // write Moves Cinematic
    writer.print("new MovesCinematic[]{");
    for (int i = 0; i < pc.getPokeMovesCinematic().size(); i++) {
      builder1 = new StringBuilder("MovesCinematic.").append(pc.getPokeMovesCinematic().get(i));
      if ((i + 1) < pc.getPokeMovesCinematic().size()) {
        writer.print(builder1.append(", "));
      } else {
        writer.print(builder1.append("}, "));
      }
    }

    // write Moves Elite Fast
    writer.print("new MovesFast[]{");
    if (pc.getPokeMovesEliteFast().size() == 0) {
      builder1 = new StringBuilder("}, ");
      writer.print(builder1);
    } else {
      for (int i = 0; i < pc.getPokeMovesEliteFast().size(); i++) {
        if (!pc.getPokeMovesEliteFast().get(i).contains("HIDDEN_P")) {
          builder1 = new StringBuilder("MovesFast.").append(pc.getPokeMovesEliteFast().get(i));
          if ((i + 1) < pc.getPokeMovesEliteFast().size()) {
            writer.print(builder1.append(", "));
          } else {
            writer.print(builder1.append("}, "));
          }
        } else if (pc.getPokeMovesEliteFast().get(i).contains("HIDDEN_P")) {
          for (Type type : Type.values()) {
            if (!type.getTYPENAME().contains("NORM")) {
              builder1 =
                  new StringBuilder("MovesFast.HIDDEN_POWER_")
                      .append(type.getTYPENAME().substring(13))
                      .append("_FAST");
              if (!type.getTYPENAME().equals("POKEMON_TYPE_WATER")) {
                writer.print(builder1.append(", "));
              }
            }
          }
          if ((i == pc.getPokeMovesEliteFast().size() - 1)) {
            writer.print(builder1.append("}, "));
          }
        }
      }
    }

    // write Moves Elite Cinematic
    writer.print("new MovesCinematic[]{");
    if (pc.getPokeMovesEliteCinematic().size() == 0) {
      builder1 = new StringBuilder("}, ");
      writer.print(builder1);
    } else {
      for (int i = 0; i < pc.getPokeMovesEliteCinematic().size(); i++) {
        builder1 =
            new StringBuilder("MovesCinematic.").append(pc.getPokeMovesEliteCinematic().get(i));
        if ((i + 1) < pc.getPokeMovesEliteCinematic().size()) {
          writer.print(builder1.append(", "));
        } else {
          writer.print(builder1.append("}, "));
        }
      }
    }

    // write Moves Shadow Cinematic
    writer.print("new MovesCinematic[]{");
    if (pc.getPokeMovesShadowCinematic().size() == 0) {
      builder1 = new StringBuilder("}, ");
      writer.print(builder1);
    } else {
      for (int i = 0; i < pc.getPokeMovesShadowCinematic().size(); i++) {
        builder1 =
            new StringBuilder("MovesCinematic.").append(pc.getPokeMovesShadowCinematic().get(i));
        if ((i + 1) < pc.getPokeMovesShadowCinematic().size()) {
          writer.print(builder1.append(", "));
        } else {
          writer.print(builder1.append("}, "));
        }
      }
    }
    // write Moves Purified Cinematic
    writer.print("new MovesCinematic[]{");
    if (pc.getPokeMovesPurifiedCinematic().size() == 0) {
      builder1 = new StringBuilder("}, ");
      writer.print(builder1);
    } else {
      for (int i = 0; i < pc.getPokeMovesPurifiedCinematic().size(); i++) {
        builder1 =
            new StringBuilder("MovesCinematic.").append(pc.getPokeMovesPurifiedCinematic().get(i));
        if ((i + 1) < pc.getPokeMovesPurifiedCinematic().size()) {
          writer.print(builder1.append(", "));
        } else {
          writer.print(builder1.append("}, "));
        }
      }
    }

    // write Type(s)
    writer.print("new Type[]{");
    builder1 =
        new StringBuilder("Type.")
            .append(pc.getPokeType().get(0).replace("[", "").replace("]", ""));
    if (pc.getPokeType().size() == 1) {
      builder1.append("})");
    } else if (pc.getPokeType().size() == 2) {
      builder1
          .append(", Type.")
          .append(pc.getPokeType().get(1).replace("[", "{").replace("]", "}"))
          .append("})");
    }
    if (!lastEntry) {
      builder1.append(",");
    } else {
      builder1.append(";\n");
    }
    writer.print(builder1);
  }

  private static boolean checkIfGenStartWritten(String pokeGenNum) {
    boolean alreadyWritten = false;

    if (!firstOfEachGen.contains(pokeGenNum)) {
      firstOfEachGen.add(pokeGenNum);
      alreadyWritten = true;
    }

    return alreadyWritten;
  }

  private static void printPokedexVariablesAndConstructor(
      String stringGenNum, String pokedexVariables) {
    writer.print(
        pokedexVariables
            + "\n\t\tGeneration"
            + stringGenNum
            + "(final String pokeDexNum,"
            + "final String pokeName, final String pokeForm, final String pokeFamily,"
            + "\n\t\t\t\t\tfinal int pokeAttack, final int pokeDefense, final int pokeStamina,"
            + "\n\t\t\t\t\tMovesFast[] pokeMovesFast, MovesCinematic[] pokeMovesCinematic,"
            + "\n\t\t\t\t\tMovesFast[] pokeMovesEliteFast, MovesCinematic[] pokeMovesEliteCinematic,"
            + "\n\t\t\t\t\tMovesCinematic[] pokeMovesShadowCinematic, MovesCinematic[] pokeMovesPurifiedCinematic,"
            + "\n\t\t\t\t\tType[] type) {\n"
            + "\n\t\t\tPOKEGENNUM = \""
            + stringGenNum
            + "\";"
            + "\n\t\t\tPOKEDEXNUM = pokeDexNum;"
            + "\n\t\t\tPOKENAME = pokeName;"
            + "\n\t\t\tPOKEFORM = pokeForm;"
            + "\n\t\t\tPOKEFAMILY = pokeFamily;"
            + "\n\t\t\tPOKEATTACK = pokeAttack;"
            + "\n\t\t\tPOKEDEFENSE = pokeDefense;"
            + "\n\t\t\tPOKESTAMINA = pokeStamina;"
            + "\n\t\t\tPOKEMOVESFAST = pokeMovesFast;"
            + "\n\t\t\tPOKEMOVESCINEMATIC = pokeMovesCinematic;"
            + "\n\t\t\tPOKEMOVESELITEFAST = pokeMovesEliteFast;"
            + "\n\t\t\tPOKEMOVESELITECINEMATIC = pokeMovesEliteCinematic;"
            + "\n\t\t\tPOKEMOVESSHADOWCINEMATIC = pokeMovesShadowCinematic;"
            + "\n\t\t\tPOKEMOVESPURIFIEDCINEMATIC = pokeMovesPurifiedCinematic;"
            + "\n\t\t\tTYPE = type;\n\t\t}\n\t}\n");
  }

  private static void printPokedexEachConstructor(String stringGenNumber) {
    writer.print(
        "\n\tPokedex(Generation"
            + stringGenNumber
            + " g) {\n"
            + "\t\tPOKEGENNUM = g.POKEGENNUM;\n"
            + "\t\tPOKEDEXNUM = g.POKEDEXNUM;\n"
            + "\t\tPOKENAME = g.POKENAME;\n"
            + "\t\tPOKEFORM = g.POKEFORM;\n"
            + "\t\tPOKEFAMILY = g.POKEFAMILY;\n"
            + "\t\tPOKEATTACK = g.POKEATTACK;\n"
            + "\t\tPOKEDEFENSE = g.POKEDEFENSE;\n"
            + "\t\tPOKESTAMINA = g.POKESTAMINA;\n"
            + "\t\tPOKEMOVESFAST = g.POKEMOVESFAST;\n"
            + "\t\tPOKEMOVESCINEMATIC = g.POKEMOVESCINEMATIC;\n"
            + "\t\tPOKEMOVESELITEFAST = g.POKEMOVESELITEFAST;\n"
            + "\t\tPOKEMOVESELITECINEMATIC = g.POKEMOVESELITECINEMATIC;\n"
            + "\t\tPOKEMOVESSHADOWCINEMATIC = g.POKEMOVESSHADOWCINEMATIC;\n"
            + "\t\tPOKEMOVESPURIFIEDCINEMATIC = g.POKEMOVESPURIFIEDCINEMATIC;\n"
            + "\t\tTYPE = g.TYPE;\n\t}\n");
  }

  // write Moves Cinematic
  public static void writeMovesCinematicEnum() {
    try {
      writer = new PrintWriter("src\\pokemans\\core\\MovesCinematic.java");

      builder0 = new StringBuilder(fileHeader);

      String movesCinematicVariables =
          "\n\tprivate final String MOVENUM, MOVENAME;\n"
              + "\tprivate final Type MOVETYPE;\n"
              + "\tprivate final Double PVEPOWER, PVPPOWER, PVPDPE, PVPBUFFCHANCE;\n"
              + "\tprivate final Byte PVEENERGY, PVPENERGY,\n\t\t\tPVPBUFFATTACKERATTACK, PVPBUFFATTACKERDEFENSE, PVPBUFFTARGETATTACK, PVPBUFFTARGETDEFENSE;\n"
              + "\tprivate final int PVEDURATIONMS, PVEDAMAGEWINDOWSTARTMS, PVEEDAMAGEWINDOWENDMS;\n";

      builder0.append("\npublic enum MovesCinematic {\n\n\t");
      writer.print(builder0);

      for (int writeCounter = 1;
          writeCounter < MovesCinematicUtility.getMoveNum().size();
          writeCounter++) {
        builder1 =
            new StringBuilder(
                MovesCinematicUtility.getMoveName().get(writeCounter)
                    + "(ChargeMove."
                    + MovesCinematicUtility.getMoveName().get(writeCounter)
                    + ")");
        if (writeCounter != (MovesCinematicUtility.getMoveNum().size() - 1)) {
          builder1.append(",\n\t");
        } else {
          builder1.append(";\n");
        }

        writer.print(builder1);
      }

      writer.print("\n\tprivate enum ChargeMove {\n");

      for (int writeCounter = 1;
          writeCounter < MovesCinematicUtility.getMoveNum().size();
          writeCounter++) {
        double dpe;
        if (MovesCinematicUtility.getPvpPower().get(writeCounter) > 0) {
          BigDecimal pvpDPE =
              ((new BigDecimal(MovesCinematicUtility.getPvpPower().get(writeCounter).toString()))
                      .divide(
                          new BigDecimal(
                              MovesCinematicUtility.getPvpEnergy().get(writeCounter).toString()),
                          8,
                          RoundingMode.DOWN))
                  .negate();
          dpe = pvpDPE.doubleValue();
        } else {
          dpe = 0.0;
        }

        builder1 =
            new StringBuilder("\n\t")
                .append(MovesCinematicUtility.getMoveName().get(writeCounter))
                .append("(")
                .append("\"")
                .append(MovesCinematicUtility.getMoveNum().get(writeCounter))
                .append("\", ")
                .append("\"")
                .append(MovesCinematicUtility.getMoveName().get(writeCounter))
                .append("\", ")
                .append("Type.")
                .append(MovesCinematicUtility.getMoveType().get(writeCounter))
                .append(", ")
                .append(MovesCinematicUtility.getPvePower().get(writeCounter))
                .append(", ")
                .append("(byte) ")
                .append(MovesCinematicUtility.getPveEnergy().get(writeCounter))
                .append(", ")
                .append(MovesCinematicUtility.getPveDurationMs().get(writeCounter))
                .append(", ")
                .append(MovesCinematicUtility.getPveDamageWindowStartMs().get(writeCounter))
                .append(", ")
                .append(MovesCinematicUtility.getPveDamageWindowEndMs().get(writeCounter))
                .append(", ")
                .append(MovesCinematicUtility.getPvpPower().get(writeCounter))
                .append(", ")
                .append("(byte) ")
                .append(MovesCinematicUtility.getPvpEnergy().get(writeCounter))
                .append(", ")
                .append(dpe)
                .append(", ")
                .append(MovesCinematicUtility.getPvpBuffChance().get(writeCounter))
                .append(", ")
                .append("(byte) ")
                .append(MovesCinematicUtility.getPvpBuffAttackerAttack().get(writeCounter))
                .append(", ")
                .append("(byte) ")
                .append(MovesCinematicUtility.getPvpBuffAttackerDefense().get(writeCounter))
                .append(", ")
                .append("(byte) ")
                .append(MovesCinematicUtility.getPvpBuffTargetAttack().get(writeCounter))
                .append(", ")
                .append("(byte) ")
                .append(MovesCinematicUtility.getPvpBuffTargetDefense().get(writeCounter))
                .append(")");
        if (writeCounter != (MovesCinematicUtility.getMoveNum().size() - 1)) {
          writer.print(builder1.append(","));
        } else {
          writer.print(builder1.append(";\n"));
        }
      }

      writer.print(movesCinematicVariables);
      writer.print(
          "\n\tChargeMove (String moveNum, String moveName, Type moveType, Double pvePower,\n"
              + "\t\t\tByte pveEnergy, int pveDurationMs, int pveDamageWindowStartMs, int pveDamageWindowEndMs,\n"
              + "\t\t\tDouble pvpPower, Byte pvpEnergy, Double pvpDPE, Double pvpBuffChance, Byte pvpBuffAttackerAttack,\n"
              + "\t\t\tByte pvpBuffAttackerDefense, Byte pvpBuffTargetAttack, Byte pvpBuffTargetDefense) {\n"
              + "\t\tMOVENUM = moveNum;\n"
              + "\t\tMOVENAME = moveName;\n"
              + "\t\tMOVETYPE = moveType;\n"
              + "\t\tPVEPOWER = pvePower;\n"
              + "\t\tPVEENERGY = pveEnergy;\n"
              + "\t\tPVEDURATIONMS = pveDurationMs;\n"
              + "\t\tPVEDAMAGEWINDOWSTARTMS = pveDamageWindowStartMs;\n"
              + "\t\tPVEEDAMAGEWINDOWENDMS = pveDamageWindowEndMs;\n"
              + "\t\tPVPPOWER = pvpPower;\n"
              + "\t\tPVPENERGY = pvpEnergy;\n"
              + "\t\tPVPDPE = pvpDPE;\n"
              + "\t\tPVPBUFFCHANCE = pvpBuffChance;\n"
              + "\t\tPVPBUFFATTACKERATTACK = pvpBuffAttackerAttack;\n"
              + "\t\tPVPBUFFATTACKERDEFENSE = pvpBuffAttackerDefense;\n"
              + "\t\tPVPBUFFTARGETATTACK = pvpBuffTargetAttack;\n"
              + "\t\tPVPBUFFTARGETDEFENSE = pvpBuffTargetDefense;\n\t}\n\n}\n");

      writer.print(movesCinematicVariables);
      writer.print(
          "\n\tMovesCinematic (ChargeMove cm) {\n"
              + "\t\tMOVENUM = cm.MOVENUM;\n"
              + "\t\tMOVENAME = cm.MOVENAME;\n"
              + "\t\tMOVETYPE = cm.MOVETYPE;\n"
              + "\t\tPVEPOWER = cm.PVEPOWER;\n"
              + "\t\tPVEENERGY = cm.PVEENERGY;\n"
              + "\t\tPVEDURATIONMS = cm.PVEDURATIONMS;\n"
              + "\t\tPVEDAMAGEWINDOWSTARTMS = cm.PVEDAMAGEWINDOWSTARTMS;\n"
              + "\t\tPVEEDAMAGEWINDOWENDMS = cm.PVEEDAMAGEWINDOWENDMS;\n"
              + "\t\tPVPPOWER = cm.PVPPOWER;\n"
              + "\t\tPVPENERGY = cm.PVPENERGY;\n"
              + "\t\tPVPDPE = cm.PVPDPE;\n"
              + "\t\tPVPBUFFCHANCE = cm.PVPBUFFCHANCE;\n"
              + "\t\tPVPBUFFATTACKERATTACK = cm.PVPBUFFATTACKERATTACK;\n"
              + "\t\tPVPBUFFATTACKERDEFENSE = cm.PVPBUFFATTACKERDEFENSE;\n"
              + "\t\tPVPBUFFTARGETATTACK = cm.PVPBUFFTARGETATTACK;\n"
              + "\t\tPVPBUFFTARGETDEFENSE = cm.PVPBUFFTARGETDEFENSE;\n\t}\n");

      writer.print(
          "\n\tpublic String getMoveNum() {\n\t\treturn MOVENUM;\n\t}"
              + "\n\tpublic String getMoveName() {\n\t\treturn MOVENAME;\n\t}"
              + "\n\tpublic Type getMoveType() {\n\t\treturn MOVETYPE;\n\t}"
              + "\n\tpublic Double getPvePower() {\n\t\treturn PVEPOWER;\n\t}"
              + "\n\tpublic Byte getPveEnergy() {\n\t\treturn PVEENERGY;\n\t}"
              + "\n\tpublic int getPveDurationMs() {\n\t\treturn PVEDURATIONMS;\n\t}"
              + "\n\tpublic int getPveDamageWindowStartMs() {\n\t\treturn PVEDAMAGEWINDOWSTARTMS;\n\t}"
              + "\n\tpublic int getPveDamageWindowEndMs() {\n\t\treturn PVEEDAMAGEWINDOWENDMS;\n\t}"
              + "\n\tpublic Double getPvpPower() {\n\t\treturn PVPPOWER;\n\t}"
              + "\n\tpublic Byte getPvpEnergy() {\n\t\treturn PVPENERGY;\n\t}"
              + "\n\tpublic Double getPvpDPE() {\n\t\treturn PVPDPE;\n\t}"
              + "\n\tpublic Double getPvpBuffChance() {\n\t\treturn PVPBUFFCHANCE;\n\t}"
              + "\n\tpublic Byte getPvpBuffAttackerAttack() {\n\t\treturn PVPBUFFATTACKERATTACK;\n\t}"
              + "\n\tpublic Byte getPvpBuffAttackerDefense() {\n\t\treturn PVPBUFFATTACKERDEFENSE;\n\t}"
              + "\n\tpublic Byte getPvpBuffTargetAttack() {\n\t\treturn PVPBUFFTARGETATTACK;\n\t}"
              + "\n\tpublic Byte getPvpBuffTargetDefense() {\n\t\treturn PVPBUFFTARGETDEFENSE;\n\t}");

      writer.print("\n}");
      writer.flush();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // write Moves Fast
  public static void writeMovesFastEnum() {
    try {
      String string0;
      writer = new PrintWriter("src\\pokemans\\core\\MovesFast.java");

      builder0 = new StringBuilder(fileHeader);

      String movesFastVariables =
          "\n\tprivate final String MOVENUM, MOVENAME;\n"
              + "\tprivate final Type MOVETYPE;\n"
              + "\tprivate final Double PVEPOWER, PVPPOWER, PVPDPT, PVPEPT, PVPDPTEPT;\n"
              + "\tprivate final Byte PVEENERGY, PVPENERGY, PVPTURNS;\n"
              + "\tprivate final int PVEDURATIONMS, PVEDAMAGEWINDOWSTARTMS, PVEEDAMAGEWINDOWENDMS;\n";

      builder0.append("\npublic enum MovesFast {\n\n\t");
      writer.print(builder0);

      for (int writeCounter = 1;
          writeCounter < MovesFastUtility.getMoveNum().size();
          writeCounter++) {
        if (!MovesFastUtility.getMoveName().get(writeCounter).equals("HIDDEN_POWER_FAST")) {
          builder1 =
              new StringBuilder(MovesFastUtility.getMoveName().get(writeCounter))
                  .append("(FastMove.")
                  .append(MovesFastUtility.getMoveName().get(writeCounter))
                  .append(")");
          if (writeCounter != (MovesFastUtility.getMoveNum().size() - 1)) {
            builder1.append(",\n\t");
          } else {
            builder1.append(";\n");
          }
          writer.print(builder1);
        } else if (MovesFastUtility.getMoveName().get(writeCounter).equals("HIDDEN_POWER_FAST")) {
          for (Type type : Type.values()) {
            String hpName = "HIDDEN_POWER_" + type.getTYPENAME().substring(13) + "_FAST";
            builder1 = new StringBuilder(hpName).append("(FastMove.").append(hpName).append(")");
            if (!type.equals(Type.values().length - 1)) {
              builder1.append(",\n\t");
            } else {
              builder1.append(";\n");
            }
            writer.print(builder1);
          }
        }
      }

      writer.print("\n\tprivate enum FastMove {\n");

      // write each fast move with stats
      for (int writeCounter = 1;
          writeCounter < MovesFastUtility.getMoveNum().size();
          writeCounter++) {
        if (!MovesFastUtility.getMoveName().get(writeCounter).equals("HIDDEN_POWER_FAST")) {
          writeEachFastMove(writeCounter, MovesFastUtility.getMoveType().get(writeCounter));
        } else if (MovesFastUtility.getMoveName().get(writeCounter).equals("HIDDEN_POWER_FAST")) {
          for (Type type : Type.values()) {
            writeEachFastMove(writeCounter, type.getTYPENAME());
          }
        }
      }

      writer.print(movesFastVariables);
      writer.print(
          "\n\tFastMove (String moveNum, String moveName, Type moveType, Double pvePower, Byte pveEnergy, int pveDurationMs,\n"
              + "\t\t\t  int pveDamageWindowStartMs, int pveDamageWindowEndMs, Double pvpPower, Byte pvpEnergy, Byte pvpTurns,\n"
              + "\t\t\t   Double pvpDPT, Double pvpEPT, Double pvpDPTEPT) {\n"
              + "\t\tMOVENUM = moveNum;\n"
              + "\t\tMOVENAME = moveName;\n"
              + "\t\tMOVETYPE = moveType;\n"
              + "\t\tPVEPOWER = pvePower;\n"
              + "\t\tPVEENERGY = pveEnergy;\n"
              + "\t\tPVEDURATIONMS = pveDurationMs;\n"
              + "\t\tPVEDAMAGEWINDOWSTARTMS = pveDamageWindowStartMs;\n"
              + "\t\tPVEEDAMAGEWINDOWENDMS = pveDamageWindowEndMs;\n"
              + "\t\tPVPPOWER = pvpPower;\n"
              + "\t\tPVPENERGY = pvpEnergy;\n"
              + "\t\tPVPTURNS = pvpTurns;\n"
              + "\t\tPVPDPT = pvpDPT;\n"
              + "\t\tPVPEPT = pvpEPT;\n"
              + "\t\tPVPDPTEPT = pvpDPTEPT;\n"
              + "\t\t}\n\t}\n");

      writer.print(movesFastVariables);
      writer.print(
          "\n\tMovesFast (FastMove fm) {\n"
              + "\t\tMOVENUM = fm.MOVENUM;\n"
              + "\t\tMOVENAME = fm.MOVENAME;\n"
              + "\t\tMOVETYPE = fm.MOVETYPE;\n"
              + "\t\tPVEPOWER = fm.PVEPOWER;\n"
              + "\t\tPVEENERGY = fm.PVEENERGY;\n"
              + "\t\tPVEDURATIONMS = fm.PVEDURATIONMS;\n"
              + "\t\tPVEDAMAGEWINDOWSTARTMS = fm.PVEDAMAGEWINDOWSTARTMS;\n"
              + "\t\tPVEEDAMAGEWINDOWENDMS = fm.PVEEDAMAGEWINDOWENDMS;\n"
              + "\t\tPVPPOWER = fm.PVPPOWER;\n"
              + "\t\tPVPENERGY = fm.PVPENERGY;\n"
              + "\t\tPVPTURNS = fm.PVPTURNS;\n"
              + "\t\tPVPDPT = fm.PVPDPT;\n"
              + "\t\tPVPEPT = fm.PVPEPT;\n"
              + "\t\tPVPDPTEPT = fm.PVPDPTEPT;\n"
              + "\t}\n\n");

      writer.print(
          "\tpublic String getMoveNum() {\n"
              + "\t\treturn MOVENUM;\n"
              + "\t}\n"
              + "\tpublic String getMoveName() {\n"
              + "\t\treturn MOVENAME;\n"
              + "\t}\n"
              + "\tpublic Type getMoveType() {\n"
              + "\t\treturn MOVETYPE;\n"
              + "\t}\n"
              + "\tpublic Double getPvePower() {\n"
              + "\t\treturn PVEPOWER;\n"
              + "\t}\n"
              + "\tpublic Byte getPveEnergy() {\n"
              + "\t\treturn PVEENERGY;\n"
              + "\t}\n"
              + "\tpublic int getPveDurationMs() {\n"
              + "\t\treturn PVEDURATIONMS;\n"
              + "\t}\n"
              + "\tpublic int getPveDamageWindowStartMs() {\n"
              + "\t\treturn PVEDAMAGEWINDOWSTARTMS;\n"
              + "\t}\n"
              + "\tpublic int getPveDamageWindowEndMs() {\n"
              + "\t\treturn PVEEDAMAGEWINDOWENDMS;\n"
              + "\t}\n"
              + "\tpublic Double getPvpPower() {\n"
              + "\t\treturn PVPPOWER;\n"
              + "\t}\n"
              + "\tpublic Byte getPvpEnergy() {\n"
              + "\t\treturn PVPENERGY;\n"
              + "\t}\n"
              + "\tpublic Byte getPvpTurns() {\n"
              + "\t\treturn PVPTURNS;\n"
              + "\t}\n"
              + "\tpublic Double getPvpDPT() {\n"
              + "\t\treturn PVPDPT;}\n"
              + "\tpublic Double getPvpEPT() {\n"
              + "\t\treturn PVPEPT;\n"
              + "\t}\n"
              + "\tpublic Double getPvpDPTEPT() {\n"
              + "\t\treturn PVPDPTEPT;\n"
              + "\t}\n");

      writer.print("\n\t}");
      writer.flush();
      writer.close();
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    }
  }

  public static void writeEachFastMove(Integer writeCounter, String type) {
    BigDecimal bdDPT =
        (new BigDecimal(MovesFastUtility.getPvpPower().get(writeCounter).toString()))
            .divide(
                new BigDecimal(MovesFastUtility.getPvpTurns().get(writeCounter).toString()),
                8,
                RoundingMode.DOWN);
    double dpt = bdDPT.doubleValue();

    BigDecimal bdEPT =
        (new BigDecimal(MovesFastUtility.getPvpEnergy().get(writeCounter).toString()))
            .divide(
                new BigDecimal(MovesFastUtility.getPvpTurns().get(writeCounter).toString()),
                8,
                RoundingMode.DOWN);
    double ept = bdEPT.doubleValue();

    BigDecimal bdDPEEPT = bdDPT.multiply(bdEPT);
    double dptEPT = bdDPEEPT.doubleValue();

    if (!MovesFastUtility.getMoveName().get(writeCounter).equals("HIDDEN_POWER_FAST")) {
      builder1 =
          new StringBuilder("\n\t" + MovesFastUtility.getMoveName().get(writeCounter))
              .append("(")
              .append("\"")
              .append(MovesFastUtility.getMoveNum().get(writeCounter))
              .append("\", ")
              .append("\"")
              .append(MovesFastUtility.getMoveName().get(writeCounter))
              .append("\", ");
    } else if (MovesFastUtility.getMoveName().get(writeCounter).equals("HIDDEN_POWER_FAST")) {
      String hpName = "HIDDEN_POWER_" + type.substring(13) + "_FAST";
      builder1 =
          new StringBuilder("\n\t" + hpName)
              .append("(")
              .append("\"")
              .append(MovesFastUtility.getMoveNum().get(writeCounter))
              .append("\", ")
              .append("\"")
              .append(hpName)
              .append("\", ");
    }
    builder1
        .append("Type.")
        .append(type)
        .append(", ")
        .append(MovesFastUtility.getPvePower().get(writeCounter))
        .append(", ")
        .append("(byte) ")
        .append(MovesFastUtility.getPveEnergy().get(writeCounter))
        .append(", ")
        .append(MovesFastUtility.getPveDurationMs().get(writeCounter))
        .append(", ")
        .append(MovesFastUtility.getPveDamageWindowStartMs().get(writeCounter))
        .append(", ")
        .append(MovesFastUtility.getPveDamageWindowEndMs().get(writeCounter))
        .append(", ")
        .append(MovesFastUtility.getPvpPower().get(writeCounter))
        .append(", ")
        .append("(byte) ")
        .append(MovesFastUtility.getPvpEnergy().get(writeCounter))
        .append(", ")
        .append("(byte) ")
        .append(MovesFastUtility.getPvpTurns().get(writeCounter))
        .append(", ")
        .append(dpt)
        .append(", ")
        .append(ept)
        .append(", ")
        .append(dptEPT)
        .append(")");
    if (writeCounter != (MovesFastUtility.getMoveNum().size() - 1)) {
      writer.print(builder1.append(","));
    } else {
      writer.print(builder1.append(";\n"));
    }
  }

  // write Types
  public static void writeTypeEnum(List<TypeUtility> typeUtilityList) {
    try {
      PrintWriter writer = new PrintWriter("src\\pokemans\\core\\Type.java");

      builder0 = new StringBuilder(fileHeader);
      builder0.append("\nimport java.math.BigDecimal;" + "\n\npublic enum Type {\n");

      writer.print(builder0);

      writer.print(
          "\t// Is \"this much\" effective against: "
              + "\n\t// 0 = Normal"
              + "\t1 = Fighting"
              + "\t2 = Flying"
              + "\t3 = Poison"
              + "\t4 = Ground"
              + "\n\t// 5 = Rock"
              + "\t 6 = Bug"
              + "\t7 = Ghost"
              + "\t 8 = Steel"
              + "\t9 = Fire"
              + "\n\t// 10 = Water"
              + "\t11 = Grass"
              + "\t12 = Electric"
              + "\t13 = Psychic"
              + "\t14 = Ice"
              + "\n\t//15 = Dragon"
              + "\t16 = Dark"
              + "\t17 = Fairy\n");

      // write main enum Types
      for (TypeUtility tc : typeUtilityList) {
        builder0 =
            new StringBuilder("\n\t")
                .append(tc.getTypeEffectivenessName())
                .append("(\"")
                .append(tc.getTypeEffectivenessName())
                .append("\", new BigDecimal(\"")
                .append(tc.getEffectiveness().get(0).toString().replace("[", "").replace("]", ""))
                .append("\"), ");
        for (int types = 1; types < 18; types++) {
          if (types != 17) {
            if (types != 4 && types != 9 && types != 14) {
              builder0
                  .append("new BigDecimal(\"")
                  .append(
                      tc.getEffectiveness().get(types).toString().replace("[", "").replace("]", ""))
                  .append("\"), ");
            } else if (types == 4 || types == 9 || types == 14) {
              builder0
                  .append("new BigDecimal(\"")
                  .append(
                      tc.getEffectiveness().get(types).toString().replace("[", "").replace("]", ""))
                  .append("\"),\n\t\t\t\t\t\t\t\t  ");
            }
          } else {
            builder0
                .append("new BigDecimal(\"")
                .append(
                    tc.getEffectiveness().get(types).toString().replace("[", "").replace("]", ""));
          }
        }
        builder0.append("\"))");
        if (tc != typeUtilityList.get(typeUtilityList.size() - 1)) {
          writer.print(builder0.append(","));
        } else {
          writer.print(builder0.append(";"));
        }
      }
      builder1 =
          new StringBuilder(
                  "\n\n\tprivate final BigDecimal NORMAL, FIGHTING, FLYING, POISON, GROUND, ROCK, BUG, GHOST, STEEL, FIRE,\n")
              .append("\t\t\tWATER, GRASS, ELECTRIC, PSYCHIC, ICE, DRAGON, DARK, FAIRY;\n")
              .append("\tprivate final String TYPENAME;\n")
              .append(
                  "\n\tType(String TYPENAME, BigDecimal NORMAL, BigDecimal FIGHTING, BigDecimal FLYING, BigDecimal POISON,\n")
              .append(
                  "\t\t BigDecimal GROUND, BigDecimal ROCK, BigDecimal BUG, BigDecimal GHOST, BigDecimal STEEL, BigDecimal FIRE,\n")
              .append(
                  "\t\t BigDecimal WATER, BigDecimal GRASS, BigDecimal ELECTRIC, BigDecimal PSYCHIC, BigDecimal ICE,\n")
              .append("\t\t BigDecimal DRAGON, BigDecimal DARK, BigDecimal FAIRY) {\n")
              .append("\t\tthis.TYPENAME = TYPENAME;\n")
              .append("\t\tthis.NORMAL = NORMAL;\n")
              .append("\t\tthis.FIGHTING = FIGHTING;\n")
              .append("\t\tthis.FLYING = FLYING;\n")
              .append("\t\tthis.POISON = POISON;\n")
              .append("\t\tthis.GROUND = GROUND;\n")
              .append("\t\tthis.ROCK = ROCK;\n")
              .append("\t\tthis.BUG = BUG;\n")
              .append("\t\tthis.GHOST = GHOST;\n")
              .append("\t\tthis.STEEL = STEEL;\n")
              .append("\t\tthis.FIRE = FIRE;\n")
              .append("\t\tthis.WATER = WATER;\n")
              .append("\t\tthis.GRASS = GRASS;\n")
              .append("\t\tthis.ELECTRIC = ELECTRIC;\n")
              .append("\t\tthis.PSYCHIC = PSYCHIC;\n")
              .append("\t\tthis.ICE = ICE;\n")
              .append("\t\tthis.DRAGON = DRAGON;\n")
              .append("\t\tthis.DARK = DARK;\n")
              .append("\t\tthis.FAIRY = FAIRY;\n\t}\n");

      builder1
          .append("\n\tpublic String getTYPENAME() { return TYPENAME; }\n")
          .append("\tpublic BigDecimal getNORMAL() { return NORMAL; }\n")
          .append("\tpublic BigDecimal getFIGHTING() { return FIGHTING; }\n")
          .append("\tpublic BigDecimal getFLYING() { return FLYING; }\n")
          .append("\tpublic BigDecimal getPOISON() { return POISON; }\n")
          .append("\tpublic BigDecimal getGROUND() { return GROUND; }\n")
          .append("\tpublic BigDecimal getROCK() { return ROCK; }\n")
          .append("\tpublic BigDecimal getBUG() { return BUG; }\n")
          .append("\tpublic BigDecimal getGHOST() { return GHOST; }\n")
          .append("\tpublic BigDecimal getSTEEL() { return STEEL; }\n")
          .append("\tpublic BigDecimal getFIRE() { return FIRE; }\n")
          .append("\tpublic BigDecimal getWATER() { return WATER; }\n")
          .append("\tpublic BigDecimal getGRASS() { return GRASS; }\n")
          .append("\tpublic BigDecimal getELECTRIC() { return ELECTRIC; }\n")
          .append("\tpublic BigDecimal getPSYCHIC() { return PSYCHIC; }\n")
          .append("\tpublic BigDecimal getICE() { return ICE; }\n")
          .append("\tpublic BigDecimal getDRAGON() { return DRAGON; }\n")
          .append("\tpublic BigDecimal getDARK() { return DARK; }\n")
          .append("\tpublic BigDecimal getFAIRY() { return FAIRY; }\n" + "\n");

      builder1
          .append("\n\tpublic static Type stringToType(String inputType) {\n")
          .append("\t\tType thisType = null;\n")
          .append("\t\tswitch (inputType) {\n")
          .append("\t\t\tcase \"POKEMON_TYPE_BUG\" -> thisType = Type.POKEMON_TYPE_BUG;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_DARK\" -> thisType = Type.POKEMON_TYPE_DARK;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_DRAGON\" -> thisType = Type.POKEMON_TYPE_DRAGON;\n")
          .append(
              "\t\t\tcase \"POKEMON_TYPE_ELECTRIC\" -> thisType = Type.POKEMON_TYPE_ELECTRIC;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_FAIRY\" -> thisType = Type.POKEMON_TYPE_FAIRY;\n")
          .append(
              "\t\t\tcase \"POKEMON_TYPE_FIGHTING\" -> thisType = Type.POKEMON_TYPE_FIGHTING;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_FIRE\" -> thisType = Type.POKEMON_TYPE_FIRE;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_FLYING\" -> thisType = Type.POKEMON_TYPE_FLYING;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_GHOST\" -> thisType = Type.POKEMON_TYPE_GHOST;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_GRASS\" -> thisType = Type.POKEMON_TYPE_GRASS;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_GROUND\" -> thisType = Type.POKEMON_TYPE_GROUND;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_ICE\" -> thisType = Type.POKEMON_TYPE_ICE;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_NORMAL\" -> thisType = Type.POKEMON_TYPE_NORMAL;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_POISON\" -> thisType = Type.POKEMON_TYPE_POISON;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_PSYCHIC\" -> thisType = Type.POKEMON_TYPE_PSYCHIC;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_ROCK\" -> thisType = Type.POKEMON_TYPE_ROCK;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_STEEL\" -> thisType = Type.POKEMON_TYPE_STEEL;\n")
          .append("\t\t\tcase \"POKEMON_TYPE_WATER\" -> thisType = Type.POKEMON_TYPE_WATER;\n")
          .append("\t\t\tdefault -> { return thisType;\n\t\t\t}\n\t\t}\n\t\treturn thisType;\n\t}");

      writer.print(builder1);

      writer.print("\n}");
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // write CP Multiplier interface
  public static void writeCPMultiplierEnum(List<BigDecimal> cpMultiplier) {
    try {
      PrintWriter writer = new PrintWriter("src\\pokemans\\engine\\CPMultiplier.java");

      writer.print(
          "/*\n * Copyright (c) 2020, Derek Fawcett. All rights reserved. "
              + "No usage without permission. */\n\n\t// **This file is auto-generated**"
              + "\n\npackage pokemans.engine;\n"
              + "\nimport java.math.BigDecimal;"
              + "\n\ninterface CPMultiplier {\n");

      // write main enum cpMultiplier
      writer.print("\n\tstatic final BigDecimal[] levels = {");
      BigDecimal two = new BigDecimal("2");

      int indexCounter = 0;
      for (int i = 0; i < cpMultiplier.size(); i++) {
        builder1 =
            new StringBuilder("\n\t\tnew BigDecimal(\"").append(cpMultiplier.get(i)).append("\")");
        if (i != cpMultiplier.size() - 1) {
          writer.print(
              builder1
                  .append(",\t// index [")
                  .append(indexCounter)
                  .append("], level ")
                  .append((i + 1)));
        } else {
          writer.print(
              builder1
                  .append("};\t// index [")
                  .append(indexCounter)
                  .append("], level ")
                  .append((i + 1)));
          break;
        }

        // for half-level
        BigDecimal halfLevel =
            (((new BigDecimal(String.valueOf(cpMultiplier.get(i))))
                        .add(new BigDecimal(String.valueOf(cpMultiplier.get(i + 1)))))
                    .divide(two))
                .setScale(8, RoundingMode.DOWN);
        builder1 = new StringBuilder("\n\t\tnew BigDecimal(\"").append(halfLevel).append("\")");
        if (i != cpMultiplier.size() - 1) {
          writer.print(
              builder1
                  .append(",\t// index [")
                  .append(indexCounter)
                  .append("], level ")
                  .append(i + 1)
                  .append(".5"));
          indexCounter++;
        }
        indexCounter++;
      }

      writer.print("\n}");
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // write battle multiplier interface
  public static void writeBattleMultipliers(TreeMap<String, Double> battleMultipliers) {
    try {
      PrintWriter writer = new PrintWriter("src\\pokemans\\engine\\BattleMultipliers.java");

      writer.print(
          "/*\n * Copyright (c) 2020, Derek Fawcett. All rights reserved. "
              + "No usage without permission. */\n\n\t// **This file is auto-generated** "
              + "\n\npackage pokemans.engine;\n"
              + "\nimport java.math.BigDecimal;"
              + "\n\ninterface BattleMultipliers {\n");

      Set<String> keys = battleMultipliers.keySet();

      for (String key : keys) {
        if (!key.equals("attackBonusMultiplier")) {
          writer.print(
              "\n\tstatic final BigDecimal "
                  + key
                  + " = new BigDecimal(\""
                  + battleMultipliers.get(key)
                  + "\");");
        } else {
          writer.print(
              "\n\tstatic final BigDecimal weather"
                  + key
                  + " = new BigDecimal(\""
                  + battleMultipliers.get(key)
                  + "\");");
        }
      }

      writer.print("\n\n}");
      writer.flush();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // write battle multiplier interface
  public static void writeCombatMultipliers(
      TreeMap<String, Double> combatMultipliers,
      TreeMap<String, List<Double>> combatStageMultipliers) {
    try {
      PrintWriter writer = new PrintWriter("src\\pokemans\\engine\\CombatMultipliers.java");

      writer.print(
          "/*\n * Copyright (c) 2020, Derek Fawcett. All rights reserved. "
              + "No usage without permission. */\n\n\t// **This file is auto-generated** "
              + "\n\npackage pokemans.engine;\n"
              + "\nimport java.math.BigDecimal;"
              + "\n\ninterface CombatMultipliers {\n");

      Set<String> keys = combatMultipliers.keySet();
      for (String key : keys) {
        writer.print(
            "\n\tstatic final BigDecimal "
                + key
                + " = new BigDecimal(\""
                + combatMultipliers.get(key)
                + "\");");
      }

      Set<String> keysStages = combatStageMultipliers.keySet();
      for (String key : keysStages) {
        if (!key.equals("attackBuffMultiplier") && !key.equals("defenseBuffMultiplier")) {
          writer.print(
              "\n\tstatic final BigDecimal "
                  + key
                  + " = new BigDecimal(\""
                  + combatStageMultipliers.get(key).toString().replace("[", "").replace("]", "")
                  + "\");");
        } else {
          writer.print("\n\tstatic final BigDecimal[] " + key + " = {");
          for (Double values : combatStageMultipliers.get(key)) {
            StringBuilder builder1 =
                new StringBuilder("new BigDecimal(\"")
                    .append(values.toString().replace("[", "").replace("]", ""))
                    .append("\")");

            if (!values.equals(
                combatStageMultipliers.get(key).get(combatStageMultipliers.get(key).size() - 1))) {
              builder1.append(", ");
            } else {
              builder1.append("}; ");
            }
            writer.print(builder1);
          }
        }
      }

      writer.print("\n\n}");
      writer.flush();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // write battle multiplier interface
  public static void writeWeatherMultipliers(TreeMap<String, Double> weatherMultipliers) {
    try {
      PrintWriter writer = new PrintWriter("src\\pokemans\\engine\\WeatherMultipliers.java");

      writer.print(
          "/*\n * Copyright (c) 2020, Derek Fawcett. All rights reserved. "
              + "No usage without permission. */\n\n\t// **This file is auto-generated** "
              + "\n\npackage pokemans.engine;\n"
              + "\nimport java.math.BigDecimal;"
              + "\n\ninterface WeatherMultipliers {\n");

      Set<String> keys = weatherMultipliers.keySet();
      for (String key : keys) {
        if (!key.equals("attackBonusMultiplier")) {
          writer.print(
              "\n\tstatic final BigDecimal "
                  + key
                  + " = new BigDecimal(\""
                  + weatherMultipliers.get(key)
                  + "\");");
        } else {
          writer.print(
              "\n\tstatic final BigDecimal weather"
                  + key
                  + " = new BigDecimal(\""
                  + weatherMultipliers.get(key)
                  + "\");");
        }
      }

      writer.print("\n\n}");
      writer.flush();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // write Game Master Check
  public static void writeGameMasterCheckClass(String fileName) {
    try {
      PrintWriter writer = new PrintWriter("src\\pokemans\\utilities\\read\\GameMasterCheck.java");
      // extract substring to avoid problems with escape characters
      String justFileName = fileName.substring(14, 42);

      // writer.print(
      String string0 =
          "/*\n"
              + " Copyright (c) 2020, My Name. All rights reserved. No usage without permission.\n"
              + "*/\n"
              + "\n"
              + "package pokemans.utilities.read;\n"
              + "\n"
              + "import java.io.*;\n"
              + "import java.net.URL;\n"
              + "import java.security.MessageDigest;\n"
              + "import java.security.NoSuchAlgorithmException;\n"
              + "import java.time.LocalDate;\n"
              + "import java.time.ZoneId;\n"
              + "import java.time.format.DateTimeFormatter;\n"
              + "\n"
              + "import static java.time.temporal.ChronoUnit.DAYS;\n"
              + "\n"
              + "public class GameMasterCheck {\n"
              + "\n"
              + "  // set date format pattern\n"
              + "  private static final DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern(\"yyyyMMdd\");\n"
              + "\n"
              + "  public static String checkFile(String gameMasterURL) {\n"
              + "    String existingFileName = \"src\\\\dataFiles\\\\";
      String string1 =
          "\";\n"
              + "    try {\n"
              + "      File tmpDir = new File(existingFileName);\n"
              + "      boolean fileExists = tmpDir.exists();\n"
              + "\n"
              + "      // check # of days between current date and date from game master\n"
              + "      long noOfDaysBetween = calculateDaysBetween(existingFileName);\n"
              + "      if (noOfDaysBetween > 1) {\n"
              + "        ZoneId americaNY = ZoneId.of(\"America/New_York\");\n"
              + "        String newFileName = (timeStampPattern.format(java.time.LocalDateTime.now(americaNY)));\n"
              + "        newFileName = \"src\\\\dataFiles\\\\GAME_MASTER_V2_\" + newFileName + \".json\";\n"
              + "\n"
              + "        MessageDigest md = MessageDigest.getInstance(\"MD5\");\n"
              + "\n"
              + "        String hexFile = \"\", hexURL = \"\";\n"
              + "\n"
              + "        // if no game master currently exists, create file\n"
              + "        if (!fileExists) {\n"
              + "          writeNewFile(fileExists, gameMasterURL, existingFileName, newFileName);\n"
              + "        } else {\n"
              + "          hexFile = checksumFile(existingFileName, md);\n"
              + "          hexURL = checksumURL(gameMasterURL, md);\n"
              + "\n"
              + "          // if checksums are different, overwrite file\n"
              + "          if (!hexFile.equals(hexURL)) {\n"
              + "            writeNewFile(fileExists, gameMasterURL, existingFileName, newFileName);\n"
              + "            return newFileName;\n"
              + "          }\n"
              + "        }\n"
              + "      }\n"
              + "    } catch (NoSuchAlgorithmException e) {\n"
              + "      e.printStackTrace();\n"
              + "    }\n"
              + "    // JOptionPane.showMessageDialog(null, \"PoGo Game Master reading complete!\");\n"
              + "    return existingFileName;\n"
              + "  }\n"
              + "\n"
              + "  private static String checksumFile(String fileName, MessageDigest md) {\n"
              + "    StringBuilder result = new StringBuilder();\n"
              + "    int nread;\n"
              + "    byte[] buffer = new byte[1024];\n"
              + "\n"
              + "    try (InputStream fis = new FileInputStream(fileName)) {\n"
              + "      while ((nread = fis.read(buffer)) != -1) {\n"
              + "        md.update(buffer, 0, nread);\n"
              + "      }\n"
              + "    } catch (IOException e) {\n"
              + "      e.printStackTrace();\n"
              + "    }\n"
              + "    // bytes to hex\n"
              + "    for (byte b : md.digest()) {\n"
              + "      result.append(String.format(\"%02x\", b));\n"
              + "    }\n"
              + "    return result.toString();\n"
              + "  }\n"
              + "\n"
              + "  private static String checksumURL(String fileName, MessageDigest md) {\n"
              + "    StringBuilder result = new StringBuilder();\n"
              + "    int nread;\n"
              + "    byte[] buffer = new byte[1024];\n"
              + "\n"
              + "    try (InputStream urlis = new URL(fileName).openStream()) {\n"
              + "      while ((nread = urlis.read(buffer)) != -1) {\n"
              + "        md.update(buffer, 0, nread);\n"
              + "      }\n"
              + "    } catch (IOException e) {\n"
              + "      e.printStackTrace();\n"
              + "    }\n"
              + "    // bytes to hex\n"
              + "    for (byte b : md.digest()) {\n"
              + "      result.append(String.format(\"%02x\", b));\n"
              + "    }\n"
              + "    return result.toString();\n"
              + "  }\n"
              + "\n"
              + "  private static long calculateDaysBetween(String fileName) {\n"
              + "    // extract date string from current game master file name\n"
              + "    String dateFromFileName;\n"
              + "    dateFromFileName = fileName.substring(29, 37);\n"
              + "    LocalDate currentDate = LocalDate.now();\n"
              + "    // set LocalDate from \"dateFromFileName\"\n"
              + "    LocalDate dateFromCurrentGameMaster = LocalDate.parse(dateFromFileName, timeStampPattern);\n"
              + "\n"
              + "    // calculate days between now and date from game master\n"
              + "    return DAYS.between(dateFromCurrentGameMaster, currentDate);\n"
              + "  }\n"
              + "\n"
              + "  private static void writeNewFile(\n"
              + "      boolean fileExists, String gameMasterUrl, String existingFileName, String newFileName) {\n"
              + "    try (BufferedInputStream in = new BufferedInputStream(new URL(gameMasterUrl).openStream());\n"
              + "        // new file name\n"
              + "        FileOutputStream fileOutputStream = new FileOutputStream(newFileName)) {\n"
              + "      byte[] dataBuffer = new byte[1024];\n"
              + "      int bytesRead;\n"
              + "      while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {\n"
              + "        fileOutputStream.write(dataBuffer, 0, bytesRead);\n"
              + "      }\n"
              + "\n"
              + "      // delete old/existing file\n"
              + "      if (fileExists) {\n"
              + "        File file = new File(existingFileName);\n"
              + "        if (file.delete()) {\n"
              + "          System.out.println(\"\\n\\t\\t**Old Game Master deleted successfully**\\n\");\n"
              + "        }\n"
              + "      }\n"
              + "    } catch (IOException e) {\n"
              + "      System.out.println(\"\\nGame Master Check IO Exception!\\n\");\n"
              + "      e.printStackTrace();\n"
              + "    }\n"
              + "  }\n"
              + "}";
      builder0 = new StringBuilder(string0).append(justFileName).append(string1);
      writer.print(builder0);
      writer.flush();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
