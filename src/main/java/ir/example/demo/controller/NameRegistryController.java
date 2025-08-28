package ir.example.demo.controller;

import ir.example.demo.dto.CustomerAdditionalData;
import ir.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/name_catalog")
@AllArgsConstructor
public class NameCatalogController {
    private final CustomerService customerService;

    @GetMapping("/list")
    public Set<String> getNameCatalogs() {
        return customerService.getCustomers();
    }

    @GetMapping("/additional-data")
    public List<CustomerAdditionalData> getCustomersAdditionalData() {
        return customerService.getCustomersAdditionalData();
    }


}
