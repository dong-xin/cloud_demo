/*
package com.example.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        //网关API
        routes.route("gateway-provider", r -> r.path("/nacos/**").filters(p -> p.stripPrefix(1)).uri("lb://gateway-provider")).build();
        //Nacos Config
        routes.route("nacos-config", r -> r.path("/nacosConfig/**").filters(p -> p.prefixPath("/api")).uri("lb://nacos-config")).build();
        return routes.build();
    }
}
*/
