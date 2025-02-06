package spring.ecosystem.rest_api_template.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.ecosystem.rest_api_template.entities.User;

public interface UserRepoository extends JpaRepository<User, UUID> {

}
