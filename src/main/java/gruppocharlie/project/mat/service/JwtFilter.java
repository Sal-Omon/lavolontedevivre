package gruppocharlie.project.mat.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // ✅ Escludi tutte le richieste tranne `MAT.html`
        if (!isProtectedPage(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        if (token != null && validateTokenAndSetAuthentication(token, request, response)) {
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido o scaduto");
        }
    }

    // 🔥 Verifica se la pagina è protetta
    private boolean isProtectedPage(String requestPath) {
        return requestPath.endsWith("MAT.html");
    }

    // 🔥 Estrai il token dai cookie o dall'header
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = getTokenFromCookies(request);
        if (token == null) {
            token = getTokenFromHeader(request);
        }
        return token;
    }

    // 🔥 Estrai il token dai cookie
    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 🔥 Estrai il token dall'header Authorization
    private String getTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    // 🔥 Verifica il token e imposta l'autenticazione
    private boolean validateTokenAndSetAuthentication(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = jwtUtil.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token scaduto");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido");
        }
        return false;
    }
}
