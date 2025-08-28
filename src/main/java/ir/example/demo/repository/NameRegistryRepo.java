package ir.example.demo.repository;

import ir.example.demo.model.NameRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NameRegistryRepo extends JpaRepository<NameRegistry, Long>, JpaSpecificationExecutor<NameRegistry> {
}
