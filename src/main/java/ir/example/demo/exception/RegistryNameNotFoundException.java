package ir.example.demo.exception;

public class RegistryNameNotFoundException extends RuntimeException {
    public RegistryNameNotFoundException(String message) {
        super(message);
    }

    public RegistryNameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
