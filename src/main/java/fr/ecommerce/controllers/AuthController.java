package fr.ecommerce.controllers;

import fr.ecommerce.dto.LoginDTO;
import fr.ecommerce.dto.RefreshRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import fr.ecommerce.security.JwtService;
import fr.ecommerce.dto.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Authentifier l'utilisateur (vérifie les identifiants)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.usernameOrEmail(), loginDTO.password()));

        // Charger les détails de l'utilisateur
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.usernameOrEmail());

        System.out.println("RAW (clair) password de la requête : " + loginDTO.password());
        System.out.println("Encoded password en base : " + userDetails.getPassword());

        // Tester si les mots de passe correspondent
        boolean passwordMatches = passwordEncoder.matches(loginDTO.password(), userDetails.getPassword());
        System.out.println("Password matches ? " + passwordMatches);

        // Si le mot de passe ne correspond pas, arrêter ici
        if (!passwordMatches) {
            throw new BadCredentialsException("Mot de passe incorrect");
        }
        // Générer les tokens
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails, jwtService.generateRefreshTokenId());

        // Renvoyer accessToken et refreshToken
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtService.isTokenValid(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token invalid or expired");
        }
        String username = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateToken(userDetailsService.loadUserByUsername(username));
        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
    }
}
