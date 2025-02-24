package spring.ecosystem.rest_api_template.dto;

import lombok.Data;
import spring.ecosystem.rest_api_template.enums.Role;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateUserDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
