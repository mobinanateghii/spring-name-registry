package ir.example.demo.dto.filter;

import ir.example.demo.dto.base.DateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NameRegistryFilterDto {
    private Long id;
    private String firstName;
    private String lastName;
    private DateRange createdDate;

    @Builder.Default
    private int from = 0;

    @Builder.Default
    private int size = 20;
}
