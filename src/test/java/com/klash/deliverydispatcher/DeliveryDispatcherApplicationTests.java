package com.klash.deliverydispatcher;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeliveryDispatcherApplicationTests {

	@Test
	void contextLoads() {
		new AuthenticationControllerIntegrationTest();
		new LocationControllerIntegrationTest();
		new DeliveryControllerIntegrationTest();
	}

}
