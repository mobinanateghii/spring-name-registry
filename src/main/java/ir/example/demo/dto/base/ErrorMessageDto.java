package ir.example.demo.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessageDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
