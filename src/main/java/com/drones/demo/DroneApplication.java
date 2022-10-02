package com.drones.demo;

import com.drones.demo.models.Drone;
import com.drones.demo.services.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class DroneApplication {

  static final Logger log = LoggerFactory.getLogger(DroneApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(DroneApplication.class, args);
  }

  @Bean
  public CommandLineRunner run(DroneService droneService) {
    return (args) -> {
      droneService.loadDrone();
      List<Drone> allDrones = droneService.getAllDrones();
      log.info("All drones: {}", allDrones);

      Timer timer = new Timer();
      TimerTask timerTask =
          new TimerTask() {
            @Override
            public void run() {
              List<Drone> allDrones = droneService.getAllDrones();
              allDrones.forEach(
                  drone -> {
                    log.info("Drone: {}", drone);
                    log.info(
                        LocalDateTime.now()
                            + " Battery percentage: "
                            + drone.getBatteryPercentage());
                  });
            }
          };
      timer.schedule(timerTask, 0, 5000);
    };
  }
}
