package spring.ecosystem.rest_api_template.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.ecosystem.rest_api_template.dto.AuthenticationRequestDTO;
import spring.ecosystem.rest_api_template.dto.AuthenticationResponseDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.repositories.UserRepoository;
import spring.ecosystem.rest_api_template.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AutheticateService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepoository userRepoository;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    public UserDTO registerUser(UserDTO user) throws Exception {
        User user = userService.createUser(user);
        UserDTO userDto = new UserDTO();
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        userDto.setJwt(jwt);
        userRepoository.save(user);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", user.getEmail());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorizaties", user.getAuthorities());
        extraClaims.put("userName", user.getUserName());
        return extraClaims;

    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authen) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authen.getEmail(),
                authen.getPassword());

        authenticationManager.authenticate(authentication);

        User user = userService.findOneByEmail(authen.getEmail());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setJwt(jwt);
        authenticationResponseDTO.setUserName(user.getUserName());
        authenticationResponseDTO.setRol(user.getRole().toString());
        authenticationResponseDTO.setId(user.getId());

        return authenticationResponseDTO;

    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extracEmail(jwt);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("validateTkoken(stringjwt");
            return false;

        }

    }

    public User findLogginInUser() {
        /* aca basicamente tengo que obtener el usuarui del securutyContextHolder */
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();

        String user = (String) auth.getPrincipal();
        return userService.findOneByEmail(user);

    }

    public AuthenticationResponseDTO validateGetProfile(String jwt) {
        try {
            String userName = jwtService.extracEmail(jwt);
            User usuario = userService.findOneByEmail(userName);
            AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
            authenticationResponseDTO.setId(usuario.getId());
            authenticationResponseDTO.setUserName(usuario.getUserName());
            authenticationResponseDTO.setRol(String.valueOf(usuario.getRole()));
            authenticationResponseDTO.setJwt(jwt);
            return authenticationResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
