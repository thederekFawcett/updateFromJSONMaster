/************************************************
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 ************************************************/

package pokemans.utilities.read;

import java.util.ArrayList;

public class TypeUtility {

    private String typeEffectivenessName;
    private ArrayList<Double> effectiveness;

    public TypeUtility(String typeEffectivenessName, ArrayList<Double> effectiveness) {
        this.typeEffectivenessName = typeEffectivenessName;
        this.effectiveness = effectiveness;
    }

    @Override
    public String toString() {
        return (//"\n++++++++++++++++++++++++++++++++++++++++++++" +
                "\nType Name: " + this.getTypeEffectivenessName() +
                        "\nEffectiveness: " + this.getEffectiveness());
    }

    public String getTypeEffectivenessName() {
        return typeEffectivenessName;
    }

    public void setTypeEffectivenessName(String typeEffectivenessName) {
        this.typeEffectivenessName = typeEffectivenessName;
    }

    public ArrayList<Double> getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(ArrayList<Double> effectiveness) {
        this.effectiveness = effectiveness;
    }
}
