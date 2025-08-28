package ir.example.demo.mapper;

import ir.example.demo.dto.RegisterNameDto;
import ir.example.demo.model.NameRegistry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NameRegistryMapper {
    RegisterNameDto toDto(NameRegistry entity);

    NameRegistry toEntity(RegisterNameDto dto);

    List<RegisterNameDto> toDtoList(List<NameRegistry> entities);

    List<NameRegistry> toEntityList(List<RegisterNameDto> dtos);
}
