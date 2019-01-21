package zyx.tumit.springrest.food;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zyx.tumit.springrest.exception.FindByIdNotFoundException;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class FoodController {
    private final FoodRepository repository;

    @GetMapping("/foods")
    public List<Food> findAll() {
        return repository.findAll();
    }

    @GetMapping("/foods/{id}")
    public Food findOne(@PathVariable long id) throws FindByIdNotFoundException {
        return repository.findById(id)
                         .orElseThrow(()-> new FindByIdNotFoundException(id, Food.class.getSimpleName()));
    }
}
