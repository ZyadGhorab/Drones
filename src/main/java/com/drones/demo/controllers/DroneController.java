package com.drones.demo.controllers;

import com.drones.demo.exceptions.DroneBatteryLowException;
import com.drones.demo.exceptions.DroneOverLoadedException;
import com.drones.demo.models.Drone;
import com.drones.demo.models.Medication;
import com.drones.demo.services.DroneService;
import com.drones.demo.validators.DroneSchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class DroneController {
  private final DroneService droneService;
  private final DroneSchemaValidator droneSchemaValidator;

  public DroneController(DroneService droneService) {
    this.droneService = droneService;
    this.droneSchemaValidator = new DroneSchemaValidator();
  }

  @PostMapping("/register")
  public ResponseEntity<Drone> registerDrone(@RequestBody Drone drone) throws IOException {

    JsonSchema jsonSchema = droneSchemaValidator.getJsonSchemaFromClasspath("drone-schema.json");
    JsonNode result =
        droneSchemaValidator.getJsonNodeFromStringContent(
            droneSchemaValidator.getMapper().writeValueAsString(drone));
    Set<ValidationMessage> errors = jsonSchema.validate(result);

    if (errors.isEmpty()) {
      try {
        Drone registeredDrone = droneService.registerDrone(drone);
        return new ResponseEntity<>(registeredDrone, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/{droneId}/loadMedication")
  public ResponseEntity<Drone> loadDroneWithMedication(
      @PathVariable String droneId, @RequestBody Medication medication) throws IOException {

    JsonSchema jsonSchema =
        droneSchemaValidator.getJsonSchemaFromClasspath("medication-schema.json");
    JsonNode result =
        droneSchemaValidator.getJsonNodeFromStringContent(
            droneSchemaValidator.getMapper().writeValueAsString(medication));
    Set<ValidationMessage> errors = jsonSchema.validate(result);
    if (errors.isEmpty()) {
      try {
        Drone loadedDrone = droneService.loadDroneWithMedication(droneId, medication);
        return new ResponseEntity<>(loadedDrone, HttpStatus.OK);
      } catch (DroneOverLoadedException | DroneBatteryLowException e) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{droneId}/medications")
  public ResponseEntity<List<Medication>> getDroneLoadedMedication(@PathVariable String droneId) {

    List<Medication> medications = droneService.getDroneLoadedMedication(droneId);
    return new ResponseEntity<>(medications, HttpStatus.OK);
  }

  @GetMapping("/available")
  public ResponseEntity<List<Drone>> getAvailableDronesForLoading() {

    List<Drone> availableDronesForLoading = droneService.getAvailableDronesForLoading();
    return new ResponseEntity<>(availableDronesForLoading, HttpStatus.OK);
  }

  @GetMapping("/{droneId}/batteryLevel")
  public ResponseEntity<Double> getDroneBatteryLevel(@PathVariable String droneId) {

    Double batteryLevel = droneService.getDroneBatteryLevel(droneId);
    return new ResponseEntity<>(batteryLevel, HttpStatus.OK);
  }

  @GetMapping("/drones")
  public ResponseEntity<List<Drone>> getAllDrones() {

    List<Drone> drones = droneService.getAllDrones();
    return new ResponseEntity<>(drones, HttpStatus.OK);
  }
}
