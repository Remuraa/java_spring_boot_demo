package uemura.java_spring_boot_demo;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import uemura.java_spring_boot_demo.controller.SalesController;

public class MockMvcTest {

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new SalesController());
    }
}
