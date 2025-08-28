package ir.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "NAME_REGISTRY")
@Data
public class NameRegistry extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NAME_REGISTRY_SEQ")
    @SequenceGenerator(name = "NAME_REGISTRY_SEQ", sequenceName = "NAME_REGISTRY_SEQ", allocationSize = 1, initialValue = 1000)
    private Long id;

    private String firstName;

    private String lastName;
}
