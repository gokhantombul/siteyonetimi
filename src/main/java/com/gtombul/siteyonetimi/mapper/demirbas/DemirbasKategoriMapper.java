package com.gtombul.siteyonetimi.mapper.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.DemirbasKategoriDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.demirbas.DemirbasKategori;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DemirbasKategoriMapper extends BaseMapper<DemirbasKategori, DemirbasKategoriDto> {

}
