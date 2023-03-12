package com.klash.deliverydispatcher.delivery;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryLocationRequest {
    @NotEmpty(message = "location cannot be empty")
    private List<@NotNull Integer> locationIds;
    @NotNull(message = "deliveryId cannot be null")
    @Min(value = 0L, message = "The value must be positive")
    private Integer deliveryId;
}
