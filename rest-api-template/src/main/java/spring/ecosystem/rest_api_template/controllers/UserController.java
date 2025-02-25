package spring.ecosystem.rest_api_template.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import spring.ecosystem.rest_api_template.dto.ChangePasswordDTO;
import spring.ecosystem.rest_api_template.dto.CreateUserDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.services.impl.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserDTO>> getUsersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUsersByPageSize(page, size));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Validated @RequestBody CreateUserDTO createUserDTO) throws Exception {
        return new ResponseEntity<>(userService.createUser(createUserDTO), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDTO userDTO, @PathVariable UUID id) {
        try {
            return ResponseEntity.ok(userService.updateUser(userDTO, id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable UUID id,
            @RequestBody @Validated ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(id, changePasswordDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate-account")
    public ResponseEntity<String> activateUserAccount(@PathVariable UUID id) {
        try {
            userService.activateUser(id);
            return ResponseEntity.ok("Usuario activado exitosamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/deactivate-account")
    public ResponseEntity<String> deactivateUserAccount(@PathVariable UUID id) {
        try {
            userService.deactivateUser(id);
            return ResponseEntity.ok("Usuario desactivado exitosamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
