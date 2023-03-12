package com.klash.deliverydispatcher.delivery;

import com.klash.deliverydispatcher.location.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_delivery_locations",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"location_id", "delivery_id"})}
)
@Lazy
public class DeliveryLocation implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name="location_id", nullable=false)
    private Location location;
    @ManyToOne
    @JoinColumn(name="delivery_id", nullable=false)
    private Delivery delivery;

}
