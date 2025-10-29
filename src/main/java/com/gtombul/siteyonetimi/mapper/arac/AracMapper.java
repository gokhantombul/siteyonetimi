package com.gtombul.siteyonetimi.mapper.arac;

import com.gtombul.siteyonetimi.dto.arac.AracDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.arac.Arac;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AracMapper extends BaseMapper<Arac, AracDto> {

    @Override
    @Mapping(target = "kisiId", source = "kisi.id")
    AracDto toDto(Arac entity);

    @Override
    @Mapping(target = "kisi.id", source = "kisiId")
    Arac toEntity(AracDto dto);

}
