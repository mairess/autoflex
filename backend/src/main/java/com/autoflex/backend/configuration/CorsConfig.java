package com.autoflex.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Cors config.
 */
@Configuration
public class CorsConfig {

  @Value("${app.frontend.url}")
  private String frontendUrl;

  /**
   * Cors configurer web mvc configurer.
   *
   * @return the web mvc configurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CORS allowed for: " + frontendUrl);
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173", frontendUrl)
            .allowedMethods("*")
            .allowedHeaders("*");
      }
    };
  }
}