package zyx.tumit.springrest.food;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/foods")
public class FoodController {

    private final FoodRepository repository;


}
