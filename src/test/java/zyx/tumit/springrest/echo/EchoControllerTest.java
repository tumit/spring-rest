package zyx.tumit.springrest.echo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class EchoControllerTest {

    private MockMvc mvc;

    private JacksonTester<EchoBean> jsonEchoBean;

    @Before
    public void setUp() {
        // init JacksonTester
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);

        // create controller
        EchoController controller = new EchoController();

        // mock mvc
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldHelloAndName() throws Exception {
        // arrange
        String url = "/echo/John";

        // act
        RequestBuilder req = get(url).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mvc.perform(req).andReturn().getResponse();

        // assert
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo("Hello, John");
    }

    @Test
    public void shouldGetEchoBean() throws Exception {
        // arrange
        String url = "/echo/beans/1";

        // act
        RequestBuilder req = get(url).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mvc.perform(req).andReturn().getResponse();

        // assert
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        EchoBean expected = EchoBean.builder()
                .id(1L)
                .name("Fake Name")
                .dob(LocalDate.of(2019, Month.JANUARY, 31))
                .build();
        assertThat(res.getContentAsString()).isEqualTo(jsonEchoBean.write(expected).getJson());

    }
}
