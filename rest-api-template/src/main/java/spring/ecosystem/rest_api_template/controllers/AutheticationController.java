package spring.ecosystem.rest_api_template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.ecosystem.rest_api_template.dto.auth.AuthenticationRequestDTO;
import spring.ecosystem.rest_api_template.dto.auth.AuthenticationResponseDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.services.auth.AuthenticateService;

@RestController
@RequestMapping("/api/v1/auth")
public class AutheticationController {
    @Autowired
    private AuthenticateService autheticateService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authetication(@RequestBody AuthenticationRequestDTO authDTO) {
        AuthenticationResponseDTO auth = autheticateService.login(authDTO);
        return ResponseEntity.ok(auth);
    }

    @GetMapping("validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isValidate = autheticateService.validateToken(jwt);
        return ResponseEntity.ok(isValidate);
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
