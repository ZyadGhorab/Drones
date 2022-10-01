package com.drones.demo.models;

import com.drones.demo.models.enums.DroneModel;
import com.drones.demo.models.enums.DroneState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String serialNumber;
  private DroneModel model;
  private Double weightLimitInGrams;
  private Double batteryCapacityPercentage;
  private DroneState state;

  @OneToMany private List<Medication> medications;

  public Drone() {}

  public Drone(
      Long id,
      String serialNumber,
      DroneModel model,
      Double weightLimitInGrams,
      Double batteryCapacityPercentage,
      DroneState state) {
    this.id = id;
    this.serialNumber = serialNumber;
    this.model = model;
    this.weightLimitInGrams = weightLimitInGrams;
    this.batteryCapacityPercentage = batteryCapacityPercentage;
    this.state = state;
  }
}
