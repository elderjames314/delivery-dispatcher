package com.klash.deliverydispatcher.location;

import com.klash.deliverydispatcher.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @PostMapping
    public ResponseEntity<ResponseData> create(@RequestBody @Valid LocationRequest request) {
        return ResponseEntity.ok(locationService.save(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> update(
            @RequestBody LocationRequest request,
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(locationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(locationService.delete(id));
    }
}
