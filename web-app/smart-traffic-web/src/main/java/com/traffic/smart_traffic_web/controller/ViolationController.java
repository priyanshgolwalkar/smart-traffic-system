package com.traffic.smart_traffic_web.controller;

import com.traffic.smart_traffic_web.repository.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViolationController {

    @Autowired
    private ViolationRepository repo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("violations", repo.findAll());
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String vehicleId, Model model) {
        model.addAttribute("violations", repo.findByVehicleId(vehicleId));
        return "index";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam String zone, Model model) {
        model.addAttribute("violations", repo.findByZone(zone));
        return "index";
    }
}