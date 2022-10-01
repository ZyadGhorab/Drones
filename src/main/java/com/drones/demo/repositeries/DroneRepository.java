package com.drones.demo.repositeries;

import com.drones.demo.models.Drone;
import org.springframework.data.repository.CrudRepository;

public interface DroneRepository extends CrudRepository<Drone, Long> {}
