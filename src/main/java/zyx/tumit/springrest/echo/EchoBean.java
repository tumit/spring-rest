package zyx.tumit.springrest.echo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class EchoBean {
    private Long id;
    private String name;
    private LocalDate dob;
    private LocalDateTime createdDate;
}
