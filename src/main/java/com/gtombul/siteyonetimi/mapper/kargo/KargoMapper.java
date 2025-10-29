package com.gtombul.siteyonetimi.mapper.kargo;

import com.gtombul.siteyonetimi.dto.kargo.KargoDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoListeDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoOlusturGuncelleDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.kargo.Kargo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KargoMapper extends BaseMapper<Kargo, KargoDto> {

    @Mapping(target = "id", ignore = true)
    Kargo toEntity(KargoOlusturGuncelleDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void guncelle(@MappingTarget Kargo entity, KargoOlusturGuncelleDto dto);

    KargoListeDto toListeDto(Kargo entity);

}
