package ir.example.demo.service;

import ir.example.demo.dto.CustomerProcessedNameDto;
import ir.example.demo.dto.filter.NameRegistryFilterDto;
import ir.example.demo.exception.DuplicateCustomerException;
import ir.example.demo.model.NameRegistry;
import ir.example.demo.service.generator.RandomNameGenerator;
import ir.example.demo.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final Long GENERATED_CUSTOMERS_COUNT = 1_000_000L;
    private static final int CUSTOMER_PROCESS_THREAD_COUNT = 10;

    private final NameRegistryService nameRegistryService;
    private static Set<String> customerNames;

    /**
     * NOTE: Initialize RandomCustomerNames
     * We use RandomNameGenerator based on MAX_SEQUENTIAL_COUNT, so it will be sequential or parallel mechanism
     * <p>
     * Originally, this was intended to be executed in {@code @PostConstruct} method. However, when using
     * H2 database, {@code @PostConstruct} runs before data from {@code data.sql}
     * This means the NameRegistry table will be empty!
     */
    private Set<String> getCustomerNames() {
        if (Objects.isNull(customerNames) || customerNames.isEmpty()) {
            List<NameRegistry> nameRegistries = nameRegistryService.getNames(new NameRegistryFilterDto());
            RandomNameGenerator randomNameGenerator = new RandomNameGenerator(nameRegistries);
            customerNames = randomNameGenerator.generate(GENERATED_CUSTOMERS_COUNT);
        }

        return customerNames;
    }

    /**
     * Add new customer name to set of customer names.
     * Before adding, it ensures that the random customer names are initialized {@code getCustomerNames()}.
     *
     * @param name the name of the customer
     * @throws DuplicateCustomerException if the customer name already exists in the set
     */
    public void addCustomerName(String name) {
        boolean isUniq = getCustomerNames().add(name);
        if (!isUniq)
            throw new DuplicateCustomerException(String.format("Customer by name : %s ,is already exist!", name));
    }


    //region ProcessedCustomerNames

    /**
     * Processes all customer names in sequential mode and returns a list of processed dto.
     *
     * @return processed list of CustomerProcessedNameDto
     */
    public List<CustomerProcessedNameDto> sequentialProcessCustomerNames() {
        List<CustomerProcessedNameDto> processedNameList = new ArrayList<>();

        for (String name : getCustomerNames()) {
            CustomerProcessedNameDto processedName = CustomerProcessedNameDto.builder()
                    .name(name)
                    .nameLength(name.length())
                    .reversedName(StringUtils.reverse(name))
                    .build();
            processedNameList.add(processedName);
        }
        return processedNameList;
    }

    /**
     * Processes all customer names in parallel stream mode and returns a list of processed dto.
     * On this method spring will divide data list into multiple chunks based on prefer performance and number of available CPU cores
     *
     * @return processed list of CustomerProcessedNameDto
     */
    public List<CustomerProcessedNameDto> parallelProcessCustomerNames() {
        return getCustomerNames()
                .parallelStream()
                .map(name -> CustomerProcessedNameDto.builder()
                        .name(name)
                        .nameLength(name.length())
                        .reversedName(StringUtils.reverse(name))
                        .build())
                .collect(Collectors.toList());
    }


    /**
     * Processes all customer names in explicit Parallel stream mode and returns a list of processed dto.
     * On this method we manually divide data list into multiple chunks and use threadPoolExecutor for parallel processing
     *
     * @return processed list of CustomerProcessedNameDto
     */
    public List<CustomerProcessedNameDto> explicitParallelProcessCustomerNames() throws ExecutionException, InterruptedException {
        List<CustomerProcessedNameDto> results = new ArrayList<>();
        Set<String> customerNames = getCustomerNames();

        int chunkSize = (int) Math.ceil((double) customerNames.size() / CUSTOMER_PROCESS_THREAD_COUNT);
        List<List<String>> chunks = CollectionUtils.doChunk(customerNames, chunkSize);

        ExecutorService executor = Executors.newFixedThreadPool(CUSTOMER_PROCESS_THREAD_COUNT);
        try {
            List<Future<List<CustomerProcessedNameDto>>> futures = chunks.stream()
                    .map(chunk -> executor.submit(new CustomerNameProcessingCallable(chunk)))
                    .collect(Collectors.toList());

            for (Future<List<CustomerProcessedNameDto>> future : futures) {
                results.addAll(future.get());
            }
        } finally {
            executor.shutdown();
        }

        return results;
    }

    //endregion
}
