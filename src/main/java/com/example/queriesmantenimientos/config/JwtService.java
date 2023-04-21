package com.example.queriesmantenimientos.config;

import com.example.queriesmantenimientos.model.User;
import com.example.queriesmantenimientos.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${app.jwt.access.secret}")
    private String ACCESS_TOKEN_SECRET;
    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepo;

    public User getUser(String authorization) throws Exception {

        String userEmail = getEmailFromAuth(authorization);
        if (userEmail == null) throw new Exception("Invalid token");
        return userRepo.findOneByEmail(userEmail).orElseThrow();
    }

    public User getUserWeb(String authorization) throws Exception {

        String userEmail = getEmailFromAuth(authorization);
        if (userEmail == null) throw new Exception("Invalid token");
        return webClientBuilder.build()
                .get()
                .uri("http://authorization/auth/user")
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claims != null
                ? claimsResolver.apply(claims)
                : null;
    }

    public boolean isTokenValid(String token) {
        final String username = extractUsername(token);
        return username != null && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        return date == null || date.before(new Date());
    }


    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromAuth(String authorization) throws Exception {
        if (authorization == null || !authorization.startsWith("Bearer "))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        String token = authorization.substring("Bearer ".length());
        if (!isTokenValid(token)) {
            throw new Exception("Invalid token");
        }
        return extractUsername(token);
    }

}
