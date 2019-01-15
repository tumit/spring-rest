package zyx.tumit.springrest.echo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EchoBean {
    private Long id;
    private String name;
    private LocalDate dob;
}
