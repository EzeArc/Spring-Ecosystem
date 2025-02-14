package spring.ecosystem.rest_api_template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import spring.ecosystem.rest_api_template.audit.AuditorAwareImp;

@Configuration
@EnableJpaAuditing
public class PersistenseConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImp();
    }

}
