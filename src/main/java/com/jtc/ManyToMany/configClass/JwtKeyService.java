package com.jtc.ManyToMany.configClass;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtKeyService {

    private static  final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractIssuer(String token)
    {
        return extractClaim(token, Claims::getIssuer);
    }

    public String generateToken(UserDetailClass userDetailClass)
    {
        return Jwts
                .builder()
                .signWith(keySignature(), SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*24))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(userDetailClass.getUsername())
                .compact();
    }

    public boolean isTokenExpired(String token)
    {
        if (new Date(System.currentTimeMillis()).compareTo(extractClaim(token,Claims::getExpiration))<0)
        {
            return true;
        }

        else return false;
    }

    public boolean isTokenValid(String token, UserDetailClass userDetailClass)
    {
        if (!isTokenExpired(token) &&
                (userDetailClass.getUsername().equals(extractClaim(token,Claims::getSubject))))
        {
            return true;
        }

        else return false;
    }

    public String generateTokenWithClaim(Map<String, Object> cliams, UserDetailClass userDetailClass)
    {
        return Jwts
                .builder()
                .setSubject(userDetailClass.getUsername())
                .signWith(keySignature(),SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*24))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setClaims(cliams)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims,T > extractClaim) {
        Claims claims = extractsAllClaims(token);
        return extractClaim.apply(claims);
    }

    private Claims extractsAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(keySignature())
                .build()
                .parseClaimsJwt(token)
                .getBody();

    }

    private Key keySignature()
    {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyByte);
    }

}
