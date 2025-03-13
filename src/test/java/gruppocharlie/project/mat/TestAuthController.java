package gruppocharlie.project.mat;

import gruppocharlie.project.mat.controller.AuthController;
import gruppocharlie.project.mat.service.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.HttpStatus;

class AuthControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        Map<String, String> credentials = Map.of("username", "titolare", "password", "controllo");
        String expectedToken = "mocked-jwt-token";

        when(jwtUtil.generateToken("titolare")).thenReturn(expectedToken);

        ResponseEntity<?> response = authController.login(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonMap("token", expectedToken), response.getBody());
        verify(jwtUtil, times(1)).generateToken("titolare");
    }

    @Test
    void testLoginFailure() {
        Map<String, String> credentials = Map.of("username", "wrongUser", "password", "wrongPass");

        ResponseEntity<?> response = authController.login(credentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenziali errate", response.getBody());
        verify(jwtUtil, never()).generateToken(anyString());
    }
}
