package ir.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "NAME_CATALOG")
@Data
public class NameCatalog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NAME_CATALOG_SEQ")
    @SequenceGenerator(name = "NAME_CATALOG_SEQ", sequenceName = "NAME_CATALOG_SEQ", allocationSize = 1, initialValue = 1000)
    private Long id;

    private String firstName;

    private String lastName;
}
