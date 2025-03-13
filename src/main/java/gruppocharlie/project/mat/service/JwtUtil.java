package gruppocharlie.project.mat.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {
    // La chiave deve essere di almeno 32 byte per HS256
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("chiaveSuperSicuraChiaveSuperSicura".getBytes());

    // Metodo per generare il token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 ora
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Firma con la SecretKey
                .compact();
    }

    // Estrai il nome utente dal token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Verifica se il token è valido
    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Verifica se il token è scaduto
    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = getClaims(token).getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // Se il token è scaduto, restituiamo true direttamente
        }
    }

    // Estrai i claims dal token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
