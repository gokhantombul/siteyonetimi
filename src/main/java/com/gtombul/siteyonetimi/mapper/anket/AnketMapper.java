package com.gtombul.siteyonetimi.mapper.anket;

import com.gtombul.siteyonetimi.dto.anket.AnketDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.anket.Anket;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnketMapper extends BaseMapper<Anket, AnketDto> {

    @Override
    @Mapping(source = "tur", target = "tur")
    AnketDto toDto(Anket entity);

    @Override
    @InheritInverseConfiguration
    Anket toEntity(AnketDto dto);

}
