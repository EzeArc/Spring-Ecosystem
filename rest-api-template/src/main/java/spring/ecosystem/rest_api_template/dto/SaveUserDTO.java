package spring.ecosystem.rest_api_template.dto;

import lombok.Data;

@Data
public class SaveUserDTO {
    private String name;
    private String userName;
    private String email;
    private String password;
    private String repeatePassword;
}
