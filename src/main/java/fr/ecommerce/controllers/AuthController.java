package fr.ecommerce.controllers;

import fr.ecommerce.dto.LoginDTO;
import fr.ecommerce.dto.ProducerCreateDTO;
import fr.ecommerce.dto.RefreshRequest;
import fr.ecommerce.models.entities.Producer;
import fr.ecommerce.responses.ProducerResponseDTO;
import fr.ecommerce.services.ProducerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

/**
 * The type Auth controller.
 */
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

    @Autowired
    private ProducerService producerService;

    /**
     * Login response entity.
     *
     * @param loginDTO the login dto
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.usernameOrEmail(), loginDTO.password()));


        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.usernameOrEmail());

        System.out.println("RAW (clair) password de la requête : " + loginDTO.password());
        System.out.println("Encoded password en base : " + userDetails.getPassword());

        boolean passwordMatches = passwordEncoder.matches(loginDTO.password(), userDetails.getPassword());
        System.out.println("Password matches ? " + passwordMatches);

        if (!passwordMatches) {
            throw new BadCredentialsException("Mot de passe incorrect");
        }
        // Générer les tokens
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails, jwtService.generateRefreshTokenId());

        // Renvoyer accessToken et refreshToken
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    /**
     * Register producer response entity.
     *
     * @param producerCreateDTO the producer create dto
     * @return the response entity
     */
    @PostMapping("/register-producer")
    public ResponseEntity<ProducerResponseDTO> registerProducer(@Valid @RequestBody ProducerCreateDTO producerCreateDTO) {
        ProducerResponseDTO createdProducer = producerService.createProducer(producerCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProducer);
    }


    /**
     * Refresh response entity.
     *
     * @param request the request
     * @return the response entity
     */
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
