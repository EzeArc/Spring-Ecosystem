package spring.ecosystem.rest_api_template.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
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

import spring.ecosystem.rest_api_template.config.filters.CustomOAuth2SuccessHandler;
//import spring.ecosystem.rest_api_template.config.filters.CustomOAuth2UserService;
import spring.ecosystem.rest_api_template.config.filters.JwtAuthenticateFilter;
import spring.ecosystem.rest_api_template.enums.Role;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthenticationProvider daoAuthenticationProvider;

    @Autowired
    private JwtAuthenticateFilter jwtAuthenticateFilter;

    @Autowired
    private CustomOAuth2SuccessHandler successHandler;

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
                    authRequestConfig.requestMatchers(WHITE_LIST_URL).permitAll();
                    authRequestConfig.requestMatchers(PUBLIC_LIST_URL_PUBLIC).permitAll();
                    authRequestConfig.requestMatchers(PRIVATE_LIST_URL_USER).hasRole(Role.USER.name());
                    authRequestConfig.requestMatchers(PRIVATE_LIST_URL_ADMIN).hasRole(Role.ADMIN.name());
                    authRequestConfig.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler) // Usa el CustomOAuth2SuccessHandler
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler) // Usa el CustomOAuth2SuccessHandler
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        }));
        return http.build();
    }

    // swagger end-points
    private static final String[] WHITE_LIST_URL = {
            "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
            "/swagger-resources/**", "/configuration/ui", "/configuration/security",
            "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/test/**"
    };

    // PUBLIC ENDPOINTS
    private static final String[] PUBLIC_LIST_URL_PUBLIC = {
            "/api/v1/auth/login", "/api/v1/auth/validate", "/users/register",
    };

    // PRIVATE ENDPOINTS
    private static final String[] PRIVATE_LIST_URL_USER = {
            "/api/v1/auth/validateGetProfile", "/api/v1/auth/profiles", "/users/{id}",
            "/users/page", "/users/{id}/change-password", "/users/{id}/deactivate-account",
            "/users/{id}/activate-account",
    };

    private static final String[] PRIVATE_LIST_URL_ADMIN = { "/admin" };

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

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}
