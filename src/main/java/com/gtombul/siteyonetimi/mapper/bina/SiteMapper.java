package com.gtombul.siteyonetimi.mapper.bina;

import com.gtombul.siteyonetimi.dto.bina.SiteDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.bina.Site;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteMapper extends BaseMapper<Site, SiteDto> {

    @Override
    @Mappings({
            @Mapping(target = "adresSatir1", source = "adres.adresSatir1"),
            @Mapping(target = "adresSatir2", source = "adres.adresSatir2"),
            @Mapping(target = "mahalle", source = "adres.mahalle"),
            @Mapping(target = "postaKodu", source = "adres.postaKodu"),
            @Mapping(target = "ilceAd", source = "adres.ilceAd"),
            @Mapping(target = "ilAd", source = "adres.ilAd")
    })
    SiteDto toDto(Site entity);

    @Override
    @InheritInverseConfiguration
    Site toEntity(SiteDto dto);

}
