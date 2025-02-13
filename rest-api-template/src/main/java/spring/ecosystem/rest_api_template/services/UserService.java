package spring.ecosystem.rest_api_template.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.ecosystem.rest_api_template.dto.RegisterUserDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.enums.Role;
import spring.ecosystem.rest_api_template.repositories.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Override
    public Page<RegisterUserDTO> listAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(RegisterUserDTO::new);
    }

    @Override
    public RegisterUserDTO createUser(UserDTO newUser) {
        // validatePassword(newUser);
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setUserName(newUser.getUserName());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
        // SE QUERES DEVOLER EL REGISTRO UNA VEZ CREADA LA CUENTA, AGREGA ESTO
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUserName(newUser.getUserName());
        registerUserDTO.setId(user.getId());
        registerUserDTO.setEmail(newUser.getEmail());
        registerUserDTO.setRole(Role.USER.name());
        return registerUserDTO;
    }

    @Override
    public User updateUser(UserDTO updatedUser, UUID id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    User user = new User(
                            existingUser.getId(),
                            updatedUser.getUserName(),
                            updatedUser.getFirstName(),
                            updatedUser.getLastName(),
                            updatedUser.getEmail(),
                            updatedUser.getPassword() != null ? passwordEncoder.encode(updatedUser.getPassword())
                                    : existingUser.getPassword(),
                            existingUser.getRole());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public void deactivateUser(UUID id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setActive(false);
                    userRepository.save(user);
                });
    }

    @Override
    public void activateUser(UUID id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setActive(true);
                    userRepository.save(user);
                });
    }

    @Override
    public void deleteUser(UUID id) {
        // Aca deberia verificarse si es el usuario mismo q se elimina o si es el admin
        // q lo elimina?
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }

}
