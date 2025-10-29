package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.MulkSahipligiDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.MulkSahipligi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MulkSahipligiMapper extends BaseMapper<MulkSahipligi, MulkSahipligiDto> {

    @Override
    @Mappings({
            @Mapping(target = "daireId", source = "daire.id"),
            @Mapping(target = "kisiId", source = "kisi.id")
    })
    MulkSahipligiDto toDto(MulkSahipligi entity);

    @Override
    @Mappings({
            @Mapping(target = "daire.id", source = "daireId"),
            @Mapping(target = "kisi.id", source = "kisiId")
    })
    MulkSahipligi toEntity(MulkSahipligiDto dto);

}
