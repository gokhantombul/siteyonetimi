package com.gtombul.siteyonetimi.mapper.base;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface BaseMapper<E, DTO> {

    E toEntity(DTO dto);

    DTO toDto(E entity);

    List<E> toEntity(List<DTO> dto);

    List<DTO> toDto(List<E> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DTO dto, @MappingTarget E entity);

    //String jsonAsString(Object obj);
}
