package zyx.tumit.springrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class FindByIdNotFoundException extends RuntimeException {

    public FindByIdNotFoundException(long id, String entity) {
        super(String.format("FindById not found: id=%s, entity=%s", id, entity));
    }
}
