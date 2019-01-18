package zyx.tumit.springrest.echo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
public class EchoControllerTest {

    private MockMvc mvc;

    private JacksonTester<EchoBean> jsonEchoBean;

    @Before
    public void setUp() {
        // init JacksonTester and MappingJackson2HttpMessageConverter
        ObjectMapper objectMapper = createObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);

        // create controller
        EchoController controller = new EchoController();

        // mock mvc
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(converter)
                .build();
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
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
        assertThat(res.getContentAsString()).isEqualTo("\"Hello, John\"");
    }

    @Test
    public void shouldGetEchoBean() throws Exception {
        // arrange
        String url = "/echo/beans/1";

        // act
        RequestBuilder req = get(url).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mvc.perform(req).andReturn().getResponse();

        // assert
        LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 31);
        LocalTime localTime = LocalTime.of(11, 38 , 1);
        EchoBean expected = EchoBean.builder()
                .id(1L)
                .name("Fake Name")
                .dob(localDate)
                .createdDate(LocalDateTime.of(localDate, localTime))
                .build();
        String json = jsonEchoBean.write(expected).getJson();
        log.debug("json={}", json);
        assertThat(res.getContentAsString()).isEqualTo(json);
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
