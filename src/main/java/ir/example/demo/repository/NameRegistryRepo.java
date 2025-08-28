package ir.example.demo.repository;

import ir.example.demo.model.NameCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameCatalogRepo extends JpaRepository<NameCatalog, Long> {
}
