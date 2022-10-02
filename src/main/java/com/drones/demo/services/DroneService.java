package com.drones.demo.services;

import com.drones.demo.exceptions.DroneBatteryLowException;
import com.drones.demo.exceptions.DroneOverLoadedException;
import com.drones.demo.models.Drone;
import com.drones.demo.models.Medication;
import com.drones.demo.models.enums.DroneModel;
import com.drones.demo.models.enums.DroneState;
import com.drones.demo.repositeries.DroneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DroneService {
  private final DroneRepository droneRepository;

  public DroneService(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  public void loadDrone() {
    droneRepository.save(new Drone(1L, "1", DroneModel.LightWeight, 500.0, 100.0, DroneState.IDLE));
    droneRepository.save(
        new Drone(2L, "2", DroneModel.MiddleWeight, 500.0, 95.0, DroneState.LOADING));
    droneRepository.save(
        new Drone(3L, "3", DroneModel.CruiserWeight, 500.0, 90.0, DroneState.LOADED));
    droneRepository.save(
        new Drone(4L, "4", DroneModel.HeavyWeight, 500.0, 88.0, DroneState.DELIVERING));
    droneRepository.save(
        new Drone(5L, "5", DroneModel.LightWeight, 500.0, 80.0, DroneState.DELIVERED));
    droneRepository.save(
        new Drone(6L, "6", DroneModel.MiddleWeight, 500.0, 60.0, DroneState.RETURNING));
    droneRepository.save(
        new Drone(7L, "7", DroneModel.CruiserWeight, 500.0, 20.0, DroneState.IDLE));
    droneRepository.save(
        new Drone(8L, "8", DroneModel.HeavyWeight, 500.0, 40.0, DroneState.LOADING));
    droneRepository.save(
        new Drone(9L, "9", DroneModel.LightWeight, 500.0, 50.0, DroneState.LOADED));
    droneRepository.save(
        new Drone(10L, "10", DroneModel.MiddleWeight, 500.0, 36.0, DroneState.DELIVERING));
    droneRepository.save(
        new Drone(11L, "11", DroneModel.CruiserWeight, 500.0, 44.0, DroneState.DELIVERED));
    droneRepository.save(
        new Drone(12L, "12", DroneModel.HeavyWeight, 500.0, 77.0, DroneState.RETURNING));
  }

  public Drone registerDrone(Drone drone) {
    return droneRepository.save(drone);
  }

  public List<Drone> getAllDrones() {
    List<Drone> registeredDrones = new ArrayList<>();
    droneRepository.findAll().forEach(registeredDrones::add);
    return registeredDrones;
  }

  public Drone loadDroneWithMedication(String droneId, Medication medication)
      throws DroneOverLoadedException, DroneBatteryLowException {
    Optional<Drone> optionalDrone = droneRepository.findById(Long.valueOf(droneId));
    if (optionalDrone.isPresent()) {
      Drone drone = optionalDrone.get();

      checkIfDroneBatteryIsSufficient(droneId, drone);

      checkIfDroneIsOverWeighted(droneId, medication, drone);

      drone.setState(DroneState.LOADING);
      addMedicationToDrone(medication, drone);

      droneRepository.save(drone);
      return drone;
    } else {
      throw new IllegalArgumentException("Drone not found");
    }
  }

  private static void addMedicationToDrone(Medication medication, Drone drone) {
    List<Medication> medications = drone.getMedications();
    if (medications == null) {
      medications = new ArrayList<>();
    }
    medications.add(medication);
    drone.setMedications(medications);
  }

  private void checkIfDroneBatteryIsSufficient(String droneId, Drone drone)
      throws DroneBatteryLowException {
    if (drone.getBatteryPercentage() < 25) {
      throw new DroneBatteryLowException(
          String.format("Drone %s battery is low, please charge it", droneId));
    }
  }

  private static void checkIfDroneIsOverWeighted(String droneId, Medication medication, Drone drone)
      throws DroneOverLoadedException {
    List<Medication> medications = drone.getMedications();
    double currentTotalMedicationWeight =
        medications == null || medications.isEmpty()
            ? 0
            : medications.stream().mapToDouble(Medication::getWeightInGrams).sum();

    if (isDroneOverWeighted(medication, drone, currentTotalMedicationWeight)) {
      throw new DroneOverLoadedException(
          String.format(
              "Drone %s is overloaded with %s grams of medication",
              droneId, currentTotalMedicationWeight));
    }
  }

  private static boolean isDroneOverWeighted(
      Medication medication, Drone drone, double currentTotalMedicationWeight) {
    return currentTotalMedicationWeight + medication.getWeightInGrams()
        > drone.getWeightLimitInGrams();
  }

  public List<Medication> getDroneLoadedMedication(String droneId) {
    Optional<Drone> drone = droneRepository.findById(Long.valueOf(droneId));
    if (drone.isPresent()) {
      return drone.get().getMedications();
    } else {
      throw new IllegalArgumentException("Drone not found");
    }
  }

  public List<Drone> getAvailableDronesForLoading() {
    return droneRepository.findByStateLike(DroneState.IDLE.name());
  }

  public Double getDroneBatteryLevel(String droneId) {
    Optional<Drone> drone = droneRepository.findById(Long.valueOf(droneId));
    if (drone.isPresent()) {
      return drone.get().getBatteryPercentage();
    } else {
      throw new IllegalArgumentException("Drone not found");
    }
  }
}
