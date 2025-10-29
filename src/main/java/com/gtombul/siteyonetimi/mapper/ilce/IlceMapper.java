package com.gtombul.siteyonetimi.mapper.ilce;

import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.adres.Ilce;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IlceMapper extends BaseMapper<Ilce, IlceDto> {

    @Override
    @Mapping(source = "il.id", target = "ilId")
    @Mapping(source = "il.ad", target = "ilAd")
    @Mapping(source = "il.plakaKodu", target = "ilPlakaKodu")
    @Mapping(source = "il.telefonKodu", target = "ilTelefonKodu")
    IlceDto toDto(Ilce entity);

    @Override
    @Mapping(source = "ilId", target = "il.id")
    Ilce toEntity(IlceDto ilceDto);

}
