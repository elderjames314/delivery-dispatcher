package com.klash.deliverydispatcher.utils;

public class KilometerDistance {
    private final static  Double EARTH_RADIUS_KM = 6371.0;
    public static Double distanceInKmBetweenEarthCoordinates(GeoLocation geoLocation) {

        var dLat = degreesToRadians(geoLocation.getDestinationLatitude()- geoLocation.getOriginLatitude());
        var dLon = degreesToRadians(geoLocation.getDestinationLongitude() - geoLocation.getOriginLongitude());

        Double originLatitude = degreesToRadians(geoLocation.getOriginLatitude());
        Double destinationLatitude = degreesToRadians(geoLocation.getDestinationLatitude());

        var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(originLatitude) * Math.cos(destinationLatitude);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS_KM * c;
    }

    private static Double degreesToRadians(Double coordinates) {
        return coordinates * Math.PI / 180;
    }
}
