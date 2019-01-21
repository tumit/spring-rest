package zyx.tumit.springrest.food;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import zyx.tumit.springrest.jpa.JpaConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaConfig.class)
public class FoodRepositoryTest {

    @Autowired
    private FoodRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.save(Food.builder().name("Apple").calories(300F).build());
        repository.save(Food.builder().name("Banana").calories(200F).build());
        repository.save(Food.builder().name("Potato").calories(100F).build());
    }

    @Test
    public void findById() {
        // arrange
        // act
        Optional<Food> food = repository.findById(1L);
        // assert
        food.ifPresent(f -> log.info("food={}", f));
        assertThat(food.isPresent()).isTrue();
    }

    @Test
    public void lessThan201Calories() {
        // arrange
        // act
        List<Food> foods = repository.findByCaloriesLessThanOrderByCalories(201F);
        // assert
        foods.forEach(food -> log.info("food={}", food));
        assertThat(foods.size()).isEqualTo(2);
    }
}
