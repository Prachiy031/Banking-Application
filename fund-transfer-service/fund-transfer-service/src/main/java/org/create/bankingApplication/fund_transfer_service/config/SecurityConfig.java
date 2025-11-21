package org.create.bankingApplication.fund_transfer_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. Disable CSRF for API services (stateless security policy)
        http.csrf(csrf -> csrf.disable());

        // 2. Configure Authorization:
        //    Allow ALL requests (/**) only if they are authenticated (valid JWT token present).
        //    This means a token is required, but its contents (roles/scopes) are ignored.
        http.authorizeHttpRequests(auth -> auth
            // Public endpoints (e.g., health/info)
            .requestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
            // All other application endpoints require an authenticated user
            .anyRequest().authenticated()
        );

        // 3. Configure Resource Server (JWT validation)
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
            // Default JWT decoder will be automatically created using properties like spring.security.oauth2.resourceserver.jwt.jwk-set-uri
        }));

        // 4. Set Session Management to STATELESS (No sessions/cookies are used)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
