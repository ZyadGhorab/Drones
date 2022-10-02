package com.drones.demo.repositeries;

import com.drones.demo.models.Drone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DroneRepository extends CrudRepository<Drone, Long> {
  List<Drone> findByStateLike(String state);
}
