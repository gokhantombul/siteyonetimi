package com.gtombul.siteyonetimi.mapper.anket;

import com.gtombul.siteyonetimi.dto.anket.SecenekDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.anket.AnketSecenek;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnketSecenekMapper extends BaseMapper<AnketSecenek, SecenekDto> {

}
