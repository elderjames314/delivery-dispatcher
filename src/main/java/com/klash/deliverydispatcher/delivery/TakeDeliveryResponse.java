package com.klash.deliverydispatcher.delivery;

import com.klash.deliverydispatcher.location.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeDeliveryResponse {
    private Origin origin;
    private Location destination;
    private String cost;
    private Delivery delivery;
    private String distance;

}
