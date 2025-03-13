package gruppocharlie.project.mat.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecureControllerTest {

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new SecureController()).build();

    @Test
    void testGetProtectedData() throws Exception {
        mockMvc.perform(get("/secure/data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Questa è un'API protetta accessibile solo con JWT valido!"));
    }
}
