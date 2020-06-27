/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */
package pokemans.utilities.read;

import java.util.ArrayList;

public class PokeFormsUtility {
  private int formDexNum;
  private ArrayList<String> formName;

  public PokeFormsUtility(int formDexNum, ArrayList<String> formName) {
    this.formDexNum = formDexNum;
    this.formName = formName;
  }

  @Override
  public String toString() {
    return "\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
        + "\nForm Dex Num: "
        + formDexNum
        + "\nForm Name: "
        + formName;
  }

  public int getFormDexNum() {
    return formDexNum;
  }

  public void setFormDexNum(short formDexNum) {
    this.formDexNum = formDexNum;
  }

  public ArrayList<String> getFormName() {
    return formName;
  }

  public void setFormName(ArrayList<String> formName) {
    this.formName = formName;
  }
}
