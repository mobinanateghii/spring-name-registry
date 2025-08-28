package ir.example.demo.service;

import ir.example.demo.dto.CustomerNameDetailsDto;
import ir.example.demo.dto.filter.NameRegistryFilterDto;
import ir.example.demo.exception.DuplicateCustomerException;
import ir.example.demo.model.NameRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final Long CUSTOMERS_COUNT = 1_000_000L;
    private final NameRegistryService nameRegistryService;
    private static Set<String> customerNames;

    /**
     * NOTE: Initialize RandomCustomerNames
     * Originally, this was intended to be executed in {@code @PostConstruct} method. However, when using
     * H2 database, {@code @PostConstruct} runs before data from {@code data.sql}
     * This means the NameRegistry table will be empty!
     */
    public void initRandomCustomerNames(){
        if(Objects.isNull(customerNames) || customerNames.isEmpty()){
            List<NameRegistry> nameRegistries = nameRegistryService.getNames(new NameRegistryFilterDto());
            RandomNameGenerator randomNameGenerator = new RandomNameGenerator(nameRegistries);
            customerNames = randomNameGenerator.generate(CUSTOMERS_COUNT);
        }
    }

    /**
     * Add new customer name to set of customer names.
     * Before adding, it ensures that the random customer names are initialized by calling
     * {@code initRandomCustomerNames()}.
     *
     * @param customerName the name of the customer
     * @throws DuplicateCustomerException if the customer name already exists in the set
     */
    public void addName(String customerName) {
        this.initRandomCustomerNames();

        boolean isUniq = customerNames.add(customerName);
        if (!isUniq)
            throw new DuplicateCustomerException(String.format("Customer by name : %s ,is already exist!", customerName));
    }

    public List<CustomerNameDetailsDto> getNamesDetail() {
        return Collections.emptyList();
    }
}
