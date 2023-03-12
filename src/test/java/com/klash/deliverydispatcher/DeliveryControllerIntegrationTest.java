package com.klash.deliverydispatcher;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.klash.deliverydispatcher.DeliveryDispatcherApplication;
import com.klash.deliverydispatcher.delivery.*;
import com.klash.deliverydispatcher.location.Location;
import com.klash.deliverydispatcher.location.LocationRepository;
import com.klash.deliverydispatcher.response.ResponseData;
import com.klash.deliverydispatcher.utils.GeoLocation;
import com.klash.deliverydispatcher.utils.KilometerDistance;
import com.klash.deliverydispatcher.utils.Message;
import org.aspectj.weaver.ast.Or;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;
import java.util.Optional;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DeliveryDispatcherApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.yml")
public class DeliveryControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
    @MockBean
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryLocationRepository deliveryLocationRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Order(1)
    @WithMockUser("admin")
    void whenSaveDelivery_status200() throws Exception {
        DeliveryRequest deliveryRequest = DeliveryRequest.builder().itemName("Standing fan").description("black").build();
        ResponseData responseData = ResponseData.builder()
                .message(Message.SUCCESS)
                .data(deliveryRequest)
                .build();
        String body = (new ObjectMapper()).valueToTree(deliveryRequest).toString();
        when(deliveryService.save(deliveryRequest)).thenReturn(responseData);
        this.mockMvc.perform(post("/api/v1/deliveries").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser("admin")
    void whenSetupDeliveryLocation_status200() throws Exception {
        DeliveryLocationTest deliveryLocationTest = getDeliveryLocationRequest();
        ResponseData responseData = ResponseData.builder()
                .message(Message.SUCCESS)
                .data(deliveryLocationTest.deliveryLocationRequest)
                .build();
        String body = (new ObjectMapper()).valueToTree(deliveryLocationTest.deliveryLocationRequest).toString();
        when(deliveryService.setupDeliveryLocation(deliveryLocationTest.deliveryLocationRequest)).thenReturn(responseData);
        this.mockMvc.perform(post("/api/v1/deliveries/setup").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(3)
    @WithMockUser("admin")
    void whenTakeDeliveryLocation_status200() throws Exception {
        DeliveryLocationTest deliveryLocationTest = getDeliveryLocationRequest();
        Origin origin = Origin.builder()
                .name("Abeokuta")
                .latitude("7.145244")
                .longitude("3.327695")
                .build();
        TakeDeliveryRequest takeDeliveryRequest = TakeDeliveryRequest.builder()
                .deliveryId(deliveryLocationTest.delivery.getId())
                .origin(origin)
                .build();
        List<DeliveryLocation> deliveryLocations = deliveryLocationRepository.findByDelivery(deliveryLocationTest.delivery);
        double bestOptimalRouteInKm = Integer.MAX_VALUE;
        Location bestDestination = new Location();
        for(DeliveryLocation deliveryLocation : deliveryLocations) {
            Optional<Location> location = locationRepository.findById(deliveryLocation.getLocation().getId());
            GeoLocation coordinates = GeoLocation.builder()
                    .originLatitude(Double.parseDouble(origin.getLatitude()))
                    .originLongitude(Double.parseDouble(origin.getLongitude()))
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
                .origin(origin)
                .destination(bestDestination)
                .delivery(deliveryLocationTest.delivery)
                .distance(""+Math.round(bestOptimalRouteInKm) + "KM")
                .build();
        ResponseData responseData = ResponseData.builder()
                .message(Message.SUCCESS)
                .data(takeDeliveryResponse)
                .build();
        String body = (new ObjectMapper()).valueToTree(takeDeliveryRequest).toString();
        when(deliveryService.setupDeliveryLocation(deliveryLocationTest.deliveryLocationRequest)).thenReturn(responseData);
        this.mockMvc.perform(post("/api/v1/deliveries/take-delivery").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private DeliveryLocationTest getDeliveryLocationRequest() {
        Delivery delivery =  Delivery.builder().itemName("Standing fan").description("black").build();
        Delivery savedDelivery = deliveryRepository.save(delivery);
        List<Location> locations =  locationRepository.saveAll(List.of(
                Location.builder().name("Festac").lat("6.466445").lon("3.283514").build(),
                Location.builder().name("Mowe").lat("6.807101").lon("3.436726").build(),
                Location.builder().name("ibafo").lat("6.7401").lon("3.4221").build()
        ));
        DeliveryLocationRequest deliveryLocationRequest = DeliveryLocationRequest.builder()
                .deliveryId(savedDelivery.getId())
                .locationIds(List.of(locations.get(0).getId(), locations.get(1).getId(), locations.get(2).getId()))
                .build();
       DeliveryLocationTest deliveryLocationTest =  new DeliveryLocationTest();
       deliveryLocationTest.deliveryLocationRequest = deliveryLocationRequest;
       deliveryLocationTest.delivery = savedDelivery;
       return deliveryLocationTest;
    }


    private static class DeliveryLocationTest {
        DeliveryLocationRequest deliveryLocationRequest;
        Delivery delivery;
    }


}
