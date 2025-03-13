package gruppocharlie.project.mat.config;

import gruppocharlie.project.mat.service.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtFilter jwtFilter; // Mockiamo il filtro JWT

    @BeforeEach
    void setUp() {
        // Possiamo aggiungere eventuali setup se necessario
    }

    @Test
    void testLoginEndpointIsAccessible() throws Exception {
        mockMvc.perform(post("/auth/login"))
                .andExpect(status().isOk()); // Dovrebbe essere accessibile senza autenticazione
    }

    @Test
    void testSecureEndpointWithoutAuth() throws Exception {
        mockMvc.perform(get("/secure/data"))
                .andExpect(status().isUnauthorized()); // Dovrebbe restituire 401 Unauthorized senza JWT
    }

    @Test
    @WithMockUser // Simula un utente autenticato
    void testSecureEndpointWithAuth() throws Exception {
        mockMvc.perform(get("/secure/data"))
                .andExpect(status().isOk()); // Dovrebbe permettere l'accesso con autenticazione
    }
}
