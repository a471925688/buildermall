//package com.noah_solutions.interceptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.addAllowedOrigin("http://localhost:8080"); // 1允许任何域名使用
////        corsConfiguration.addAllowedOrigin("http://192.168.1.116:8080"); // 1允许任何域名使用
////        corsConfiguration.addAllowedOrigin("http://localhost:9988"); // 1允许任何域名使用
////        corsConfiguration.addAllowedOrigin("http://localhost:63342"); // 1允许任何域名使用
////        corsConfiguration.addAllowedOrigin("http://localhost:9090"); // 1允许任何域名使用
//        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
//        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
//        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }
//}
