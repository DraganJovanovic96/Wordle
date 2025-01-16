package com.wordle.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
/**
 * SecurityConfiguration is a configuration class that defines the security settings and filters for the application.
 * It enables web security, method security, and configures the security filter chain.
 *
 * @author Dragan Jovanovic
 * @version 1.0
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Value("${spring.frontend.url}")
    private String frontendUrl;

    /**
     * Configures CORS settings for the application.
     *
     * <p>This method defines the allowed origins, HTTP methods, and headers for CORS requests.
     * It also specifies whether credentials (such as cookies or authorization headers) are allowed.</p>
     *
     * @return a {@link CorsConfigurationSource} that provides the CORS configuration for the application.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type","Refresh"));
        configuration.setExposedHeaders(List.of("X-Total-Items", "X-Total-Pages", "X-Current-Page", "Authorization", "Refresh"));
        configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
