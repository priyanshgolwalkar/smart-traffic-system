package com.traffic.smart_traffic_web.repository;

import com.traffic.smart_traffic_web.model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation, Integer> {

    List<Violation> findByVehicleId(String vehicleId);

    List<Violation> findByZone(String zone);
}