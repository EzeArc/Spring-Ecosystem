package spring.ecosystem.rest_api_template.dto;

import lombok.Data;
import spring.ecosystem.rest_api_template.entities.User;

import java.util.UUID;

@Data
public class RegisterUserDTO {
    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String jwt;

    public RegisterUserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.jwt = jwt;
    }

    public RegisterUserDTO() {
    }
}

