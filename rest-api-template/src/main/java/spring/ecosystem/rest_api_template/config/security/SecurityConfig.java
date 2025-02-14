package spring.ecosystem.rest_api_template.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import spring.ecosystem.rest_api_template.config.filters.JwtAuthenticateFilter;
import spring.ecosystem.rest_api_template.enums.Role;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Autowired
        private AuthenticationProvider daoAuthenticationProvider;

        @Autowired
        private JwtAuthenticateFilter jwtAuthenticateFilter;

        private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
                        "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
                        "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
                        "/api/test/**", "/authenticate" };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sessionMagConfig -> sessionMagConfig
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(daoAuthenticationProvider)
                                .addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class)
                                .authorizeHttpRequests(authRequestConfig -> {
                                        authRequestConfig
                                                        .requestMatchers(HttpMethod.GET,
                                                                        "/adminController/listaServiciosAdmin")
                                                        .hasRole(Role.ADMIN.name());
                                        authRequestConfig.requestMatchers(WHITE_LIST_URL)
                                                        .permitAll().anyRequest().authenticated();
                                });
                return http.build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                configuration.setAllowedMethods(Arrays.asList("*"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);
                configuration.setExposedHeaders(Arrays.asList("Set-Cookie"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
