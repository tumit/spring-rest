package zyx.tumit.springrest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import zyx.tumit.springrest.food.Food;
import zyx.tumit.springrest.food.FoodRepository;

@Slf4j
@SpringBootApplication
public class SpringRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(FoodRepository repository) {
        return (args)  -> {
            log.info("args={}", args);
            repository.save(Food.builder().name("Apple").calories(300F).build());
            repository.save(Food.builder().name("Banana").calories(200F).build());
            repository.save(Food.builder().name("Potato").calories(100F).build());
        };
    }

}

