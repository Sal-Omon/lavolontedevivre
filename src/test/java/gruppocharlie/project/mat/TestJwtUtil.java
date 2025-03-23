package gruppocharlie.project.mat;

import gruppocharlie.project.mat.service.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String tokenValido;
    private final String username = "testUser";
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        // ✅ Inizializza la stessa chiave segreta per coerenza
        String secret = "chiaveSuperSicuraChiaveSuperSicuraChiaveSuperSicura!";
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        jwtUtil = new JwtUtil(secret); // ✅ Passa la chiave segreta
        tokenValido = jwtUtil.generateToken(username);
    }

    @Test
    void testGenerateToken() {
        assertNotNull(tokenValido, "Il token generato non dovrebbe essere nullo");
        assertFalse(tokenValido.isEmpty(), "Il token generato non dovrebbe essere vuoto");
    }

    @Test
    void testExtractUsername() {
        String extractedUsername = jwtUtil.extractUsername(tokenValido);
        assertEquals(username, extractedUsername, "L'username estratto dovrebbe corrispondere a quello originale");
    }

    @Test
    void testTokenValidity() {
        assertTrue(jwtUtil.validateToken(tokenValido), "Il token dovrebbe essere valido");
    }

    @Test
    void testTokenExpiration() {
        // ✅ Generiamo un token già scaduto con la stessa chiave di JwtUtil
        String expiredToken = io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 100000)) // 100 secondi fa
                .setExpiration(new Date(System.currentTimeMillis() - 50000)) // Scaduto 50 secondi fa
                .signWith(secretKey)
                .compact();

        assertTrue(jwtUtil.isTokenExpired(expiredToken), "Il token scaduto dovrebbe essere rilevato come tale");
    }

    @Test
    void testInvalidToken() {
        String invalidToken = "invalid.token.value";
        assertFalse(jwtUtil.validateToken(invalidToken), "Un token non valido dovrebbe essere rilevato come non valido");
    }

    @Test
    void testExpiredTokenThrowsException() {
        // ✅ Generiamo un token scaduto con la stessa chiave
        String expiredToken = io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 100000)) // 100 secondi fa
                .setExpiration(new Date(System.currentTimeMillis() - 50000)) // Scaduto 50 secondi fa
                .signWith(secretKey)
                .compact();

        assertThrows(ExpiredJwtException.class, () -> jwtUtil.extractUsername(expiredToken),
                "Un token scaduto dovrebbe lanciare un'eccezione ExpiredJwtException");
    }
}
