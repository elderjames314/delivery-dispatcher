package com.klash.deliverydispatcher.location;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @GetMapping
    public ResponseEntity<LocationResponse> saveLocation(@RequestBody LocationRequest request) {
        return ResponseEntity.ok(locationService.save(request));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(
            @RequestBody LocationRequest request,
            @PathVariable Integer id
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Integer id) {
        return null;
    }
}
