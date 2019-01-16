package zyx.tumit.springrest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import zyx.tumit.springrest.ldap.LdapUserRepository;

@Slf4j
@SpringBootApplication
public class SpringRestApplication {

    @Autowired
    private LdapUserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String username = "ostumit";
            String password = "Jan#2019";
            log.debug("authed={}", repository.authenticate(username,password));
        };
    }

}

