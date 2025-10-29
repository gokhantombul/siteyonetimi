package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.KatDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.Kat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KatMapper extends BaseMapper<Kat, KatDto> {

    @Override
    @Mapping(target = "blokId", source = "blok.id")
    KatDto toDto(Kat entity);

    @Override
    @Mapping(target = "blok.id", source = "blokId")
    Kat toEntity(KatDto dto);

}
