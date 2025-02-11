package spring.ecosystem.rest_api_template.dto;

import lombok.Data;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.enums.Role;

@Data
public class UserDTO {
    private String username;
    private String email;
    private Role role;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
