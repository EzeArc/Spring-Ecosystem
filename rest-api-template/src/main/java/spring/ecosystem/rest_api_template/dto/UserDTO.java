package spring.ecosystem.rest_api_template.dto;

import lombok.Data;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.enums.Role;

@Data
public class UserDTO {
    private String userName;
    private String email;
    private Role role;
    private String password;
    private String repeatePassword;
    private String jwt;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.password = user.getPassword();
        this.repeatePassword = user.getPassword();
    }
}
