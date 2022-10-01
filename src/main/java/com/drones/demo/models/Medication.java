package com.drones.demo.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@ToString
public class Medication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double weight;
  private String code;
  private Byte[] image;

  public Medication() {}

  public Medication(String name, Double weight, String code, Byte[] image) {
    this.name = name;
    this.weight = weight;
    this.code = code;
    this.image = image;
  }
}
