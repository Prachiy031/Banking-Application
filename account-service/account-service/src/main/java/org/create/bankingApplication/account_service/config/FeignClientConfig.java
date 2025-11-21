package org.create.bankingApplication.account_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
  Configuration for Feign clients to ensure the Authorization header (JWT)
  is propagated from the incoming request to the outgoing inter-service call.
  
  makes sure that the security credentials used to authenticate 
  the original user are passed down to any other microservice 
  needed to complete that user's request.
 */
@Configuration
public class FeignClientConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Get the current request attributes (from the incoming user request)
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (Objects.nonNull(attributes)) {
                    // Extract the Authorization header value (e.g., "Bearer eyJ...")
                    String authorizationHeader = attributes.getRequest().getHeader(AUTHORIZATION_HEADER);

                    if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                        // Forward the header to the outgoing Feign request
                        template.header(AUTHORIZATION_HEADER, authorizationHeader);
                    }
                }
            }
        };
    }
}