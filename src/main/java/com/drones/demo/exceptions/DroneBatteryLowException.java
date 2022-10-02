package com.drones.demo.exceptions;

public class DroneBatteryLowException extends Exception {
  public DroneBatteryLowException(String message) {
    super(message);
  }
}
