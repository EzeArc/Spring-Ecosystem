package spring.ecosystem.rest_api_template.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.enums.Role;
import spring.ecosystem.rest_api_template.repositories.UserRepoository;

public class UserService implements IUserService {

    private UserRepoository userRepository;

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

    public User createUser(User newUser) {
        // validatePassword(newUser);
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setRole(Role.USER);
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deactivateUser(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deactivateUser'");
    }

    @Override
    public void activateUser(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activateUser'");
    }

    @Override
    public void deleteUser(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

}
