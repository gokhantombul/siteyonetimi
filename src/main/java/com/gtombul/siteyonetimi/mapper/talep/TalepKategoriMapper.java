package com.gtombul.siteyonetimi.mapper.talep;

import com.gtombul.siteyonetimi.dto.talep.TalepKategoriDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.talep.TalepKategori;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TalepKategoriMapper extends BaseMapper<TalepKategori, TalepKategoriDto> { // <-- BaseMapper'dan miras

    @Override
    @Mapping(target = "talepler", ignore = true) // DTO'dan Entity'ye dönerken listeyi görmezden gel
    TalepKategori toEntity(TalepKategoriDto dto);

    @Override
    TalepKategoriDto toDto(TalepKategori entity);

    // updateEntityFromDto, toEntity(List), toDto(List) metotları BaseMapper'dan gelecek.
}