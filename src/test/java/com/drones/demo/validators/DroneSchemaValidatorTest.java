package com.drones.demo.validators;

import com.drones.demo.models.Drone;
import com.drones.demo.models.Medication;
import com.drones.demo.models.enums.DroneState;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DroneSchemaValidatorTest {

  private DroneSchemaValidator droneSchemaValidator;

  @Before
  public void setUp() {
    droneSchemaValidator = new DroneSchemaValidator();
  }

  @After
  public void tearDown() {}

  @Test
  public void givenInvalidInput_whenValidating_thenInvalid() throws IOException {
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

    JsonSchema jsonSchema = droneSchemaValidator.getJsonSchemaFromClasspath("drone-schema.json");
    JsonNode result =
        droneSchemaValidator.getJsonNodeFromStringContent(
            droneSchemaValidator.getMapper().writeValueAsString(drone));
    Set<ValidationMessage> errors = jsonSchema.validate(result);
    assertThat(errors, is(not(empty())));

    String errorsString = errors.toString();

    Assertions.assertTrue(errorsString.contains("serialNumber: null found, string expected"));
    Assertions.assertTrue(
        errorsString.contains(
            "model: does not have a value in the enumeration [LightWeight, MiddleWeight, CruiserWeight, HeavyWeight]"));
    Assertions.assertTrue(errorsString.contains("medications[0].id: null found, integer expected"));
    Assertions.assertTrue(
        errorsString.contains("medications[0].name: null found, string expected"));
    Assertions.assertTrue(
        errorsString.contains("medications[0].code: null found, string expected"));
    Assertions.assertTrue(errorsString.contains("medications[1].id: null found, integer expected"));
    Assertions.assertTrue(
        errorsString.contains("medications[1].name: null found, string expected"));
    Assertions.assertTrue(
        errorsString.contains("medications[1].code: null found, string expected"));
  }
}
