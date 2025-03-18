package gruppocharlie.project.mat.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtUtil {

    private final SecretKey SECRET_KEY;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT per un utente dato.
     * @param username Il nome utente dell'utente autenticato.
     * @return Il token JWT generato.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Imposta il nome utente come "subject"
                .setIssuedAt(new Date()) // Data di creazione
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Valido 1 ora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Firma con la chiave segreta
                .compact();
    }

    /**
     * Estrae il nome utente dal token JWT.
     * @param token Il token JWT.
     * @return Il nome utente contenuto nel token.
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Verifica se il token è valido per un determinato utente.
     * @param token Il token JWT.
     * @param username Il nome utente atteso.
     * @return True se il token è valido, false altrimenti.
     */
    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /**
     * Verifica se il token è scaduto.
     * @param token Il token JWT.
     * @return True se il token è scaduto, false altrimenti.
     */
    public boolean isTokenExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // Token scaduto
        }
    }

    /**
     * Estrai i claims (informazioni) dal token.
     * @param token Il token JWT.
     * @return I claims contenuti nel token.
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}