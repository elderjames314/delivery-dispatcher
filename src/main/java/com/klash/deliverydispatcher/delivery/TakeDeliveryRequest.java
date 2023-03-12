package com.klash.deliverydispatcher.delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeDeliveryRequest {
    @NotNull(message = "Delivery id name cannot be null")
    private Integer deliveryId;
    private Origin origin;
}
