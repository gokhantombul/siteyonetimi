package com.gtombul.siteyonetimi.mapper.il;

import com.gtombul.siteyonetimi.dto.adres.il.IlDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.adres.Il;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IlMapper extends BaseMapper<Il, IlDto> {

}
