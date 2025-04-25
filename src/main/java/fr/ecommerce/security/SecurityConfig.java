package fr.ecommerce.security;

import fr.ecommerce.repositories.UserRepository;
import fr.ecommerce.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Désactiver CSRF (tu peux ajuster si JWT est en place)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()       // Endpoints publics (login et inscription)
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN uniquement
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/api/users/**").permitAll()// USER uniquement
                        .anyRequest().authenticated()                 // Toutes les autres requêtes sécurisées
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); // Utilisation plus récente, sans redondances
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilisé dans tes services pour encoder les mots de passe
    }
}


