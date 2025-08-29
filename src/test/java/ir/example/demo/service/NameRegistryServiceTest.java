package ir.example.demo.service;

import ir.example.demo.dto.RegisterNameDto;
import ir.example.demo.dto.filter.NameRegistryFilterDto;
import ir.example.demo.exception.RegistryNameNotFoundException;
import ir.example.demo.mapper.NameRegistryMapper;
import ir.example.demo.model.NameRegistry;
import ir.example.demo.repository.NameRegistryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NameRegistryServiceTest {
    @Mock
    private NameRegistryRepo nameRegistryRepo;
    @Mock
    private NameRegistryMapper nameRegistryMapper;

    @InjectMocks
    private NameRegistryService nameRegistryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addName_Success() {
        RegisterNameDto dto = new RegisterNameDto();
        dto.setFirstName("mobina");
        dto.setLastName("ntg");

        NameRegistry entity = new NameRegistry();
        entity.setFirstName("mobina");
        entity.setLastName("ntg");

        when(nameRegistryMapper.toEntity(dto)).thenReturn(entity);
        when(nameRegistryRepo.save(entity)).thenReturn(entity);

        NameRegistry savedEntity = nameRegistryService.addName(dto);

        assertNotNull(savedEntity);
        assertEquals("mobina", savedEntity.getFirstName());
        assertEquals("ntg", savedEntity.getLastName());
    }

    @Test
    void updateName_Success() {
        RegisterNameDto dto = new RegisterNameDto();
        dto.setFirstName("mobinaV2");
        dto.setLastName("ntgV2");

        NameRegistry entity = new NameRegistry();
        entity.setId(1L);
        entity.setFirstName("mobina");
        entity.setLastName("ntg");

        when(nameRegistryRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(nameRegistryRepo.save(entity)).thenReturn(entity);

        NameRegistry updated = nameRegistryService.updateName(1L, dto);

        assertEquals("mobinaV2", updated.getFirstName());
        assertEquals("ntgV2", updated.getLastName());
    }

    @Test
    void getName_Success() {
        NameRegistry entity = new NameRegistry();
        entity.setId(1L);
        entity.setFirstName("mobina");

        when(nameRegistryRepo.findById(1L)).thenReturn(Optional.of(entity));

        NameRegistry result = nameRegistryService.getName(1L);

        assertNotNull(result);
        assertEquals("mobina", result.getFirstName());
    }

    @Test
    void getName_NotFound() {
        when(nameRegistryRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RegistryNameNotFoundException.class, () -> nameRegistryService.getName(1L));
    }

    @Test
    void removeName_Success() {
        NameRegistry entity = new NameRegistry();
        entity.setId(1L);

        when(nameRegistryRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(nameRegistryRepo.save(entity)).thenReturn(entity);

        nameRegistryService.removeName(1L);

        assertNotNull(entity.getThruDate());
    }

    @Test
    void getPaginatedNames_Success() {
        NameRegistryFilterDto filterDto = new NameRegistryFilterDto();
        filterDto.setFrom(0);
        filterDto.setSize(10);

        NameRegistry entity = new NameRegistry();
        entity.setId(1L);
        Page<NameRegistry> page = new PageImpl<>(Collections.singletonList(entity));

        when(nameRegistryRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<NameRegistry> result = nameRegistryService.getPaginatedNames(filterDto);

        assertNotNull(result);
    }

    @Test
    void getNames_Success() {
        NameRegistryFilterDto filterDto = new NameRegistryFilterDto();
        filterDto.setFrom(0);
        filterDto.setSize(10);

        NameRegistry entity = new NameRegistry();
        entity.setId(1L);

        when(nameRegistryRepo.findAll(any(Specification.class))).thenReturn(Collections.singletonList(entity));

        List<NameRegistry> result = nameRegistryService.getNames(filterDto);

        assertNotNull(result);
    }
}
