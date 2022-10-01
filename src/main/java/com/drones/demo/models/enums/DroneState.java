package com.drones.demo.models.enums;

public enum DroneState {
  IDLE(Values.IDLE),
  LOADING(Values.LOADING),
  LOADED(Values.LOADED),
  DELIVERING(Values.DELIVERING),
  DELIVERED(Values.DELIVERED),
  RETURNING(Values.RETURNING);

  DroneState(String val) {
    // force equality between name of enum instance, and value of constant
    if (!this.name().equals(val)) throw new IllegalArgumentException("Incorrect use of ELanguage");
  }

  public static class Values {
    public static final String IDLE = "IDLE";
    public static final String LOADING = "LOADING";
    public static final String LOADED = "LOADED";
    public static final String DELIVERING = "DELIVERING";
    public static final String DELIVERED = "DELIVERED";
    public static final String RETURNING = "RETURNING";
  }
}
