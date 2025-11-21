package org.create.bankingApplication.fund_transfer_service.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

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
