package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.DaireOturumDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.DaireOturum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DaireOturumMapper extends BaseMapper<DaireOturum, DaireOturumDto> {

    @Override
    @Mappings({
            @Mapping(target = "daireId", source = "daire.id"),
            @Mapping(target = "kisiId", source = "kisi.id"),
            @Mapping(target = "kiraSozlesmesiId", source = "kiraSozlesmesi.id")
    })
    DaireOturumDto toDto(DaireOturum entity);

    @Override
    @Mappings({
            @Mapping(target = "daire.id", source = "daireId"),
            @Mapping(target = "kisi.id", source = "kisiId"),
            @Mapping(target = "kiraSozlesmesi.id", source = "kiraSozlesmesiId")
    })
    DaireOturum toEntity(DaireOturumDto dto);

}
