package com.gtombul.siteyonetimi.mapper.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.DemirbasDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.demirbas.Demirbas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DemirbasMapper extends BaseMapper<Demirbas, DemirbasDto> {

    @Override
    @Mapping(source = "kategori.id", target = "kategoriId")
    @Mapping(source = "kategori.ad", target = "kategoriAd")
    @Mapping(source = "depo.id", target = "depoId")
    @Mapping(source = "depo.ad", target = "depoAd")
    DemirbasDto toDto(Demirbas entity);

    @Override
    @Mapping(target = "kategori.id", source = "kategoriId")
    @Mapping(target = "depo.id", source = "depoId")
    Demirbas toEntity(DemirbasDto dto);

}
