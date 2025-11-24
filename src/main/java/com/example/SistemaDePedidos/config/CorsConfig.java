package com.example.SistemaDePedidos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                    "https://sistema-de-pedidos-quay.vercel.app/",
                    "https://sistema-de-pedidos-quay-git-main-nicols-projects-baf675f0.vercel.app/",
                    "https://sistema-de-pedidos-git-main-nicols-projects-baf675f0.vercel.app",
                    "http://localhost:3000", 
                    "http://localhost:5173"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
