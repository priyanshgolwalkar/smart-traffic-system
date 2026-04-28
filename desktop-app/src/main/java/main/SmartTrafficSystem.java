package main;

import dao.ViolationDAO;
import model.VehicleEvent;
import model.ViolationRecord;

import javax.swing.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class SmartTrafficSystem {

    static class TrafficRules {
        static Predicate<VehicleEvent> violationFilter = event ->
                event.speed > 80 && !event.isEmergencyVehicle;

        static Function<Double, Integer> fineCalculator = speed -> {
            if (speed > 120) return 5000;
            else if (speed > 100) return 2000;
            else return 1000;
        };
    }

    public static void main(String[] args) {

        List<VehicleEvent> events = Arrays.asList(
                new VehicleEvent("MH12AB1234", 95, "Pune-West", false, System.currentTimeMillis()),
                new VehicleEvent("MH14XY5678", 130, "Pune-East", false, System.currentTimeMillis()),
                new VehicleEvent(null, 110, null, false, System.currentTimeMillis()),
                new VehicleEvent("MH01ZZ9999", 70, "Mumbai", false, System.currentTimeMillis()),
                new VehicleEvent("MH12EM1111", 140, "Pune-West", true, System.currentTimeMillis())
        );

        List<ViolationRecord> violations = events
                .parallelStream()
                .filter(Objects::nonNull)
                .filter(TrafficRules.violationFilter)
                .map(event -> {

                    String vehicleId = Optional.ofNullable(event.vehicleId)
                            .orElse("UNKNOWN");

                    String zone = Optional.ofNullable(event.zone)
                            .orElse("UNKNOWN_ZONE");

                    int fine = TrafficRules.fineCalculator.apply(event.speed);

                    return new ViolationRecord(vehicleId, event.speed, zone, fine);
                })
                .collect(Collectors.toList());

        // Save to DB
        ViolationDAO dao = new ViolationDAO();
        violations.forEach(dao::saveViolation);

        // Set UI theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch UI safely
        SwingUtilities.invokeLater(() -> {
            new ui.TrafficUI(violations);
        });

        // Aggregation
        int totalFine = violations.stream()
                .map(v -> v.fine)
                .reduce(0, Integer::sum);

        long totalViolations = violations.size();

        System.out.println("\n===============================");
        System.out.println("Total Violations: " + totalViolations);
        System.out.println("Total Fine Collected: ₹" + totalFine);
        System.out.println("===============================");
    }
}