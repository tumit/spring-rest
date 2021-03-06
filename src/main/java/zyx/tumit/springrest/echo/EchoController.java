package zyx.tumit.springrest.echo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

@RestController()
@RequestMapping("/echo")
public class EchoController {

    @GetMapping("/{name}")
    public String echo(@PathVariable("name") final String name) {
        return String.format("Hello, %s", name);
    }

    @GetMapping("/beans/{id}")
    public EchoBean findOne(@PathVariable("id") final long id) {
        LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 31);
        LocalTime localTime = LocalTime.of(11, 38 , 1);
        return EchoBean.builder()
                .id(id)
                .name("Fake Name")
                .dob(localDate)
                .createdDate(LocalDateTime.of(localDate, localTime))
                .build();
    }
}
