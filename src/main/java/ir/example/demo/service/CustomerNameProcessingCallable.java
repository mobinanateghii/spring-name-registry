package ir.example.demo.service;

import ir.example.demo.dto.CustomerProcessedNameDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class CustomerNameProcessingCallable implements Callable<List<CustomerProcessedNameDto>> {
    private final List<String> names;

    public CustomerNameProcessingCallable(List<String> names) {
        this.names = names;
    }

    @Override
    public List<CustomerProcessedNameDto> call() {
        return names.stream().map(name -> {
            return CustomerProcessedNameDto.builder()
                    .name(name)
                    .nameLength(name.length())
                    .reversedName(StringUtils.reverse(name))
                    .build();
        }).collect(Collectors.toList());
    }
}
