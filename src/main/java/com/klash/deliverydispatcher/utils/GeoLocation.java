package com.klash.deliverydispatcher.utils;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class GeoLocation {
    private Double originLongitude;
    private Double destinationLongitude;
    private Double originLatitude;
    private Double destinationLatitude;
}
