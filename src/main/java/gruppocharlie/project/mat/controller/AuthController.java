package gruppocharlie.project.mat.controller;

import gruppocharlie.project.mat.service.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // ✅ Verifica credenziali
        if ("titolare".equals(username) && "controllo".equals(password)) {
            String token = jwtUtil.generateToken(username);

            // ✅ Crea un cookie HTTPOnly con il token
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)  // 🔥 Protezione XSS
                    .secure(false)   // ⚠️ Metti `true` in produzione con HTTPS
                    .path("/")       // Il cookie è accessibile in tutto il sito
                    .sameSite("Strict") // Protezione CSRF
                    .build();

            // ✅ Aggiungi il cookie alla risposta
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

            return ResponseEntity.ok("Login riuscito!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali errate");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // ✅ Rimuove il cookie impostandolo con durata 0
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)  // 🔥 Scade immediatamente
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok("Logout riuscito!");
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@CookieValue(name = "jwt", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(401).body("Token mancante");
        }

        if (!jwtUtil.validateToken(token)) { // ✅ Corretto il metodo {
            return ResponseEntity.status(401).body("Token non valido o scaduto");
        }

        String username = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(Collections.singletonMap("user", username));
    }

}
