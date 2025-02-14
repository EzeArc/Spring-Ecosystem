package spring.ecosystem.rest_api_template.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private UUID id;
    private String userName;
    private String rol;
    private String jwt;
    private String email;
}
