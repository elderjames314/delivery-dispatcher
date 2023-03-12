package com.klash.deliverydispatcher.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klash.deliverydispatcher.location.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_deliveries")
public class Delivery implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String itemName;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy="delivery")
    private List<DeliveryLocation> deliveryLocations;
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
}
