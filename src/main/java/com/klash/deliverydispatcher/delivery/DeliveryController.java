package com.klash.deliverydispatcher.delivery;

import com.klash.deliverydispatcher.location.LocationRequest;
import com.klash.deliverydispatcher.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;
    @PostMapping
    public ResponseEntity<ResponseData> create(@RequestBody @Valid DeliveryRequest request) {
        return ResponseEntity.ok(deliveryService.save(request));
    }
    @PostMapping("/setup")
    public ResponseEntity<ResponseData> setupDeliveryLocation(@RequestBody @Valid DeliveryLocationRequest request) {
        return ResponseEntity.ok(deliveryService.setupDeliveryLocation(request));
    }
    @PostMapping("/take-delivery")
    public ResponseEntity<ResponseData> takeDelivery(@RequestBody @Valid TakeDeliveryRequest request) {
        return ResponseEntity.ok(deliveryService.takeDelivery(request));
    }
}
