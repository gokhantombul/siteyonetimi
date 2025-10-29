package com.gtombul.siteyonetimi.mapper.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.HesapDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.muhasebe.Hesap;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HesapMapper extends BaseMapper<Hesap, HesapDto> {

}
