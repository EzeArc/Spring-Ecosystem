package spring.ecosystem.rest_api_template.config.filters;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.enums.Role;
import spring.ecosystem.rest_api_template.repositories.UserRepository;
import spring.ecosystem.rest_api_template.services.auth.JwtService;
import spring.ecosystem.rest_api_template.services.impl.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String nombre = oauth2User.getAttribute("name");

        // Verifica si el usuario ya existe en la base de datos
        User user = usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    User nuevoUsuario = new User();
                    nuevoUsuario.setEmail(email);
                    nuevoUsuario.setPassword(passwordEncoder.encode("123456"));
                    nuevoUsuario.setUserName(nombre);
                    nuevoUsuario.setRoles(Set.of(Role.USER)); // Asegúrate de que está bien definido
                    return usuarioRepository.save(nuevoUsuario); // Primero guardamos el usuario en la BD
                });

        // Generamos el JWT después de asegurar que el usuario está guardado
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        // Devuelve el token como respuesta
        response.setContentType("application/json");
        response.getWriter().write("{\"te mande el token por headers pero aca lo tenes igual\": \"" + jwt + "\"}");
        response.setHeader("Authorization", jwt);
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", user.getEmail());
        extraClaims.put("role", user.getRoles());
        extraClaims.put("authorities", user.getAuthorities());
        extraClaims.put("userName", user.getUserName());
        return extraClaims;
    }


}
