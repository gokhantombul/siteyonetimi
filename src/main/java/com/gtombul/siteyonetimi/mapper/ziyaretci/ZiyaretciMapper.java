package com.gtombul.siteyonetimi.mapper.ziyaretci;

import com.gtombul.siteyonetimi.dto.ziyaretci.ZiyaretciDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.model.ziyaretci.Ziyaretci;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ZiyaretciMapper extends BaseMapper<Ziyaretci, ZiyaretciDto> {

    @Override
    @Mapping(source = "daire.id", target = "daireId")
    @Mapping(target = "adSoyad", expression = "java(entity.getAdSoyad())")
    ZiyaretciDto toDto(Ziyaretci entity);

    @Override
    @Mapping(target = "daire", ignore = true) // id ile bağlayacağız
    @Mapping(target = "adSoyad", ignore = true)
    Ziyaretci toEntity(ZiyaretciDto dto);

    @AfterMapping
    default void bindDaire(@MappingTarget Ziyaretci entity, ZiyaretciDto dto) {
        if (dto.getDaireId() != null) {
            entity.setDaire(Daire.builder().id(dto.getDaireId()).build());
        } else {
            entity.setDaire(null);
        }
    }

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "daire", ignore = true)
    @Mapping(target = "adSoyad", ignore = true)
    void updateEntityFromDto(ZiyaretciDto dto, @MappingTarget Ziyaretci entity);

    @AfterMapping
    default void updateDaire(@MappingTarget Ziyaretci entity, ZiyaretciDto dto) {
        if (dto.getDaireId() != null) {
            entity.setDaire(Daire.builder().id(dto.getDaireId()).build());
        } else if (dto.getDaireId() == null) {
            entity.setDaire(null);
        }
    }

}
