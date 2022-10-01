package com.drones.demo.repositeries;

import com.drones.demo.models.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationRepository extends CrudRepository<Medication, Long> {}
