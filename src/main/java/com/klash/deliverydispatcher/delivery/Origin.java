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
public class Origin {
    @NotNull(message = " name cannot be null")
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotNull(message = "latitude name cannot be null")
    @NotBlank(message = "latitude name cannot be blank")
    private String latitude;
    @NotNull(message = "longitude name cannot be null")
    @NotBlank(message = "longitude name cannot be blank")
    private String longitude;
}
