package uemura.java_spring_boot_demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uemura.java_spring_boot_demo.domais.SalesRequestDto;
import uemura.java_spring_boot_demo.service.SalesService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testes para validar a Classe FuncionarioController")
class SalesControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private SalesController controller;

    @Mock
    private SalesService salesService;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void salesTest() throws Exception {
        SalesRequestDto requestDto = new SalesRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc
                .perform(post("/api/public/v1/sales/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent());
    }
}
