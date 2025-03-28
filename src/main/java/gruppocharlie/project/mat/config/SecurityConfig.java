package gruppocharlie.project.mat.config;

import gruppocharlie.project.mat.service.JwtFilter;
import gruppocharlie.project.mat.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtFilter = jwtFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Configura AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService); // Usa il CustomUserDetailsService
        return authenticationManagerBuilder.build();
    }

    // Configurazione della Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disabilita CSRF per le API REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT è stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login", "/auth/logout", // ✅ Endpoints pubblici per login/logout
                                "/Login.html", "/Login_Css.css", "/login.js",
                                "/images/**", "/favicon.ico", "/mat.js", "/MAT_Css.css"
                        ).permitAll() // ✅ Tutti possono accedere a questi file
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Solo gli admin possono accedere a queste risorse
                        .requestMatchers("/api/**", "/auth/validate").authenticated() // 🔒 Protegge solo le API, accessibile solo agli utenti autenticati
                        .anyRequest().permitAll() // ✅ Tutto il resto è accessibile senza restrizioni
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Aggiunge il filtro JWT
        return http.build();
    }
}
