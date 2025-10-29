package com.gtombul.siteyonetimi.mapper.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.AidatPlaniDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.muhasebe.AidatPlani;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AidatPlaniMapper extends BaseMapper<AidatPlani, AidatPlaniDto> {

}