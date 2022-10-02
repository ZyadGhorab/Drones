package com.drones.demo.models;

import com.drones.demo.models.enums.DroneModel;
import com.drones.demo.models.enums.DroneState;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
@Builder
@Entity
@Table(name = "Drone")
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String serialNumber;
  private DroneModel model;
  private Double weightLimitInGrams;
  private Double batteryPercentage;
  private DroneState state;

  @JsonManagedReference
  @OneToMany(mappedBy = "drone", fetch = FetchType.EAGER)
  private List<Medication> medications;

  public Drone(
      Long id,
      String serialNumber,
      DroneModel model,
      Double weightLimitInGrams,
      Double batteryPercentage,
      DroneState state) {
    this.id = id;
    this.serialNumber = serialNumber;
    this.model = model;
    this.weightLimitInGrams = weightLimitInGrams;
    this.batteryPercentage = batteryPercentage;
    this.state = state;
  }

  public Drone(
      Long id,
      String serialNumber,
      DroneModel model,
      Double weightLimitInGrams,
      Double batteryPercentage,
      DroneState state,
      List<Medication> medications) {
    this.id = id;
    this.serialNumber = serialNumber;
    this.model = model;
    this.weightLimitInGrams = weightLimitInGrams;
    this.batteryPercentage = batteryPercentage;
    this.state = state;
    this.medications = medications;
  }
}
