package com.hcc.utils;


import com.hcc.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
//@PropertySource("classpath:application.properties")
public class jwtUtil implements Serializable{
    private int counter = 0;
    public static final long JWT_TOKEN_VALIDITY = 5 * 24 * 60 * 60;

    @Value("${jwt.secret}")
    private  String secret;
    private  String generatedToken;
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
        //Key secretKey = Keys.hmacShaKeyFor(secret.getBytes()); //
//        Claims claims = Jwts.parser()
//                .setSigningKey(secret.getBytes())
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        counter++;
        System.out.println(this.generatedToken + " getClaimFromToken counter" + counter);
        final Claims claims = getAllClaimsFromToken(this.generatedToken);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        counter++;
        System.out.println(this.generatedToken + " getExpirationDateFromToken counter" + counter);
        return getClaimFromToken(this.generatedToken, Claims::getExpiration);
    }
    private Claims getAllClaimsFromToken(String token) {
        counter++;
        //Key secretKey = Keys.hmacShaKeyFor(secret.getBytes()); //
        System.out.println(this.generatedToken + " getAllClaimsFromToken counter" + counter);
        byte[] decodedSecret = Base64.getDecoder().decode(secret);
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(this.generatedToken)
                .getBody();
    }



    private Boolean isTokenExpired(String token) {
        counter++;
        System.out.println(this.generatedToken +" isTokenExpired counter" + counter);
        final Date expiration = getExpirationDateFromToken(this.generatedToken);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getUsername());
    }

    private String doGenerateToken(String subject) {
//        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes()); //
//        Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 day
//        byte[] decodedSecret = Base64.getDecoder().decode(secret);
//        String token = Jwts.builder()
//                .setSubject(subject)
//                .setIssuedAt(new Date())
//                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS512 , secret.getBytes())
//                .compact();
//        System.out.println(token + " token doGenerate");
//        return token;
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        byte[] encodedSecret = Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                    .signWith(SignatureAlgorithm.HS384, secret)
                    .compact();
        this.generatedToken = token;
        counter ++;
        System.out.println(this.generatedToken + " doGenerateToken counter "+ counter );
        return token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }


}
