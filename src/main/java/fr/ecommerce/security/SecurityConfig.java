package fr.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Désactiver CSRF pour les tests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()  // Autoriser l'accès public
                        .anyRequest().authenticated()
                );// Permet l'authentification basique (utile pour Postman)
        return http.build();
    }
}

