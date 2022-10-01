package com.drones.demo;

import com.drones.demo.services.DroneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public CommandLineRunner run(DroneService droneService) {
    return (args) -> {
      droneService.loadDrone();
      System.out.println(droneService.getAllDrones());
    };
  }
}
