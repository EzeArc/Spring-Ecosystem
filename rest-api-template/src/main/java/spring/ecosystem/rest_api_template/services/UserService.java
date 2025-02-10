package spring.ecosystem.rest_api_template.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.repositories.UserRepoository;

public class UserService implements IUserService {

    private UserRepoository userRepository;

    @Override
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

}
