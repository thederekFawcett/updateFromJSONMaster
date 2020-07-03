/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */
package pokemans.utilities.read;

import java.util.ArrayList;
import java.util.List;

public class TypeUtility {

  private String typeEffectivenessName;
  private List<Double> effectiveness;

  public TypeUtility(String typeEffectivenessName, List<Double> effectiveness) {
    this.typeEffectivenessName = typeEffectivenessName;
    this.effectiveness = effectiveness;
  }

  @Override
  public String toString() {
    return ( // "\n++++++++++++++++++++++++++++++++++++++++++++" +
    "\nType Name: "
        + this.getTypeEffectivenessName()
        + "\nEffectiveness: "
        + this.getEffectiveness());
  }

  public String getTypeEffectivenessName() {
    return typeEffectivenessName;
  }

  public void setTypeEffectivenessName(String typeEffectivenessName) {
    this.typeEffectivenessName = typeEffectivenessName;
  }

  public List<Double> getEffectiveness() {
    return effectiveness;
  }

  public void setEffectiveness(ArrayList<Double> effectiveness) {
    this.effectiveness = effectiveness;
  }
}
