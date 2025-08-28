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
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    public Set<String> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/additional-data")
    public List<CustomerAdditionalData> getCustomersAdditionalData() {
        return customerService.getCustomersAdditionalData();
    }

    @PostMapping("/name/{name}")
    public ResponseEntity<String> addCustomer(@PathVariable String name) {
        customerService.addCustomer(name);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer added");

//        try {
//            customerService.addCustomer(name);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Name added");
//        } catch (DuplicateCustomerException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("This customer was added before!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went Wrong!!");
//        }
    }
}
