package com.gtombul.siteyonetimi.mapper.personel;

import com.gtombul.siteyonetimi.dto.personel.PersonelDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import com.gtombul.siteyonetimi.model.personel.Personel;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PersonelMapper extends BaseMapper<Personel, PersonelDto> {

    @Override
    @Mapping(source = "kisi.id", target = "kisiId")
    @Mapping(source = "kisi.ad", target = "kisiAdSoyad", qualifiedByName = "kisiAdSoyad")
    @Mapping(target = "calisiyor", expression = "java(entity.isCalisiyor())")
    PersonelDto toDto(Personel entity);

    @Override
    @Mapping(source = "kisiId", target = "kisi", qualifiedByName = "kisiFromId")
    Personel toEntity(PersonelDto dto);

    @Named("kisiFromId")
    default Kisi kisiFromId(Long kisiId) {
        if (kisiId == null) return null;
        Kisi k = new Kisi();
        k.setId(kisiId);
        return k;
    }

    @Named("kisiAdSoyad")
    default String kisiAdSoyad(String ad) {
        // İhtiyaca göre soyad vs. birleşimi burada yapılabilir; şimdilik sadece ad.
        return ad;
    }

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "kisiId", target = "kisi", qualifiedByName = "kisiFromId")
    void updateEntityFromDto(PersonelDto dto, @MappingTarget Personel entity);

}
