package finki.emt.labs.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CountryWithIdNotFound extends RuntimeException {
    public CountryWithIdNotFound(Long id) {
        super(String.format("The country with id %d is not found", id));
    }
}
