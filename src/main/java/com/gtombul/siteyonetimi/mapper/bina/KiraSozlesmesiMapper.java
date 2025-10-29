package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.KiraSozlesmesiDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.KiraSozlesmesi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KiraSozlesmesiMapper extends BaseMapper<KiraSozlesmesi, KiraSozlesmesiDto> {

    @Override
    @Mappings({
            @Mapping(target = "daireId", source = "daire.id"),
            @Mapping(target = "kiraciId", source = "kiraci.id")
    })
    KiraSozlesmesiDto toDto(KiraSozlesmesi entity);

    @Override
    @Mappings({
            @Mapping(target = "daire.id", source = "daireId"),
            @Mapping(target = "kiraci.id", source = "kiraciId")
    })
    KiraSozlesmesi toEntity(KiraSozlesmesiDto dto);

}