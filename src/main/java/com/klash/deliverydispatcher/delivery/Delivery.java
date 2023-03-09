package com.klash.deliverydispatcher.delivery;

import com.klash.deliverydispatcher.location.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_deliveries")
public class Delivery {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name="location_id", nullable=false)
    private Location location;
    private Double cost;
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
}
