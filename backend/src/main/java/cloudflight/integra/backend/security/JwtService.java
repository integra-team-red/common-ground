package cloudflight.integra.backend.security;

import cloudflight.integra.backend.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtService {
    public static final long TOKEN_EXPIRATION_TIME = ChronoUnit.HOURS.getDuration().toMillis();
    private final SecretKey SECRET = KeyGenerator.getInstance("HmacSHA256").generateKey();

    public JwtService() throws NoSuchAlgorithmException {
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        String userRole = authorities.stream().map(GrantedAuthority::getAuthority).filter(authority ->
            authority.equals(Role.ADMIN.toString())).findFirst().orElse(Role.USER.toString());
        Date now = new Date(System.currentTimeMillis());
        return Jwts.builder().subject(username)
            .claim("role", userRole)
            .issuedAt(now)
            .expiration(generateTokenExpirationDate())
            .signWith(SECRET)
            .compact();
    }

    private Date generateTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);
    }

    public boolean validate(String token) {
        try {
            extractClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) throws JwtException {
        return Jwts.parser().verifyWith(SECRET).build().parseSignedClaims(token).getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
