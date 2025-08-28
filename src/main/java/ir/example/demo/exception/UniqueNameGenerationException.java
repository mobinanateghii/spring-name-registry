package ir.example.demo.exception;

public class UniqueNameGenerationException extends RuntimeException {
    public UniqueNameGenerationException(String message) {
        super(message);
    }

    public UniqueNameGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
