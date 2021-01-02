package rs.ac.uns.ftn.medDataShare.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.security.dto.UserPrinciple;
import rs.ac.uns.ftn.medDataShare.validator.MyException;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("jwtSecretKey")
    private String jwtSecret;

//    @Value("1800")  //30min
//    private int jwtExpiration;

    @Value("7200")  //2h
    private int jwtExpiration;

//    @Value("30")  //30s
//    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return generateJwtToken(userPrincipal.getUsername());
    }

    public String generateJwtToken(String username) {

        return Jwts.builder()
                .setSubject((username))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) throws Exception{
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
            throw new Exception(e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
