package com.gtombul.siteyonetimi.mapper.bildirim;

import com.gtombul.siteyonetimi.dto.bildirim.BildirimGonderRequestDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bildirim.Bildirim;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BildirimMapper extends BaseMapper<Bildirim, BildirimGonderRequestDto> {

}

