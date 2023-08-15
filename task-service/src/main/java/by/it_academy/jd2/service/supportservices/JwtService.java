package by.it_academy.jd2.service.supportservices;

import by.it_academy.jd2.config.properties.JWTProperty;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.enums.ERole;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {
    private final JWTProperty property;

    public JwtService(JWTProperty property) {
        this.property = property;
    }


    public String extractUserName(String token) {
        String username = extractClaim(token, claims -> claims.get("mail", String.class));
        return username;
    }

    public String extractUuid(String token) {
        String jwt = token.substring(7);
        validate(jwt);
        String uuid = extractClaim(jwt, claims -> claims.get("uuid", String.class));

        return uuid;
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        Claims body;
        try {
            body = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new AccessDeniedException("Invalid token");
        }

        return body;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(property.getSecret());

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public UserCheckDTO meContextDetails(String token) {
        String jwt = token.substring(7);
        validate(jwt);

        UserCheckDTO dto = new UserCheckDTO();
        dto.setUuid(UUID.fromString(extractClaim(jwt, claims -> claims.get("uuid", String.class))));
        dto.setMail(extractClaim(jwt, claims -> claims.get("mail", String.class)));
        dto.setFio(extractClaim(jwt, claims -> claims.get("fio", String.class)));
        dto.setRole(ERole.valueOf(extractClaim(jwt, claims -> claims.get("role", String.class))));

        return dto;
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);

            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
            throw new AccessDeniedException("Invalid token1");
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
            throw new AccessDeniedException("Invalid token2");
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
            throw new AccessDeniedException("Invalid token3");
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
            throw new AccessDeniedException("Invalid token4");
        }
        return false;
    }

    public String getSystemToken() {
        return this.property.getSystemToken();
    }
}
