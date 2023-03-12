package com.klash.deliverydispatcher;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klash.deliverydispatcher.DeliveryDispatcherApplication;
import com.klash.deliverydispatcher.location.*;
import com.klash.deliverydispatcher.response.ResponseData;
import lombok.With;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DeliveryDispatcherApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.yml")
public class LocationControllerIntegrationTest {

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
    LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Order(1)
    @WithMockUser("admin")
    void whenSaveLocation_status200() throws Exception {
        LocationRequest locationRequest = LocationRequest.builder().lat("6.4589852").lon("3.6015212").name("Oshodi2").build();
        ResponseData responseData = ResponseData.builder()
                .message("Location saved successfully")
                .data(locationRequest)
                .build();
        String body = (new ObjectMapper()).valueToTree(locationRequest).toString();
        when(locationService.save(locationRequest)).thenReturn(responseData);
        this.mockMvc.perform(post("/api/v1/locations").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser("admin")
    void whenUpdateLocation_status200() throws Exception {
        Location location = Location.builder().lat("6.4589852").lon("3.6015212").name("Oshodi2").build();
        Location savedLocation = locationRepository.save(location);
        Optional<Location> updatedLocation = locationService.get(savedLocation.getId());
        LocationRequest locationRequest = LocationRequest.builder()
                .name("Mile 2")
                .lat("6.4589234")
                .lon("3.6015234")
                .build();
        ResponseData data  = ResponseData.builder()
                .message("Location updated successfully")
                .data(locationRequest)
                .build();

        String body = (new ObjectMapper()).valueToTree(updatedLocation).toString();
        when(locationService.update(savedLocation.getId(),locationRequest)).thenReturn(data);
        this.mockMvc.perform(put("/api/v1/locations/"+savedLocation.getId()).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @Order(3)
    @WithMockUser("admin")
    void whenDeleteLocation_status200() throws Exception {
        Location location = Location.builder().lat("6.4589852").lon("3.6015212").name("Oshodi2").build();
        Location savedLocation = locationRepository.save(location);
        ResponseData data  = ResponseData.builder()
                .message("Location deleted successfully")
                .build();
        when(locationService.delete(savedLocation.getId())).thenReturn(data);
        this.mockMvc.perform(delete("/api/v1/locations/"+savedLocation.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }




}
