package ir.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NameRegistryFilterDto {
    private Long id;
    private String firstName;
    private String lastName;

    private Date fromCreatedDate;
    private Date toCreatedDate;

    private Date fromThruDate;
    private Date toThruDate;

    private int from;
    private int size;
}
