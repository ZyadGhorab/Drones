package com.drones.demo.services;

import com.drones.demo.exceptions.DroneBatteryLowException;
import com.drones.demo.exceptions.DroneOverLoadedException;
import com.drones.demo.models.Drone;
import com.drones.demo.models.Medication;
import com.drones.demo.models.enums.DroneState;
import com.drones.demo.repositeries.DroneRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class DroneServiceTest {

  private DroneService droneService;
  @Mock private DroneRepository droneRepository;

  @Before
  public void setUp() {
    droneService = new DroneService(droneRepository);
  }

  @After
  public void tearDown() {
    Mockito.reset(droneRepository);
  }

  @Test
  public void loadDroneWithMedication_LoadSuccess() {
    // Arrange
    String droneId = "1";
    Drone drone =
        Drone.builder()
            .id(Long.valueOf(droneId))
            .state(DroneState.IDLE)
            .weightLimitInGrams(500.0)
            .batteryPercentage(90.0)
            .build();
    List<Medication> existingMedications =
        new ArrayList<>(
            Arrays.asList(
                Medication.builder().drone(drone).weightInGrams(100.0).build(),
                Medication.builder().drone(drone).weightInGrams(250.0).build()));
    drone.setMedications(existingMedications);

    Medication medication = Medication.builder().weightInGrams(150.0).build();

    Mockito.when(droneRepository.findById(Long.valueOf(droneId))).thenReturn(Optional.of(drone));

    // Act
    // Assert
    assertDoesNotThrow(() -> droneService.loadDroneWithMedication(droneId, medication));

    ArgumentCaptor<Drone> argumentCaptor = ArgumentCaptor.forClass(Drone.class);
    Mockito.verify(droneRepository, Mockito.times(1)).findById(Long.valueOf(droneId));
    Mockito.verify(droneRepository, Mockito.times(1)).save(argumentCaptor.capture());
    Drone actualDrone = argumentCaptor.getValue();
    Assertions.assertEquals(
        DroneState.LOADING, actualDrone.getState(), "Drone state should be LOADING");
    Assertions.assertEquals(
        existingMedications, actualDrone.getMedications(), "Medications should be the same");
  }

  @Test
  public void loadDroneWithMedication_PreventOverLoading() {
    // Arrange
    String droneId = "1";
    Drone drone =
        Drone.builder()
            .id(Long.valueOf(droneId))
            .weightLimitInGrams(500.0)
            .batteryPercentage(90.0)
            .build();
    Medication medication = Medication.builder().drone(drone).weightInGrams(501.0).build();

    Mockito.when(droneRepository.findById(Long.valueOf(droneId))).thenReturn(Optional.of(drone));

    // Act
    // Assert
    assertThrows(
        DroneOverLoadedException.class,
        () -> {
          droneService.loadDroneWithMedication(droneId, medication);
        });
  }

  @Test
  public void loadDroneWithMedication_PreventIfBatteryIsLow() {
    // Arrange
    String droneId = "1";
    Drone drone =
        Drone.builder()
            .id(Long.valueOf(droneId))
            .weightLimitInGrams(500.0)
            .batteryPercentage(24.0)
            .build();
    Medication medication = Medication.builder().drone(drone).weightInGrams(100.0).build();

    Mockito.when(droneRepository.findById(Long.valueOf(droneId))).thenReturn(Optional.of(drone));

    // Act
    // Assert
    assertThrows(
        DroneBatteryLowException.class,
        () -> {
          droneService.loadDroneWithMedication(droneId, medication);
        });
  }
}
