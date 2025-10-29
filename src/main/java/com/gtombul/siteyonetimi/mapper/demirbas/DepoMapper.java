package com.gtombul.siteyonetimi.mapper.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.DepoDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.demirbas.Depo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepoMapper extends BaseMapper<Depo, DepoDto> {

}
