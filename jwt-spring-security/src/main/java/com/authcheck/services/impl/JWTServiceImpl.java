package com.authcheck.services.impl;

import com.authcheck.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Service
public class JWTServiceImpl  {

    private String generateToken(UserDetails userDetails){
        return builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000 * 60 *24))
                .signWith(getSignkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }


    private  <T> T extractClaim(String token, Function<Claims,T> claimsResolvers){
        final Claims claims = extractAllClaim(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaim(String token) {
         return Jwts.parser().setSigningKey(getSignkey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignkey(){
        byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D59703373367639792442264529478404D6351");
        return Keys.hmacShaKeyFor(key);
    }

}
