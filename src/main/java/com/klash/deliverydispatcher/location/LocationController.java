package com.klash.deliverydispatcher.location;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    @GetMapping
    public ResponseEntity<String> saveLocation() {
        return ResponseEntity.ok("saving location....");
    }
}
