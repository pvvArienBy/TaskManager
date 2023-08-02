package by.it_academy.jd2.service;

import by.it_academy.jd2.config.properties.JWTProperty;
import by.it_academy.jd2.core.dto.UserCheckDTO;
import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.dao.entity.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String generateToken(UserEntity userEntity) {
        return generateToken(new HashMap<>(), userEntity);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserEntity userEntity
    ) {
        extraClaims.put("uuid", userEntity.getUuid());
        extraClaims.put("mail", userEntity.getMail());
        extraClaims.put("fio", userEntity.getFio());
        extraClaims.put("role", userEntity.getRole());
        return Jwts

                .builder()
                .setClaims(extraClaims)
//                .setSubject(userEntity.getMail()) //todo check
                .setIssuer(property.getIssuer())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
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
        Claims body = null;
        try {
            body = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new AccessDeniedException("Invalid token");// TODO: 01.08.2023 Check trouble
        }

        return body;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(property.getSecret());

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public UserCheckDTO meDetails (String token) {
        String jwt = token.substring(7);

        validate(token);

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
}
