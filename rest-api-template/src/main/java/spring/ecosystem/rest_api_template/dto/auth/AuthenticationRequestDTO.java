package spring.ecosystem.rest_api_template.dto.auth;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
