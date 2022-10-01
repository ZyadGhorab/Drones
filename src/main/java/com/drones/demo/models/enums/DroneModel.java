package com.drones.demo.models.enums;

public enum DroneModel {
  LightWeight(Values.LightWeight),
  MiddleWeight(Values.MiddleWeight),
  CruiserWeight(Values.CruiserWeight),
  HeavyWeight(Values.HeavyWeight);

  DroneModel(String val) {
    // force equality between name of enum instance, and value of constant
    if (!this.name().equals(val)) throw new IllegalArgumentException("Incorrect use of ELanguage");
  }

  public static class Values {
    public static String LightWeight = "LightWeight";
    public static final String MiddleWeight = "MiddleWeight";
    public static final String CruiserWeight = "CruiserWeight";
    public static final String HeavyWeight = "HeavyWeight";
  }
}
