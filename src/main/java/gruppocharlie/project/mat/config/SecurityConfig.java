package gruppocharlie.project.mat.config;

import gruppocharlie.project.mat.service.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // Disabilita CSRF per le API REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT è stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login", "/auth/logout", // ✅ Endpoints pubblici per login/logout
                                "/Login.html", "/Login_Css.css", "/login.js",
                                "/images/**", "/favicon.ico", "/mat.js", "/MAT_Css.css"
                        ).permitAll() // ✅ Tutti possono accedere a questi file
                        .requestMatchers("/api/**").authenticated() // 🔒 Protegge solo le API
                        .anyRequest().permitAll() // ✅ Tutto il resto è accessibile
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}