package org.create.bankingApplication.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*creats hashed password using inbuilt method provided by BCryptPasswordEncoder's instance*/
@Configuration
public class ConfigurationsForPassword {
	/*
      Creates and returns a BCryptPasswordEncoder instance.
     
     return The BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
