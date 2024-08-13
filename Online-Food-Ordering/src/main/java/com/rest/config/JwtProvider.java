package com.rest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
@Service
public class JwtProvider {

    // Generuje tajný kľúč pre podpisovanie JWT tokenov pomocou HMAC algoritmu
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Metóda na generovanie JWT tokenu pre autentifikovaného používateľa
    public String generateToken(Authentication auth) {
        // Získanie zoznamu rolí (oprávnení) používateľa
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        // Konverzia zoznamu rolí na reťazec
        String roles = populateAuthorities(authorities);

        // Vytvorenie a podpísanie JWT tokenu s potrebnými nárokmi (claims)
        return Jwts.builder()
                .setIssuedAt(new Date()) // Nastavenie času vydania tokenu
                .setExpiration(new Date(new Date().getTime() + 86400000)) // Nastavenie expiračného času (24 hodín)
                .claim("email", auth.getName()) // Pridanie emailu používateľa ako claim
                .claim("authorities", roles) // Pridanie rolí používateľa ako claim
                .signWith(key) // Podpísanie tokenu pomocou tajného kľúča
                .compact(); // Dokončenie a získanie JWT tokenu ako reťazca
    }


    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);

        // Overí a dekóduje JWT token a získa z neho informácie (claimy)
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        // Získa email z claimov

        return String.valueOf(claims.get("email"));
    }

    // Pomocná metóda na konverziu zoznamu rolí na reťazec oddelený čiarkami
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();

        // Pre každý objekt v zozname rolí pridaj jeho názov do setu
        for (GrantedAuthority authority : authorities) {
            auth.add(authority.getAuthority());
        }

        // Spojenie všetkých rolí do jedného reťazca, oddeleného čiarkami
        return String.join(",", auth);
    }
}