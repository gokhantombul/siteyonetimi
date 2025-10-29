package com.gtombul.siteyonetimi.mapper.insan;

import com.gtombul.siteyonetimi.dto.insan.KisiDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KisiMapper extends BaseMapper<Kisi, KisiDto> {

    @Override
    @Mappings({
            @Mapping(target = "eposta", source = "iletisim.eposta"),
            @Mapping(target = "telefon", source = "iletisim.telefon")
    })
    KisiDto toDto(Kisi entity);

    @Override
    @InheritInverseConfiguration
    Kisi toEntity(KisiDto dto);

}
