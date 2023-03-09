package com.klash.deliverydispatcher.location;

import com.klash.deliverydispatcher.delivery.Delivery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_locations")
public class Location {
    @Id
    @GeneratedValue
    private Integer Id;
    private String origin;
    private String destination;
    private Double distance;
    @OneToMany(mappedBy="location")
    private List<Delivery> deliveries;
}
