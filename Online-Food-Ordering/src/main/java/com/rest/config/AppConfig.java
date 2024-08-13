package com.rest.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    // Konfigurácia bezpečnostného reťazca (SecurityFilterChain)
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Konfigurácia správy relácií na bezstavovú (každá požiadavka je nezávislá)

                .authorizeHttpRequests(Autorize -> Autorize.requestMatchers("/api/admin/**")
                        // Požiadavky na URL adresy začínajúce "/api/admin/**" sú povolené len pre ROLE_RESTAURANT_OWNER a ROLE_ADMIN
                        .hasAnyRole("RESTAURANT_OWNER", "ADMIN")

                        .requestMatchers("/api**")
                        // Ostatné požiadavky na "/api**" musia byť autentifikované
                        .authenticated()

                        .anyRequest().permitAll())
                // Ostatné požiadavky sú povolené pre všetkých

                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // Pridáva JWT token validator pred BasicAuthenticationFilter

                .csrf(AbstractHttpConfigurer::disable)
                // Zakazuje ochranu pred CSRF útokmi

                .cors(cors -> cors.configurationSource(corsConfigurationsSource()));
        // Povolenie CORS s konfiguráciou z metódy corsConfigurationsSource()

        return http.build();
    }

    // Konfigurácia CORS (Cross-Origin Resource Sharing)
    private CorsConfigurationSource corsConfigurationsSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();

                // Povolené zdroje pre CORS
                cfg.setAllowedOrigins(Arrays.asList(
                        "https://zosh/food.vercel.app",
                        "https://localhost:3000"
                ));

                // Povolené HTTP metódy (napr. GET, POST, atď.)
                cfg.setAllowedMethods(Collections.singletonList("*"));

                // Povolenie zdieľania poverení (cookies, autorizačné hlavičky, atď.)
                cfg.setAllowCredentials(true);

                // Povolené hlavičky v požiadavkách
                cfg.setAllowedHeaders(Collections.singletonList("*"));

                // Hlavičky, ktoré budú vystavené v odpovediach
                cfg.setExposedHeaders(Arrays.asList("Authorization"));

                // Nastavenie max. veku (v sekundách) pre uloženie výsledkov CORS prehliadačom
                cfg.setMaxAge(3600L);

                return cfg;
            }
        };
    }

    // Konfigurácia enkodéra hesiel na BCryptPasswordEncoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}