package spring.ecosystem.rest_api_template.configSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import spring.ecosystem.rest_api_template.Auditor.AuditorAwareImp;

@Configuration
@EnableJpaAuditing
public class PersistenseConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImp();
    }

}
