package spring.ecosystem.rest_api_template.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import spring.ecosystem.rest_api_template.dto.ChangePasswordDTO;
import spring.ecosystem.rest_api_template.dto.CreateUserDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.mapper.UserMapper;
import spring.ecosystem.rest_api_template.repositories.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID: " + id));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    // *Pageable* (interfaz) se utiliza para especificar los parámetros de
    // paginación en una
    // consulta.
    // *Page* se utiliza para encapsular los resultados de una consulta paginada y
    // la información de paginación.
    @Override
    public Page<UserDTO> getUsersByPageSize(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(userMapper::userToUserDTO);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.createUserDTOToUser(createUserDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.userToUserDTO(user);
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
                    user.setIsActive(false);
                    userRepository.save(user);
                });
    }

    @Override
    public void activateUser(UUID id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setIsActive(true);
                    userRepository.save(user);
                });
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }

    @Override
    public void changePassword(UUID id, ChangePasswordDTO changePasswordDTO) {
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                    userRepository.save(user);
                });
    }

}
