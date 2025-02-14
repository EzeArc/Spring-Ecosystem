package spring.ecosystem.rest_api_template.dto;

import lombok.Data;
import spring.ecosystem.rest_api_template.enums.Role;

@Data
public class CreateUserDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
