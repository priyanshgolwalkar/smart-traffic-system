package model;

public class ViolationRecord {
    public String vehicleId;
    public double speed;
    public String zone;
    public int fine;

    public ViolationRecord(String vehicleId, double speed, String zone, int fine) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.zone = zone;
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Vehicle: " + vehicleId +
                " | Speed: " + speed +
                " | Zone: " + zone +
                " | Fine: ₹" + fine;
    }
}