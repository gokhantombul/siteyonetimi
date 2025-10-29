package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.FirmaDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.mapper.ilce.IlceMapper;
import com.gtombul.siteyonetimi.model.bina.Firma;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {IlceMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FirmaMapper extends BaseMapper<Firma, FirmaDto> {

}
