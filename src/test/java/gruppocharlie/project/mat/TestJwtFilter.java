package gruppocharlie.project.mat;

import gruppocharlie.project.mat.service.JwtFilter;
import gruppocharlie.project.mat.service.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil; //cambiato da pubblico in privato per test

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    public JwtFilter jwtFilter; //cambiato in pubblico da privato per test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Puliamo il contesto di sicurezza prima di ogni test
    }

    @Test
    void testValidToken() throws ServletException, IOException {
        String token = "Bearer validToken";
        String username = "userTest";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extractUsername("validToken")).thenReturn(username);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(1)).extractUsername("validToken");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testMissingToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, never()).extractUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testInvalidTokenFormat() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, never()).extractUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testExpiredToken() throws ServletException, IOException {
        String token = "Bearer expiredToken";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extractUsername("expiredToken")).thenThrow(new ExpiredJwtException(null, null, "Token scaduto"));

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token scaduto");
        verify(filterChain, never()).doFilter(request, response);
    }
}
