package com.klash.deliverydispatcher.location;

import com.klash.deliverydispatcher.exception.NoRecordFoundException;
import com.klash.deliverydispatcher.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    public ResponseData save(LocationRequest request) {
        var location = Location.builder()
                .name(request.getName())
                .lon(request.getLon())
                .lat(request.getLat())
                .build();
        locationRepository.save(location);
        return ResponseData.builder()
                .message("Location saved successfully")
                .data(location)
                .build();
    }
    public ResponseData update(Integer id, LocationRequest request) {
        Optional<Location> location = get(id);
        location.get().setName(request.getName());
        location.get().setLon(request.getLon());
        location.get().setLat(request.getLat());
        locationRepository.save(location.get());
        return ResponseData.builder()
                .message("Location updated successfully")
                .data(location.get())
                .build();
    }

    public Optional<Location> get(Integer id) throws NoRecordFoundException {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location;
        }else {
            throw new NoRecordFoundException();
        }
    }

    public ResponseData delete(Integer id) {
        Optional<Location> location = get(id);
        locationRepository.delete(location.get());
        return ResponseData.builder()
                .message("Location deleted successfully")
                .build();
    }
}
