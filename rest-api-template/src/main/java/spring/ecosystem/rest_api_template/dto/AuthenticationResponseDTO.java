package spring.ecosystem.rest_api_template.dto;

import lombok.Data;


import java.util.UUID;

@Data
public class AuthenticationResponseDTO {
    private UUID id;
    private String userName;
    private String rol;
    private String jwt;
    private String email;
}
