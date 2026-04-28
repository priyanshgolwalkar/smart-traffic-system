package com.traffic.smart_traffic_web.model;

import jakarta.persistence.*;

@Entity
@Table(name = "violations")
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String vehicleId;
    private double speed;
    private String zone;
    private int fine;

    // getters setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public int getFine() { return fine; }
    public void setFine(int fine) { this.fine = fine; }
}