package com.drones.demo.controllers;

import com.drones.demo.models.Drone;
import com.drones.demo.models.enums.DroneModel;
import com.drones.demo.models.enums.DroneState;
import com.drones.demo.services.DroneService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class DroneControllerTest {

  @Mock private DroneService droneService;
  private DroneController droneController;

  @Before
  public void setUp() {
    droneController = new DroneController(droneService);
  }

  @After
  public void tearDown() {}

  @Test
  public void registerDrone() throws IOException {
    // Arrange
    Drone drone =
        Drone.builder()
            .id(1L)
            .serialNumber("1")
            .model(DroneModel.LightWeight)
            .weightLimitInGrams(200.0)
            .batteryPercentage(100.0)
            .state(DroneState.IDLE)
            .build();

    Mockito.when(droneService.registerDrone(drone)).thenReturn(drone);

    // Act
    ResponseEntity<Drone> droneResponseEntity = droneController.registerDrone(drone);

    // Assert
    Mockito.verify(droneService, Mockito.times(1)).registerDrone(drone);
    Assertions.assertEquals(drone, droneResponseEntity.getBody());
  }
}
