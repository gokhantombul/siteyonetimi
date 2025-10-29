package com.gtombul.siteyonetimi.mapper.ilan;

import com.gtombul.siteyonetimi.dto.ilan.IlanKategoriDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.ilan.IlanKategori;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IlanKategoriMapper extends BaseMapper<IlanKategori, IlanKategoriDto> {

}
