package uemura.java_spring_boot_demo.contracts;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import uemura.java_spring_boot_demo.controller.SalesController;

public class MockMvcTest {

    @Autowired
    SalesController salesController;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(salesController);
    }

}

