package com.rest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Získa JWT token z hlavičky požiadavky
        String jwt = request.getHeader((JwtConstant.JWT_HEADER));

        // Ak je JWT token prítomný, odstráni sa prvých 7 znakov (napr. "Bearer ")
        if (jwt != null) {
            jwt = jwt.substring(7);

            try {
                // Vytvorí tajný kľúč na základe preddefinovaného reťazca
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

                // Overí a dekóduje JWT token a získa z neho informácie (claimy)
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                // Získa email z claimov
                String email = String.valueOf(claims.get("email"));

                // Získa autority (práva) z claimov
                String authorities = String.valueOf(claims.get("authorities"));

                // Konvertuje autority z formátu reťazca do zoznamu GrantedAuthority objektov
                // Napríklad: "ROLE_CUSTOMER, ROLE_ADMIN"
                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Vytvorí autentifikačný objekt na základe emailu a autorít
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorityList);

                // Nastaví autentifikačný objekt do SecurityContextHolder pre aktuálny kontext
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // V prípade chyby (napr. neplatný token) vyhodí výnimku BadCredentialsException
                throw new BadCredentialsException("Neplatný token..");
            }
        }
        // Pokračuje v spracovaní ďalších filtrov v reťazci
        filterChain.doFilter(request, response);
    }
}
