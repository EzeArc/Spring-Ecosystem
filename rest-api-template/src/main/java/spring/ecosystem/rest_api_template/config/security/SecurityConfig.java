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
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
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

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
            "/api/test/**", "/authenticate"};


    //    @Bean
//    @Order(2)  // Un orden más bajo para la configuración de seguridad general
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(sessionMagConfig -> sessionMagConfig
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(daoAuthenticationProvider)
//                .addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(authRequestConfig -> {
//                    authRequestConfig.requestMatchers("/oauth2/**", "/login/**").permitAll();
//                    authRequestConfig.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll(); // Login con usuario y contraseña
//                    authRequestConfig.requestMatchers(WHITE_LIST_URL).permitAll();
//                    authRequestConfig.requestMatchers(HttpMethod.GET, "/adminController/listaServiciosAdmin")
//                            .hasRole(Role.ADMIN.name());
//                    authRequestConfig.anyRequest().authenticated();
//                });
//
//        return http.build();
//    }
//    @Autowired
//    private CustomOAuth2UserService oAuth2UserService;
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
                    authRequestConfig.requestMatchers("/oauth2/**", "/login/**").permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll(); // Login con usuario y contraseña
                    authRequestConfig.requestMatchers(WHITE_LIST_URL).permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/adminController/listaServiciosAdmin")
                            .hasRole(Role.ADMIN.name());
                    authRequestConfig.requestMatchers(HttpMethod.POST,"/api/users/register").permitAll();
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
                        })
                );
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
//    @Bean
//    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//    }
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//        return AuthorizationServerSettings.builder()
//                .issuer("http://localhost:8080")
//                .build();
//    }
}
