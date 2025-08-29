package ir.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.example.demo.dto.CustomerProcessedNameDto;
import ir.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/names/processed/sequential")
    @Operation(
            summary = "Get processed customer names sequentially",
            description = "This endpoint is used only for testing purposes." +
                    " Do not use it in Swagger UI; call it directly from test cases as it may return a large amount of data."
    )
    public ResponseEntity<List<CustomerProcessedNameDto>> getSequentialProcessCustomerNames() {
        return ResponseEntity.ok(customerService.sequentialProcessCustomerNames());
    }

    @GetMapping("/names/processed/parallel")
    @Operation(
            summary = "Get processed customer names in parallel",
            description = "This endpoint is used only for testing purposes." +
                    " Do not use it in Swagger UI; call it directly from test cases as it may return a large amount of data."
    )
    public ResponseEntity<List<CustomerProcessedNameDto>> getParallelProcessCustomerNames() {
        return ResponseEntity.ok(customerService.parallelProcessCustomerNames());
    }

    @GetMapping("/names/processed/explicit-parallel")
    @Operation(
            summary = "Get processed customer names with explicit parallel execution",
            description = "This endpoint is used only for testing purposes." +
                    " Do not use it in Swagger UI; call it directly from test cases as it may return a large amount of data."
    )
    public ResponseEntity<List<CustomerProcessedNameDto>> getExplicitParallelProcessCustomerNames() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(customerService.explicitParallelProcessCustomerNames());
    }

    @PostMapping("/name/{name}")
    @Operation(
            summary = "Get customer names",
            description = "This endpoint is used only for testing purposes." +
                    " Do not use it in Swagger UI; call it directly from test cases as it may return a large amount of data."
    )
    public ResponseEntity<String> addCustomerName(@PathVariable String name) {
        customerService.addCustomerName(name);
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
