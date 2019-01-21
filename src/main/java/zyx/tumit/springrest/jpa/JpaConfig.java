package zyx.tumit.springrest.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "defaultAuditorAwareRef")
public class JpaConfig {

    @Bean
    public AuditorAware<String> defaultAuditorAwareRef() {
        return () -> Optional.of("system");
    }
}
