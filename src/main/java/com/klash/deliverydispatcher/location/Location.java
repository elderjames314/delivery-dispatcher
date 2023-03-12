package com.klash.deliverydispatcher.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klash.deliverydispatcher.delivery.Delivery;
import com.klash.deliverydispatcher.delivery.DeliveryLocation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_locations")
public class Location implements Serializable {
    @Id
    @GeneratedValue
    private Integer Id;
    @NotNull(message = "Longitude cannot be null")
    @NotBlank(message = "Longitude cannot be blank")
    private String lon;
    @NotNull(message = "Latitude cannot be null")
    @NotBlank(message = "Latitude cannot be blank")
    private String lat;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy="location")
    private List<DeliveryLocation> deliveryLocations;
}
