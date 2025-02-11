package spring.ecosystem.rest_api_template.dto;

import lombok.Data;

@Data
public class RegistrerUserDTO {
    private String id;
    private String userName;
    private String email;
    private String name;
    private String role;
    private String jwt;
}
