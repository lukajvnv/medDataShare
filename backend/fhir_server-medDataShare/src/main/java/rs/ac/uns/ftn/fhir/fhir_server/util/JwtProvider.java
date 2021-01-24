package rs.ac.uns.ftn.fhir.fhir_server.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("jwtSecretKey")
    private String jwtSecret;

    @Value("30")  //30s
    private int jwtExpiration;

    public String generateJwtToken(String username, int jwtExpiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration* 1000L))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public void validateJwtToken(String authToken) throws Exception{
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        } catch (Exception e) {
            logger.error("Invalid JWT signature -> Message: {} ", e.getMessage());
            throw new Exception(e);
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
