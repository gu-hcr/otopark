package com.otopark.business.configuration.jwt;

import com.otopark.business.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret-key}")
    private String jwtSecret ;
    @Value("${jwt.issuer}")
    private String jwtIssuer ;
    @Value("${jwt.expiration-ms}")
    private Long expirationMs ;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public String generateAccessToken(User user) {
        String subject = format("%s,%s", user.getId(), user.getUsername());
        try {
            subject = new JSONObject(format("{'id':%s, 'username':'%s', 'email':'%s'}",
                    user.getId(), user.getUsername(), user.getEmail())).toString();
        } catch (JSONException e) {
            logger.error("GenerateAccessToken json object error" + e.getMessage());
        }
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject().split(",")[1].split(":")[1].replace("\"", "");
        return username;//username
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}