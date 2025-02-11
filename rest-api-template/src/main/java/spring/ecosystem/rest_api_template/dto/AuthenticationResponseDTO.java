package spring.ecosystem.rest_api_template.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {
    private String id;
    private String userName;
    private String rol;
    private String jwt;
    private String email;
}
