package com.drones.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Builder
@ToString
@Entity
@Table(name = "Medication")
public class Medication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double weightInGrams;
  private String code;
  private Byte[] image;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "droneId", insertable = false, updatable = false)
  private Drone drone;

  public Medication(
      Long id, String name, Double weightInGrams, String code, Byte[] image, Drone drone) {
    this.id = id;
    this.name = name;
    this.weightInGrams = weightInGrams;
    this.code = code;
    this.image = image;
    this.drone = drone;
  }
}
