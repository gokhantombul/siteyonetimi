package com.gtombul.siteyonetimi.mapper.talep;

import com.gtombul.siteyonetimi.dto.talep.GorevDto;
import com.gtombul.siteyonetimi.dto.talep.TalepDetayDto;
import com.gtombul.siteyonetimi.dto.talep.TalepGuncellemeDto;
import com.gtombul.siteyonetimi.dto.talep.TalepListeDto;
import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.model.personel.Personel;
import com.gtombul.siteyonetimi.model.talep.Gorev;
import com.gtombul.siteyonetimi.model.talep.Talep;
import com.gtombul.siteyonetimi.model.talep.TalepGuncelleme;
import com.gtombul.siteyonetimi.model.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TalepMapper {

    // --- TalepDetayDto Eşleştirmesi ---
    @Mapping(source = "olusturanKullanici", target = "olusturanKullaniciAdi", qualifiedByName = "userToAdSoyad")
    @Mapping(source = "daire", target = "daireBilgisi", qualifiedByName = "daireToBilgi")
    @Mapping(source = "kategori.ad", target = "kategoriAdi")
    @Mapping(source = "gorev", target = "gorev") // GorevDto'yu tetikler
    @Mapping(source = "guncellemeler", target = "guncellemeler")
    // TalepGuncellemeDto listesini tetikler
    TalepDetayDto toTalepDetayDto(Talep talep);

    // --- TalepListeDto Eşleştirmesi ---
    @Mapping(source = "olusturanKullanici", target = "olusturanKullaniciAdi", qualifiedByName = "userToAdSoyad")
    @Mapping(source = "daire", target = "daireBilgisi", qualifiedByName = "daireToBilgi")
    @Mapping(source = "kategori.ad", target = "kategoriAdi")
    @Mapping(source = "gorev.atananPersonel", target = "atananPersonelAdi", qualifiedByName = "personelToAdSoyad")
    TalepListeDto toTalepListeDto(Talep talep);

    List<TalepListeDto> toTalepListeDtoList(List<Talep> talepler);

    // Page<Entity> -> Page<DTO> dönüşümü (BaseServiceImpl'de olmayan Page<TalepListeDto> için)
    default Page<TalepListeDto> toTalepListeDtoPage(Page<Talep> page) {
        return page.map(this::toTalepListeDto);
    }

    // --- Görev Eşleştirmesi ---
    @Mapping(source = "atananPersonel", target = "atananPersonelAdi", qualifiedByName = "personelToAdSoyad")
    @Mapping(source = "talep.uuid", target = "talepUuid")
    GorevDto toGorevDto(Gorev gorev);

    // --- Talep Güncelleme Eşleştirmesi ---
    @Mapping(source = "kullanici", target = "kullaniciAdi", qualifiedByName = "userToAdSoyad")
    TalepGuncellemeDto toTalepGuncellemeDto(TalepGuncelleme guncelleme);

    List<TalepGuncellemeDto> toTalepGuncellemeDtoList(List<TalepGuncelleme> guncellemeler);

    // --- Yardımcı Metotlar (Qualified By Name) ---

    @Named("userToAdSoyad")
    default String userToAdSoyad(UserEntity user) {
        if (user == null) return "Sistem";
        // Projenizdeki UserEntity'de 'ad' ve 'soyad' olduğunu varsayıyorum
        // Eğer yoksa (örn: sadece 'kullaniciAdi' varsa) ona göre düzenleyin
        // return user.getAd() + " " + user.getSoyad();
        return user.getUsername(); // Örnek: Projenizdeki UserEntity'ye göre güncelleyin
    }

    @Named("personelToAdSoyad")
    default String personelToAdSoyad(Personel personel) {
        if (personel == null) return "Atanmadı";
        // Projenizdeki Personel entity'sine göre güncelleyin
        return personel.getKisi().getAd() + " " + personel.getKisi().getSoyad();
    }

    @Named("daireToBilgi")
    default String daireToBilgi(Daire daire) {
        if (daire == null) return null;
        // EntityGraph'ta 'daire.blok' çektiğimizden emin olmalıyız (TalepRepository'de ekledik)
        if (daire.getBlok() == null) return "No: " + daire.getNo();
        return daire.getBlok().getAd() + " Blok - No: " + daire.getNo();
    }

}