package zyx.tumit.springrest.echo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
public class EchoControllerTest {

    private MockMvc mvc;
    private JacksonTester<String> jsonMessageResult;

    @Test
    public void shouldHelloAndName() throws Exception {
        EchoController controller = new EchoController();
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();

        String url = "/echo/John";
        RequestBuilder req = get(url).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mvc.perform(req).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo("Hello, John");
    }
}
