package spring.ecosystem.rest_api_template.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.ecosystem.rest_api_template.entities.ClientApp;

import java.util.Optional;

public interface ClientAppRepository extends JpaRepository<ClientApp,Long> {
    Optional<ClientApp>findByClientId(String id);
}
