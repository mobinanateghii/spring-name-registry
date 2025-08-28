package ir.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseEntity {

    private Long authorUserId;

    @CreatedDate
    private LocalDateTime createdDate;

    private LocalDateTime thruDate;

    @Version
    private Timestamp version;
}
