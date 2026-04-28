package model;

public class VehicleEvent {
    public String vehicleId;
    public double speed;
    public String zone;
    public boolean isEmergencyVehicle;
    public long timestamp;

    public VehicleEvent(String vehicleId, double speed, String zone, boolean isEmergencyVehicle, long timestamp) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.zone = zone;
        this.isEmergencyVehicle = isEmergencyVehicle;
        this.timestamp = timestamp;
    }
}