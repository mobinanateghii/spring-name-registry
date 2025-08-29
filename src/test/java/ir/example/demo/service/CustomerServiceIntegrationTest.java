package ir.example.demo.service;

import ir.example.demo.dto.CustomerProcessedNameDto;
import ir.example.demo.exception.DuplicateCustomerException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
public class CustomerServiceIntegrationTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void addCustomerName_NewCustomer_Success() {
        String newName = "mobina mobina ntg";
        assertDoesNotThrow(() -> customerService.addCustomerName(newName));

        assertTrue(customerService.getCustomerNames().contains(newName));
    }

    @Test
    void addCustomerName_DuplicateCustomer_ThrowsException() {
        customerService.addCustomerName("mobina ntg");
        String duplicateName = "mobina ntg";

        assertThrows(DuplicateCustomerException.class, () -> customerService.addCustomerName(duplicateName));
    }


    @Test
    void sequentialProcessCustomerNames_MeasurePerformance() {
        long start = System.currentTimeMillis();
        List<CustomerProcessedNameDto> result = customerService.sequentialProcessCustomerNames();
        long duration = System.currentTimeMillis() - start;
        System.out.println(String.format("sequentialProcessCustomerNames run on : %s ms", duration));

        assertFalse(result.isEmpty());
        assertNotNull(result.get(0).getReversedName());
    }

    @Test
    void parallelProcessCustomerNames_MeasurePerformance() {
        long start = System.currentTimeMillis();
        List<CustomerProcessedNameDto> result = customerService.parallelProcessCustomerNames();
        long duration = System.currentTimeMillis() - start;
        System.out.println(String.format("parallelProcessCustomerNames run on : %s ms", duration));

        assertFalse(result.isEmpty());
        assertNotNull(result.get(0).getReversedName());
    }

    @Test
    void explicitParallelProcessCustomerNames_MeasurePerformance() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<CustomerProcessedNameDto> result = customerService.explicitParallelProcessCustomerNames();
        long duration = System.currentTimeMillis() - start;
        System.out.println(String.format("explicitParallelProcessCustomerNames run on : %s ms", duration));

        assertFalse(result.isEmpty());
        assertNotNull(result.get(0).getReversedName());
    }
}
