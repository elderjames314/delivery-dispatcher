package com.klash.deliverydispatcher;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klash.deliverydispatcher.DeliveryDispatcherApplication;
import com.klash.deliverydispatcher.auth.AuthenticationRequest;
import com.klash.deliverydispatcher.auth.AuthenticationResponse;
import com.klash.deliverydispatcher.auth.AuthenticationService;
import com.klash.deliverydispatcher.auth.RegisterRequest;
import com.klash.deliverydispatcher.config.JwtService;
import com.klash.deliverydispatcher.location.*;
import com.klash.deliverydispatcher.response.ResponseData;
import com.klash.deliverydispatcher.user.Role;
import com.klash.deliverydispatcher.user.Staff;
import com.klash.deliverydispatcher.user.StaffRepository;
import lombok.With;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StaffRepository staffRepository;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
    @MockBean
    AuthenticationService authenticationService;

    @Test
    @Order(1)
    @WithMockUser("admin")
    void whenRegisterNewUser_status200() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("John Doe")
                .email("john.doe@yopmail")
                .password(passwordEncoder.encode("1234567"))
                .build();
        var user = Staff.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.STAFF)
                .build();
        String token = jwtService.generateToken(user);
        AuthenticationResponse responseData =  AuthenticationResponse.builder()
                .token(token)
                .build();
        String body = (new ObjectMapper()).valueToTree(registerRequest).toString();
        when(authenticationService.register(registerRequest)).thenReturn(responseData);
        this.mockMvc.perform(post("/api/v1/auth/register").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser("admin")
    void whenLoginToApplication_status200() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("john.doe@yopmail")
                .password(passwordEncoder.encode("1234567"))
                .build();
        var user = Staff.builder()
                .name("John Doe")
                .email(authenticationRequest.getEmail())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .role(Role.STAFF)
                .build();
        var staff = staffRepository.save(user);
        String token = jwtService.generateToken(staff);
        AuthenticationResponse responseData =  AuthenticationResponse.builder()
                .token(token)
                .build();
        String body = (new ObjectMapper()).valueToTree(authenticationRequest).toString();
        when(authenticationService.authenticate(authenticationRequest)).thenReturn(responseData);
        this.mockMvc.perform(post("/api/v1/auth/authenticate").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
