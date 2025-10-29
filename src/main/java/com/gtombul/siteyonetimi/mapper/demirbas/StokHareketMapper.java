package com.gtombul.siteyonetimi.mapper.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.StokHareketDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.demirbas.StokHareket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StokHareketMapper extends BaseMapper<StokHareket, StokHareketDto> {

    @Override
    @Mapping(source = "demirbas.id", target = "demirbasId")
    @Mapping(source = "demirbas.ad", target = "demirbasAd")
    @Mapping(source = "depo.id", target = "depoId")
    @Mapping(source = "depo.ad", target = "depoAd")
    StokHareketDto toDto(StokHareket entity);

    @Override
    @Mapping(target = "demirbas.id", source = "demirbasId")
    @Mapping(target = "depo.id", source = "depoId")
    StokHareket toEntity(StokHareketDto dto);

}
