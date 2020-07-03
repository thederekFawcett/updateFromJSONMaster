/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */
package pokemans.utilities.read;

import java.util.List;

public class SmeargleMovesUtility {

  private static List<String> smeargleQuickMoves, smeargleCinematicMoves;

  public static List<String> getSmeargleQuickMoves() {
    return smeargleQuickMoves;
  }

  public static void setSmeargleQuickMoves(List<String> smeargleQuickMoves) {
    SmeargleMovesUtility.smeargleQuickMoves = smeargleQuickMoves;
  }

  public static List<String> getSmeargleCinematicMoves() {
    return smeargleCinematicMoves;
  }

  public static void setSmeargleCinematicMoves(List<String> smeargleCinematicMoves) {
    SmeargleMovesUtility.smeargleCinematicMoves = smeargleCinematicMoves;
  }
}
