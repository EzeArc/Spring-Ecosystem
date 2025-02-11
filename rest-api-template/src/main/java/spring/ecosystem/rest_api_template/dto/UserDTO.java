package spring.ecosystem.rest_api_template.dto;

import lombok.Data;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.enums.Role;

@Data
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String repeatePassword;
    private String userName;
    private Role role;
    private String jwt;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.repeatePassword = user.getPassword();
        this.role = user.getRole();
        this.userName = user.getUserName();
    }
}
