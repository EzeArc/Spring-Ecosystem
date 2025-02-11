package spring.ecosystem.rest_api_template.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.ecosystem.rest_api_template.dto.AuthenticationRequestDTO;
import spring.ecosystem.rest_api_template.dto.AuthenticationResponseDTO;
import spring.ecosystem.rest_api_template.dto.RegistrerUserDTO;
import spring.ecosystem.rest_api_template.dto.SaveUserDTO;
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
    private UserService usuarioServicio;
    @Autowired
    private JwtService jwtService;

    public RegistrerUserDTO registerOneCostumer(SaveUserDTO newUser) throws Exception {
        User user= usuarioServicio.registerOneCostumer(newUser);
        RegistrerUserDTO userDto=new RegistrerUserDTO();
        userDto.setName(user.getUsername());
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setId(user.getId());

        String jwt=jwtService.generateToken(user,generateExtraClaims(user));
        userDto.setJwt(jwt);
        userRepoository.save(user);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String,Object>extraClaims=new HashMap<>();
        extraClaims.put("name",user.getUsername());
        extraClaims.put("role",user.getRole().name());
        extraClaims.put("authorizaties",user.getAuthorities());
        extraClaims.put("userName",user.getUsername());
        return extraClaims;

    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authen) {
        Authentication authentication=new UsernamePasswordAuthenticationToken(authen.getEmail(),authen.getPassword());


        authenticationManager.authenticate(authentication);

        User user= usuarioServicio.findOneByEmail(authen.getEmail());

        String jwt=jwtService.generateToken(user,generateExtraClaims(user));

        AuthenticationResponseDTO authenticationResponseDTO =new AuthenticationResponseDTO();
        authenticationResponseDTO.setJwt(jwt);
        authenticationResponseDTO.setUserName(user.getUsername());
        authenticationResponseDTO.setRol(user.getRole().toString());
        authenticationResponseDTO.setId(user.getId());



        return authenticationResponseDTO;

    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extracEmail(jwt);
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("validateTkoken(stringjwt");
            return false;

        }


    }

    public User findLogginInUser() {
        /*aca basicamente tengo que obtener el usuarui del securutyContextHolder*/
        UsernamePasswordAuthenticationToken auth= (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String user= (String) auth.getPrincipal();
        return usuarioServicio.findOneByEmail(user);

    }

    public AuthenticationResponseDTO validateGetProfile(String jwt) {
        try {
            String username=jwtService.extracEmail(jwt);
            User usuario=usuarioServicio.findOneByEmail(username);
            AuthenticationResponseDTO authenticationResponseDTO =new AuthenticationResponseDTO();
            authenticationResponseDTO.setId(usuario.getId());
            authenticationResponseDTO.setUserName(usuario.getUsername());
            authenticationResponseDTO.setRol(String.valueOf(usuario.getRole()));
            authenticationResponseDTO.setJwt(jwt);
            return authenticationResponseDTO;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
