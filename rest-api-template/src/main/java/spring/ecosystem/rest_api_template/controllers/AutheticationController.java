package spring.ecosystem.rest_api_template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.ecosystem.rest_api_template.dto.AuthenticationRequestDTO;
import spring.ecosystem.rest_api_template.dto.AuthenticationResponseDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.services.auth.AutheticateService;

@RestController
@RequestMapping("/api/v1/auth")
public class AutheticationController {
    @Autowired
    private AutheticateService autheticateService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> Authetication(@RequestBody AuthenticationRequestDTO authen) {
        AuthenticationResponseDTO auth = autheticateService.login(authen);

        return ResponseEntity.ok(auth);
    }

    @GetMapping("validate")
    public boolean validate(@RequestParam String jwt) {

        boolean isValidate = autheticateService.validateToken(jwt);
        return isValidate;


    }

    @GetMapping("/validateGetProfile")
    public ResponseEntity<AuthenticationResponseDTO> validateGetProfile(@RequestParam String jwt) {
        AuthenticationResponseDTO isValidate = autheticateService.validateGetProfile(jwt);
        return ResponseEntity.ok(isValidate);


    }

    @GetMapping("profiles")
    public ResponseEntity<User> MyProfils() {
        User user = autheticateService.findLogginInUser();
        return ResponseEntity.ok(user);


    }
}
