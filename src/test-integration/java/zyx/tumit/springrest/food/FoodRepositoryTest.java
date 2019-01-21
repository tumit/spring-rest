package zyx.tumit.springrest.food;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodRepositoryTest {

    @Autowired
    private FoodRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.save(Food.builder().id(1L).name("Apple").calories(300F).build());
        repository.save(Food.builder().id(2L).name("Banana").calories(200F).build());
        repository.save(Food.builder().id(3L).name("Potato").calories(100F).build());
    }

    @Test
    public void shouldFindByIdSuccessful() {
        // arrange
        // act
        Optional<Food> food = repository.findById(1L);
        // assert
        food.ifPresent(f -> log.info("food={}", f));
        assertThat(food.isPresent()).isTrue();
    }

    @Test
    public void shouldFindByLessThanCaloriesSuccess() {
        // arrange
        // act
        List<Food> foods = repository.findByCaloriesLessThanOrderByCalories(201F);
        // assert
        foods.forEach(food -> log.info("food={}", food));
        assertThat(foods.size()).isEqualTo(2);
    }
}
