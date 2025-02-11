package spring.ecosystem.rest_api_template.configSecurity;

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
import spring.ecosystem.rest_api_template.configSecurity.filter.JwtAutheticateFilter;
import spring.ecosystem.rest_api_template.enums.Role;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class httpSecurityConfig {
    @Autowired
    private AuthenticationProvider daoAuthenticationProvider;

    @Autowired
    private JwtAutheticateFilter jwtAuthenticateFilter;

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
                    authRequestConfig
                            .requestMatchers(HttpMethod.GET,
                                    "/adminController/listaTurnoAdmin")
                            .hasRole(Role.ADMIN.name());
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/api/v1/user")
                            .hasRole(Role.ADMIN.name());
                    authRequestConfig
                            .requestMatchers(HttpMethod.POST,
                                    "/adminController/crearServicio")
                            .hasRole(Role.ADMIN.name());
                    authRequestConfig
                            .requestMatchers(HttpMethod.PUT,
                                    "/adminController/AltaBajaServicio/{id}")
                            .hasRole(Role.ADMIN.name());
                    authRequestConfig
                            .requestMatchers(HttpMethod.PUT,
                                    "/adminController/AltaBaja/{id}")
                            .hasRole(Role.ADMIN.name());
                    authRequestConfig
                            .requestMatchers(HttpMethod.PUT,
                                    "/adminController/ModificarServicio")
                            .hasRole(Role.ADMIN.name());
                    authRequestConfig
                            .requestMatchers(HttpMethod.PUT,
                                    "/adminController/suspendeTurnoAdmin/{turnoId}")
                            .permitAll();
                    authRequestConfig
                            .requestMatchers(HttpMethod.POST,
                                    "/adminController/agregarFeriadoAdmin")
                            .permitAll();
                    authRequestConfig
                            .requestMatchers(HttpMethod.GET,
                                    "/adminController/listaTurnoDelMesAdmin/{date}")
                            .permitAll();
                    authRequestConfig
                            .requestMatchers(HttpMethod.GET,
                                    "/adminController/filtrarTurnoPor")
                            .permitAll();

                    authRequestConfig.requestMatchers(HttpMethod.GET, "/api/v1/auth/is-token-valid")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/api/v1/auth/validate")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/api/v1/servicios")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/portal/listaSerivicios")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/portal/servicioPodo/{id}")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/Turnos/cancelarTurno/{id}")
                            .hasRole(Role.USER.name());
                    authRequestConfig.requestMatchers(HttpMethod.GET, "/Turnos/listaTurnos/{id}")
                            .hasRole(Role.USER.name());
                    authRequestConfig.requestMatchers(HttpMethod.POST, "/api/v1/auth/authenticate")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.POST, "/api/v1/auth/google")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.POST, "/api/v1/auth/send-email")
                            .permitAll();
                    authRequestConfig.requestMatchers(HttpMethod.POST, "/api/v1/register")
                            .permitAll();
                    authRequestConfig.anyRequest().authenticated();
                });
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://podogonnet.netlify.app",
                "https://podofrontdeploy.onrender.com", "http://localhost:5173",
                "https://accounts.google.com"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Set-Cookie"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
