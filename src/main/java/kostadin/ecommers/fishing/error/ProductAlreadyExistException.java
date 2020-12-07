package kostadin.ecommers.fishing.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product exist")
public class ProductAlreadyExistException extends RuntimeException {
    private int statusCode;

    public ProductAlreadyExistException(String message) {
        super(message);
        this.statusCode = 409;
    }

public int getStatusCode() {
        return this.statusCode;
}
}
