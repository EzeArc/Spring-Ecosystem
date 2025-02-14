package spring.ecosystem.rest_api_template.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.ecosystem.rest_api_template.dto.auth.AuthenticationRequestDTO;
import spring.ecosystem.rest_api_template.dto.auth.AuthenticationResponseDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.services.impl.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticateService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    // public User registerUser(UserDTO user) throws Exception {
    // User userRequest = userService.createUser(user);
    // UserDTO userDto = new UserDTO();
    // userDto.setUserName(user.getUserName());
    // userDto.setEmail(user.getEmail());
    // userDto.setRole(Role.valueOf(user.getRole().name()));
    // String jwt = jwtService.generateToken(userRequest,
    // generateExtraClaims(userRequest));
    // userDto.setJwt(jwt);
    // return userRequest;
    // }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", user.getEmail());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities", user.getAuthorities());
        extraClaims.put("userName", user.getUserName());
        return extraClaims;
    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                authRequest.getPassword());

        authenticationManager.authenticate(authentication);
        User user = userService.findOneByEmail(authRequest.getEmail());
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponseDTO(user.getId(), user.getUserName(),
                user.getRole().toString(),jwt, user.getEmail());
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractEmail(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public User findLogginInUser() {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();

        String user = (String) auth.getPrincipal();
        return userService.findOneByEmail(user);

    }

    public AuthenticationResponseDTO validateGetProfile(String jwt) {
        try {
            String userName = jwtService.extractEmail(jwt);
            User user = userService.findOneByEmail(userName);
            return new AuthenticationResponseDTO(
                    user.getId(),
                    user.getUserName(),
                    user.getRole().toString(),
                    jwt,
                    user.getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
