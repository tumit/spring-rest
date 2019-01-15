package zyx.tumit.springrest.echo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/echo")
public class EchoController {

    @GetMapping("/{name}")
    public String echo(@PathVariable("name") final String name) {
        return String.format("Hello, %s", name);
    }

}
