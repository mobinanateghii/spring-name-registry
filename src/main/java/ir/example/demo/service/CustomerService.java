package ir.example.demo.service;

import ir.example.demo.dto.CustomerAdditionalData;
import ir.example.demo.exception.DuplicateCustomerException;
import ir.example.demo.model.NameCatalog;
import ir.example.demo.repository.NameCatalogRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CustomerService {
    private static final Long CUSTOMERS_COUNT = 1_000_000L;

    private final NameCatalogRepo nameCatalogRepo;


    public List<NameCatalog> getNameCatalogs() {
        return nameCatalogRepo.findAll();
    }

    public Set<String> getCustomers(){
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(getNameCatalogs());
        return randomNameGenerator.generate(CUSTOMERS_COUNT);
    }

    public void addCustomer(String customerName){
        Set<String> customers = getCustomers();
        boolean isUniq = customers.add(customerName);
        if(!isUniq)
            throw new DuplicateCustomerException(String.format("customer by name : %s ,is already exist!", customerName));
    }

    public List<CustomerAdditionalData> getCustomersAdditionalData() {
        Set<String> customers = getCustomers();
    }


}
