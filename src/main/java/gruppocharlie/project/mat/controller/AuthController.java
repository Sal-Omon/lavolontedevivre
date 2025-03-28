package gruppocharlie.project.mat.controller;

import gruppocharlie.project.mat.service.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            // Verifica le credenziali
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Salva il contesto di autenticazione
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Genera il token JWT
            String token = jwtUtil.generateToken(username);

            // Crea il cookie HTTPOnly con il token
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(true) // Impostalo su true in produzione (HTTPS)
                    .path("/")
                    .sameSite("Strict")
                    .build();

            // Aggiungi il cookie alla risposta
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

            // Risposta con il messaggio di successo
            return ResponseEntity.ok(Collections.singletonMap("message", "Login riuscito!"));
        } catch (BadCredentialsException e) {
            // Gestione dell'errore di credenziali errate
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Credenziali errate"));
        } catch (Exception e) {
            // Gestione di altri errori
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Errore del server"));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        // Se il JwtFilter ha impostato l'autenticazione nel SecurityContextHolder,
        // significa che il token è valido.
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Token valido"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Token non valido"));
        }
    }
}