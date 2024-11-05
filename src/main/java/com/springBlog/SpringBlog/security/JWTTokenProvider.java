package com.springBlog.SpringBlog.security;

import com.springBlog.SpringBlog.exceptions.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${app.jwt-secret}")
    private String secret;
    @Value("${app-jwt-expiration-milliseconds}")
    private long expirationDetails;

    public String generateToken(Authentication authentication){
       String username= authentication.getName();
        Date currentDate= new Date();
        Date expirationDate= new Date(currentDate.getTime()+ expirationDetails) ;

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getKey())
                .compact();

        return token;
    }

    // To retrieve Username from the Token
    public String retrieveUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

//    Validate Token
    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogApiException("Validation Failed! Invalid JWT Token", HttpStatus.BAD_REQUEST);
        }catch (ExpiredJwtException expiredJwtException){
            throw new BlogApiException("JWT Token Expired", HttpStatus.BAD_REQUEST);
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw new BlogApiException("Unsupported JWT Token", HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException illegalArgumentException){
            throw new BlogApiException("JWT Claims is Empty", HttpStatus.BAD_REQUEST);
        }
    }


    public Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
