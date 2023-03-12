package com.klash.deliverydispatcher.delivery;

import com.klash.deliverydispatcher.exception.ErrorResponse;
import com.klash.deliverydispatcher.location.Location;
import com.klash.deliverydispatcher.location.LocationRepository;
import com.klash.deliverydispatcher.response.ResponseData;
import com.klash.deliverydispatcher.utils.GeoLocation;
import com.klash.deliverydispatcher.utils.KilometerDistance;
import com.klash.deliverydispatcher.utils.Message;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final LocationRepository locationRepository;
    private final DeliveryLocationRepository deliveryLocationRepository;
    public ResponseData save(DeliveryRequest request) {
        var delivery = Delivery.builder()
                .itemName(request.getItemName())
                .description(request.getDescription())
                .build();
        deliveryRepository.save(delivery);
        return ResponseData.builder()
                .message(Message.SUCCESS)
                .data(delivery)
                .build();
    }

    @Transactional
    public ResponseData setupDeliveryLocation(DeliveryLocationRequest request) {
        Optional<Delivery> delivery = getDelivery(request.getDeliveryId());
        if(!delivery.isPresent())
            return itemNotFound(Message.DELIVERY);
        for (Integer locationId: request.getLocationIds()) {
            Optional<Location> location = getLocation(locationId);
            if(!location.isPresent())
                return itemNotFound(Message.LOCATION + " with ID: "+ locationId);
        }
        for (Integer locationId: request.getLocationIds()) {
            var location = getLocation(locationId);
            var delivery2 = getDelivery(request.getDeliveryId());
            var deliveryLocation = DeliveryLocation.builder()
                    .location(location.get())
                    .delivery(delivery2.get())
                    .build();
            deliveryLocationRepository.save(deliveryLocation);
        }
        return ResponseData.builder()
                .message(Message.SUCCESS)
                .build();

    }

    @Transactional
    public ResponseData takeDelivery(TakeDeliveryRequest request) {
        Optional<Delivery> delivery = getDelivery(request.getDeliveryId());
        if(!delivery.isPresent())
            return itemNotFound(Message.DELIVERY);
        double bestOptimalRouteInKm = Integer.MAX_VALUE;
        Location bestDestination = new Location();
        List<DeliveryLocation> deliveryLocations = deliveryLocationRepository.findByDelivery(delivery.get());
        if(deliveryLocations.isEmpty()) return ResponseData.builder().message(Message.DELIVERY_LOCATION_NOT_SET).build();
        if(deliveryLocations.size() < 3) return ResponseData.builder().message(Message.THREE_LOCATIONS_MUST_BE_SET_FOR_DELIVERY).build();
        for(DeliveryLocation deliveryLocation : deliveryLocations) {
            Optional<Location> location = getLocation(deliveryLocation.getLocation().getId());
            if(!location.isPresent()) return itemNotFound(Message.LOCATION);
            GeoLocation coordinates = GeoLocation.builder()
                    .originLatitude(Double.parseDouble(request.getOrigin().getLatitude()))
                    .originLongitude(Double.parseDouble(request.getOrigin().getLongitude()))
                    .destinationLatitude(Double.parseDouble(location.get().getLat()))
                    .destinationLongitude(Double.parseDouble(location.get().getLon()))
                    .build();
            Double distance = KilometerDistance.distanceInKmBetweenEarthCoordinates(coordinates);
            bestOptimalRouteInKm = Math.min(bestOptimalRouteInKm, distance);
            bestDestination = deliveryLocation.getLocation();
        }
        //1KM = $1;
        Double cost = bestOptimalRouteInKm * 1;

        TakeDeliveryResponse takeDeliveryResponse = TakeDeliveryResponse.builder()
                .cost("$" + Math.round(cost))
                .origin(request.getOrigin())
                .destination(bestDestination)
                .delivery(delivery.get())
                .distance(""+Math.round(bestOptimalRouteInKm) + "KM")
                .build();
        return ResponseData.builder()
                .message(Message.SUCCESS)
                .data(takeDeliveryResponse)
                .build();
    }

    private Optional<Delivery> getDelivery(Integer deliveryId) {
        return deliveryRepository.findById(deliveryId);
    }

    private ResponseData itemNotFound(String item) {
        return ResponseData.builder().message(item +" not found").build();
    }

    private Optional<Location> getLocation(Integer locationId) {
        return locationRepository.findById(locationId);
    }
}
