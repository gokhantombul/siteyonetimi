package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.DaireDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.Daire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DaireMapper extends BaseMapper<Daire, DaireDto> {

    @Override
    @Mappings({
            @Mapping(target = "katId", source = "kat.id"),
            @Mapping(target = "blokId", source = "blok.id")
    })
    DaireDto toDto(Daire entity);

    @Override
    @Mappings({
            @Mapping(target = "kat.id", source = "katId"),
            @Mapping(target = "blok.id", source = "blokId")
    })
    Daire toEntity(DaireDto dto);

}
