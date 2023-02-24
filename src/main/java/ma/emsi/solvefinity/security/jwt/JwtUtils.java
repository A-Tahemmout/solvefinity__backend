package ma.emsi.solvefinity.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${solvefinity.app.jwt.secret}")
    private String jwtSecret;

    @Value("${solvefinity.app.jwt.expiration}")
    private int jwtExpirationMs;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Get all claims from the token
        final Claims claims = extractAllClaims(token);

        // Return the claim
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        // Return the token
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        // Return the token
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        // Get the username from the token
        final String username = extractUsername(token);

        // Return the result
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        // Get the expiration date from the token
        final Date expiration = extractExpiration(token);

        // Return the result
        return expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        // Get the expiration date from the token
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        // Return the claims
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        // Convert the secret to a byte array
        byte[] jwtSecretBytes = Decoders.BASE64.decode(jwtSecret);

        // Create a key from the secret
        return Keys.hmacShaKeyFor(jwtSecretBytes);
    }
}
