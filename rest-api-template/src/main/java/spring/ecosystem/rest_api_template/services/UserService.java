package spring.ecosystem.rest_api_template.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public Page<UserDTO> listAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDTO::new);
    }

    public User createUser(UserDTO newUser) {
        // validatePassword(newUser);
        User user = new User();
        user.setUserName(newUser.getUserName());
        user.setRole(Role.USER);
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deactivateUser(UUID id) {
    }

    @Override
    public void activateUser(UUID id) {
    }

    @Override
    public void deleteUser(UUID id) {
    }

}
