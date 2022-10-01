package com.drones.demo.services;

import com.drones.demo.models.Drone;
import com.drones.demo.models.enums.DroneModel;
import com.drones.demo.models.enums.DroneState;
import com.drones.demo.repositeries.DroneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DroneService {
  private final DroneRepository droneRepository;

  public DroneService(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  public Drone registerDrone(Drone drone) {
    return droneRepository.save(drone);
  }

  public List<Drone> getAllDrones() {
    List<Drone> registeredDrones = new ArrayList<>();
    droneRepository.findAll().forEach(registeredDrones::add);
    return registeredDrones;
  }

  public void getDronesByState() {}

  public void loadDrone() {
    droneRepository.save(new Drone(1L, "1", DroneModel.LightWeight, 500.0, 80.0, DroneState.IDLE));
    droneRepository.save(
        new Drone(2L, "2", DroneModel.MiddleWeight, 500.0, 80.0, DroneState.LOADING));
    droneRepository.save(
        new Drone(3L, "3", DroneModel.CruiserWeight, 500.0, 80.0, DroneState.LOADED));
    droneRepository.save(
        new Drone(4L, "4", DroneModel.HeavyWeight, 500.0, 80.0, DroneState.DELIVERING));
    droneRepository.save(new Drone(5L, "5", DroneModel.LightWeight, 500.0, 80.0, DroneState.IDLE));
    droneRepository.save(
        new Drone(6L, "6", DroneModel.MiddleWeight, 500.0, 80.0, DroneState.LOADING));
    droneRepository.save(
        new Drone(7L, "7", DroneModel.CruiserWeight, 500.0, 80.0, DroneState.LOADED));
    droneRepository.save(
        new Drone(8L, "8", DroneModel.HeavyWeight, 500.0, 80.0, DroneState.DELIVERING));
    droneRepository.save(new Drone(9L, "9", DroneModel.LightWeight, 500.0, 80.0, DroneState.IDLE));
    droneRepository.save(
        new Drone(10L, "10", DroneModel.MiddleWeight, 500.0, 80.0, DroneState.LOADING));
    droneRepository.save(
        new Drone(11L, "11", DroneModel.CruiserWeight, 500.0, 80.0, DroneState.LOADED));
    droneRepository.save(
        new Drone(12L, "12", DroneModel.HeavyWeight, 500.0, 80.0, DroneState.DELIVERING));
  }
}
