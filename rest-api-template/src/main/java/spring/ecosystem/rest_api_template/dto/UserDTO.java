package spring.ecosystem.rest_api_template.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.enums.Role;

@Data
public class UserDTO {

    private UUID id;

    @NotBlank(message = "El nombre no puede estar en blanco.")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar en blanco.")
    private String lastName;

    @Email(message = "El email debe ser válido.")
    private String email;

    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Size(min = 3, message = "El nombre de usuario debe contener al menos 3 caracteres.")
    private String userName;

    @NotBlank(message = "La contraseña no puede estar en blanco.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;

    @NotNull(message = "El rol no puede ser nulo.")
    private Role role;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.userName = user.getUserName();
    }
}
