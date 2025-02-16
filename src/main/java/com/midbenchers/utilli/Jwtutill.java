package com.midbenchers.utilli;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class Jwtutill {



    //step1: secret key

    private final String secretKey="+DDgN0YCkNZzToCKyP6RHuGKiNVUlDrnhEt5vvfw6Ho=+DDgN0YCkNZzToCKyP6RHuGKiNVUlDrnhEt5vvfw6Ho=";

    //step2: genarate Jwt token

public String generatetoken(String username){

    Map<String,Object>claims= new HashMap<>();

    return createtoken(claims,username);
}


    //step3:create token
    public String createtoken(Map<String, Object> claims, String username) {

   return Jwts.builder()
           .setClaims(claims)
           .setSubject(username)
           .setIssuer("ramesh")
           .setIssuedAt(new Date(System.currentTimeMillis()))
           .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
           .signWith(SignatureAlgorithm.HS256,getSignxkey())
           .compact();

    }

    private Key getSignxkey() {

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


//    step 4: Validate token

    public boolean Validatetoken(String token , String Username){

      final String extractedusername=extractusername(token);

      return  extractedusername.equals(Username) && !istokenexperied(token);

    }



    //step 5 Extract username from token
    public String extractusername(String token) {
        return extractClaims(token, claims -> claims.getSubject());
    }

    //step 6 extract specific claims

    public <T> T extractClaims(String token,
                                Function<Claims,T> claimsResolver){

    final Claims claims=getAllClaimsFromToken(token);

    return  claimsResolver.apply(claims);
    }

    //step 7 get all claims from token

    public Claims getAllClaimsFromToken(String token) {

    return Jwts.parser()
            .setSigningKey(getSignxkey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }


    //step 8 calling expiredate method

    public boolean istokenexperied(String token) {

    return extractExpiration(token).before(new Date());
    }


    //step 9  checking expiredate
    public Date extractExpiration(String token) {

    return extractClaims(token,Claims::getExpiration);
    }

}
