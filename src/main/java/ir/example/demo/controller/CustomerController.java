package ir.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.example.demo.dto.CustomerNameDetailsDto;
import ir.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/names/detail")
    @Operation(
            summary = "Get customer names details",
            description = "This endpoint is used only for testing purposes." +
                    " Do not use it in Swagger UI; call it directly from test cases as it may return a large amount of data."
    )
    public List<CustomerNameDetailsDto> getCustomersNameDetail() {
        return customerService.getNamesDetail();
    }

    @PostMapping("/name/{name}")
    @Operation(
            summary = "Get customer names",
            description = "This endpoint is used only for testing purposes." +
                    " Do not use it in Swagger UI; call it directly from test cases as it may return a large amount of data."
    )
    public ResponseEntity<String> addCustomerName(@PathVariable String name) {
        customerService.addName(name);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer added successfully!");

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
