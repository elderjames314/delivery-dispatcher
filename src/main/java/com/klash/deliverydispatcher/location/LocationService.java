package com.klash.deliverydispatcher.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    public LocationResponse save(LocationRequest request) {
        var location = Location.builder()
                .destination(request.getDestination())
                .origin(request.getOrigin())
                .distance(request.getDistance())
                .build();
        locationRepository.save(location);
        return LocationResponse.builder()
                .origin(location.getOrigin())
                .destination(location.getDestination())
                .distance(location.getDistance())
                .build();
    }
}
