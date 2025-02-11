package spring.ecosystem.rest_api_template.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;

public interface IUserService {
    User findOneByEmail(String email);

    Page<UserDTO> listAllUsers(Pageable pageable);

    User createUser(UserDTO user);

    User updateUser(UserDTO user, UUID id);

    void deactivateUser(UUID id);

    void activateUser(UUID id);

    void deleteUser(UUID id);
}
