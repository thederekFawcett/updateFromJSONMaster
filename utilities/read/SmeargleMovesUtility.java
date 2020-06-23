/************************************************
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 ************************************************/

package pokemans.utilities.read;

import java.util.ArrayList;

public class SmeargleMovesUtility {

    private static ArrayList<String> smeargleQuickMoves, smeargleCinematicMoves;

    public static ArrayList<String> getSmeargleQuickMoves() {
        return smeargleQuickMoves;
    }

    public static void setSmeargleQuickMoves(ArrayList<String> smeargleQuickMoves) {
        SmeargleMovesUtility.smeargleQuickMoves = smeargleQuickMoves;
    }

    public static ArrayList<String> getSmeargleCinematicMoves() {
        return smeargleCinematicMoves;
    }

    public static void setSmeargleCinematicMoves(ArrayList<String> smeargleCinematicMoves) {
        SmeargleMovesUtility.smeargleCinematicMoves = smeargleCinematicMoves;
    }
}
