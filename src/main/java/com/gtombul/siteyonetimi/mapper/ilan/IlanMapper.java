package com.gtombul.siteyonetimi.mapper.ilan;

import com.gtombul.siteyonetimi.dto.ilan.IlanDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanListelemeDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.ilan.Ilan;
import com.gtombul.siteyonetimi.model.ilan.IlanResim;
import com.gtombul.siteyonetimi.storage.ilan.DosyaPort;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IlanMapper extends BaseMapper<Ilan, IlanDto> {

    @Mapping(target = "kategoriId", source = "kategori.id")
    @Mapping(target = "kategoriAd", source = "kategori.ad")
    @Mapping(target = "olusturanKisiId", source = "olusturanKisi.id")
    @Mapping(target = "olusturanKisiAdSoyad",
            expression = "java(ilan.getOlusturanKisi()!=null ? ilan.getOlusturanKisi().getAd() : null)")
    @Mapping(target = "onaylayanKisiId", source = "onaylayanKisi.id")
    @Mapping(target = "resimUrlListesi", expression = "java(resimUrlListesi(ilan.getResimler(), dosyaPort))")
    IlanDto toDto(Ilan ilan, @Context DosyaPort dosyaPort);

    @Mapping(target = "kategoriId", source = "kategori.id")
    @Mapping(target = "kategoriAd", source = "kategori.ad")
    @Mapping(target = "ilkResimUrl", expression = "java(ilkResimUrl(ilan.getResimler(), dosyaPort))")
    IlanListelemeDto toListelemeDto(Ilan ilan, @Context DosyaPort dosyaPort);

    List<IlanListelemeDto> toListelemeDto(List<Ilan> ilanlar, @Context DosyaPort dosyaPort);

    // Helpers
    default String ilkResimUrl(List<IlanResim> resimler, DosyaPort port) {
        if (resimler == null || resimler.isEmpty()) return null;
        var r = resimler.stream()
                .sorted(Comparator.comparing(IlanResim::getSiraNo, Comparator.nullsLast(Integer::compareTo)))
                .findFirst().orElse(null);
        return r == null ? null : port.publicUrl(r.getKaynakKayitId());
    }

    default List<String> resimUrlListesi(List<IlanResim> resimler, DosyaPort port) {
        if (resimler == null || resimler.isEmpty()) return java.util.List.of();
        return resimler.stream()
                .sorted(Comparator.comparing(IlanResim::getSiraNo, Comparator.nullsLast(Integer::compareTo)))
                .map(r -> port.publicUrl(r.getKaynakKayitId()))
                .toList();
    }

}
