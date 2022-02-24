package pl.edu.pg.eti.aui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Application
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("delivery/methods", r -> r
                        .path("/api/delivery/methods/{carrier}", "/api/delivery/methods")
                        .uri("http://172.28.1.22:8080"))
                .route("orders", r -> r
                        .path("/api/orders", "/api/orders/**", "/api/delivery/methods/{carrier}/orders",
                                "/api/delivery/methods/{carrier}/orders/**")
                        .uri("http://172.28.1.23:8080"))
                .route("invoices", r -> r
                        .path("/api/invoices", "/api/invoices/**", "/api/invoices/{id}",
                                "/api/invoices/{id}/download")
                        .uri("http://172.28.1.24:8080"))
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
