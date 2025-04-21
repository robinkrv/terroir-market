package fr.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    private final Key signingKey;

    public JwtService(@Value("${jwt.secret-key}") String secretKey) {
        this.signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    // 1️⃣ Générez un JWT
    public String generateToken(String username, Map<String, Object> extraClaims, long expirationInMillis) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMillis))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2️⃣ Valider un token JWT
    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // 3️⃣ Extraire l'email/username depuis un JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Helper : Extraire un claim spécifique
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier que le token n'est pas expiré
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

