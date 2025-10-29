package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.BlokDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.Blok;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BlokMapper extends BaseMapper<Blok, BlokDto>{

    @Override
    @Mapping(target = "siteId", source = "site.id")
    BlokDto toDto(Blok entity);

    @Override
    @Mapping(target = "site.id", source = "siteId")
    Blok toEntity(BlokDto dto);

}
