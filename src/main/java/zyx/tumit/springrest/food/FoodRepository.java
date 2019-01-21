package zyx.tumit.springrest.food;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodRepository extends CrudRepository<Food, Long> {
    List<Food> findByCaloriesLessThanOrderByCalories(float calories);
}
