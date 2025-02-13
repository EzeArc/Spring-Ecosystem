package spring.ecosystem.rest_api_template.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import spring.ecosystem.rest_api_template.dto.ChangePasswordDTO;
import spring.ecosystem.rest_api_template.dto.CreateUserDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;

public interface IUserService {

    UserDTO getUserById(UUID id);

    List<UserDTO> getAllUsers();

    User findOneByEmail(String email);

    Page<UserDTO> getUsersByPageSize(int page, int size);

    UserDTO createUser(CreateUserDTO createUserDTO);

    User updateUser(UserDTO user, UUID id);

    void deactivateUser(UUID id);

    void activateUser(UUID id);

    void deleteUser(UUID id);

    void changePassword(UUID id, ChangePasswordDTO changePasswordDTO);
}
