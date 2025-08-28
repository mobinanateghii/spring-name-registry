package ir.example.demo.service;

import ir.example.demo.dto.RegisterNameDto;
import ir.example.demo.dto.filter.NameRegistryFilterDto;
import ir.example.demo.exception.RegistryNameNotFoundException;
import ir.example.demo.mapper.NameRegistryMapper;
import ir.example.demo.model.NameRegistry;
import ir.example.demo.model.NameRegistry_;
import ir.example.demo.repository.NameRegistryRepo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class NameRegistryService {
    private final NameRegistryRepo nameRegistryRepo;
    private final NameRegistryMapper nameRegistryMapper;

    /**
     * Add list of names to repository
     *
     * @param nameDtoList list of RegisterNameDto
     * @return saved list of NameRegistry entities
     */
    public List<NameRegistry> addNames(List<RegisterNameDto> nameDtoList) {
        return nameRegistryRepo.saveAll(nameRegistryMapper.toEntityList(nameDtoList));
    }

    /**
     * Add name to repository
     *
     * @param nameDto the list of RegisterNameDto to save
     * @return saved NameRegistry entity
     */
    public NameRegistry addName(RegisterNameDto nameDto) {
        return nameRegistryRepo.save(nameRegistryMapper.toEntity(nameDto));
    }

    /**
     * Update existing NameRegistry record
     *
     * @param id      the id of the NameRegistry
     * @param nameDto the new value of NameRegistry
     * @return the updated NameRegistry entity
     * @throws NoSuchElementException if no entity was found by this id
     */
    public NameRegistry updateName(Long id, RegisterNameDto nameDto) {
        NameRegistry nameRegistry = nameRegistryRepo.findById(id)
                .orElseThrow(() -> new RegistryNameNotFoundException(String.format("Request not found by id: %s", id)));
        nameRegistry.setFirstName(nameDto.getFirstName());
        nameRegistry.setLastName(nameDto.getLastName());

        return nameRegistryRepo.save(nameRegistry);
    }

    /**
     * Remove existing NameRegistry record
     *
     * @param id the id of the NameRegistry
     * @throws NoSuchElementException if no entity was found by this id
     */
    public void removeName(Long id) {
        NameRegistry nameRegistry = nameRegistryRepo.findById(id)
                .orElseThrow(() -> new RegistryNameNotFoundException(String.format("Request not found by id: %s", id)));
        nameRegistry.setThruDate(LocalDateTime.now());

        nameRegistryRepo.save(nameRegistry);
    }

    /**
     * get existing NameRegistry record
     *
     * @param id the id of the NameRegistry
     * @throws NoSuchElementException if no entity was found by this id
     */
    public NameRegistry getName(Long id) {
        return nameRegistryRepo.findById(id)
                .orElseThrow(() -> new RegistryNameNotFoundException(String.format("Request not found by id: %s", id)));
    }

    /**
     * Retrieves paginated list of NameRegistry entities
     *
     * @param filterDto the filter dto
     * @return the page of NameRegistry entities matching the filters
     */
    public Page<NameRegistry> getPaginatedNames(NameRegistryFilterDto filterDto) {
        return nameRegistryRepo.findAll(
                Specification.allOf(this.getNameRegistrySpec(filterDto)),
                PageRequest.of(filterDto.getFrom() / filterDto.getSize(), filterDto.getSize(), Sort.by(Sort.Direction.DESC, NameRegistry_.CREATED_DATE))
        );
    }

    /**
     * Retrieves list of NameRegistry entities
     *
     * @param filterDto the filter dto
     * @return the list of NameRegistry entities matching the filters
     */
    public List<NameRegistry> getNames(NameRegistryFilterDto filterDto) {
        return nameRegistryRepo.findAll(Specification.allOf(this.getNameRegistrySpec(filterDto)));
    }

    public List<Specification<NameRegistry>> getNameRegistrySpec(NameRegistryFilterDto filterDto) {
        List<Specification<NameRegistry>> specifications = new ArrayList<>();
        specifications.add((root, query, cb) -> cb.isNull(root.get(NameRegistry_.THRU_DATE)));

        if (Objects.nonNull(filterDto.getId()))
            specifications.add((root, query, cb) -> (cb.equal(root.get(NameRegistry_.ID), filterDto.getId())));

        if (StringUtils.isNotEmpty(filterDto.getFirstName()))
            specifications.add((root, query, cb) -> cb.like(root.get(NameRegistry_.FIRST_NAME), '%' + filterDto.getFirstName() + '%'));

        if (StringUtils.isNotEmpty(filterDto.getLastName()))
            specifications.add((root, query, cb) -> cb.like(root.get(NameRegistry_.LAST_NAME), '%' + filterDto.getLastName() + '%'));

        if (Objects.nonNull(filterDto.getCreatedDate())) {
            if (Objects.nonNull(filterDto.getCreatedDate().getFrom()))
                specifications.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(NameRegistry_.CREATED_DATE), filterDto.getCreatedDate().getFrom()));

            if (Objects.nonNull(filterDto.getCreatedDate().getTo()))
                specifications.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get(NameRegistry_.CREATED_DATE), filterDto.getCreatedDate().getTo()));
        }

        return specifications;
    }
}
