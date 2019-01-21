package zyx.tumit.springrest.echo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import zyx.tumit.springrest.exception.FindByIdNotFoundException;
import zyx.tumit.springrest.food.Food;
import zyx.tumit.springrest.food.FoodController;
import zyx.tumit.springrest.food.FoodRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
public class FoodControllerTest {

    private MockMvc mvc;

    private JacksonTester<List<Food>> jsonFoods;
    private JacksonTester<Food> jsonFood;

    private FoodRepository repository;
    private List<Food> stubFoods;

    @Before
    public void setUp() {
        // init JacksonTester and MappingJackson2HttpMessageConverter
        ObjectMapper objectMapper = ObjectMapperHelper.createObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);

        // create controller
        repository = mock(FoodRepository.class);
        FoodController controller = new FoodController(repository);

        // mock mvc
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                                  .setMessageConverters(converter)
                                  .build();

        // when
        Food apple = Food.builder()
                         .id(1L)
                         .name("apple")
                         .build();
        Food mango = Food.builder()
                         .id(2L)
                         .name("mango")
                         .build();
        stubFoods = Arrays.asList(apple, mango);

    }

    @Test
    public void shouldFoundAllWhenFindAll() throws Exception {
        // arrange
        String url = "/foods";

        // when
        when(repository.findAll()).thenReturn(stubFoods);

        // act
        RequestBuilder req = get(url).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mvc.perform(req).andReturn().getResponse();

        // assert
        String expectJson = jsonFoods.write(stubFoods).getJson();
        assertThat(res.getContentAsString()).isEqualTo(expectJson);
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void shouldNotFoundWhenFindByExistId() throws Exception {
        // arrange
        String url = "/foods/1";

        // when
        when(repository.findById(1L)).thenReturn(Optional.of(stubFoods.get(0)));

        // act
        RequestBuilder req = get(url).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mvc.perform(req).andReturn().getResponse();

        // assert
        String expectJson = jsonFood.write(stubFoods.get(0)).getJson();
        assertThat(res.getContentAsString()).isEqualTo(expectJson);
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void shouldNotFoundWhenFindByNotExistId() throws Exception {
        // arrange
        String url = "/foods/999";

        // when
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // act
        RequestBuilder req = get(url).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mvc.perform(req)
                                         .andDo(print())
                                         .andReturn()
                                         .getResponse();

        // assert
        assertThat(res.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
