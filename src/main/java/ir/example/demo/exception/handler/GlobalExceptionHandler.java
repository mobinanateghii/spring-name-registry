package ir.example.demo.exception.handler;

import ir.example.demo.dto.base.ErrorMessageDto;
import ir.example.demo.exception.DuplicateCustomerException;
import ir.example.demo.exception.RegistryNameNotFoundException;
import ir.example.demo.exception.UniqueNameGenerationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorMessageDto> handleDuplicateCustomerException(DuplicateCustomerException ex) {
        ErrorMessageDto error = ErrorMessageDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UniqueNameGenerationException.class)
    public ResponseEntity<ErrorMessageDto> handleUniqueNameGenerationException(UniqueNameGenerationException ex) {
        ErrorMessageDto error = ErrorMessageDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(RegistryNameNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleRegistryNameNotFoundException(RegistryNameNotFoundException ex) {
        ErrorMessageDto error = ErrorMessageDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> handleGenericException(Exception ex) {
        ErrorMessageDto error = ErrorMessageDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
