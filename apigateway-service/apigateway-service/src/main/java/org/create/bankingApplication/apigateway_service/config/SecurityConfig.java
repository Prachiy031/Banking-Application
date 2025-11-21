package org.create.bankingApplication.apigateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


/*
   Reactive Security Configuration for the API Gateway
   {This explicit configuration forces Spring Security to use the spring WebFlux and netty (reactive) stack which is non-blocking stack 
   resolving the 'jakarta.servlet.Filter' ClassNotFoundException.}
   
   Goal => The Gateway acts as both an OAuth2 Resource Server (to validate incoming tokens) 
  
  Reactive stack => 1. Uses small number of threads {Netty's event loop} which handles thousands of connections simultaneously  
  					2. Non-blocking thread management {Threads do not wait for slow operations like calling a downstream service}
  					3. Processes requests asynchronously using fast events and callbacks
  					4. Enables entire spring cloud gateway to be stateless{doesen't remember any past interactions or details of client as every request checks jwt token which has all permissions and identity of client}
 */
@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity(prePostEnabled = true) // Allows the use of @PreAuthorize
public class SecurityConfig {	
	@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                /* 
                  1. Disable CSRF Gateways are typically stateless and token-based
                 */
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                
                /* 
                  2. Configure authorization rules for exchanges (requests)
                 */
                .authorizeExchange(exchanges -> exchanges
                    // Allow access to essential service discovery and health check endpoints
                    .pathMatchers("/eureka/**", "/actuator/**").permitAll() 
                    // All other requests must be authenticated
                    .anyExchange().authenticated()
                )
                
                /*
                  3. Enable OAuth2 Resource Server to validate JWTs in the Authorization header of every request
                 */
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {})) 
                
                .build();
    }
}
