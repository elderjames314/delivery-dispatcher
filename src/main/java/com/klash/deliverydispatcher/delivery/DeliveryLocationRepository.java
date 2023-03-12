package com.klash.deliverydispatcher.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryLocationRepository extends JpaRepository<DeliveryLocation, Integer> {
    List<DeliveryLocation> findByDelivery(Delivery delivery);
}
