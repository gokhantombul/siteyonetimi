package com.gtombul.siteyonetimi.repository.bina;

import com.gtombul.siteyonetimi.model.bina.DaireOturum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DaireOturumRepository extends BaseRepository<DaireOturum, Long> {

    @Query("select o from DaireOturum o where o.daire.id = :daireId and o.durum <> 'SILINDI' and (o.bitisTarihi is null or o.bitisTarihi >= :tarih) and (o.baslangicTarihi is null or o.baslangicTarihi <= :tarih)")
    List<DaireOturum> findAktifOturumlar(Long daireId, LocalDate tarih);

    @Query("select o from DaireOturum o where o.kisi.id = :kisiId and o.durum <> 'SILINDI' and (o.bitisTarihi is null or o.bitisTarihi >= :tarih) and (o.baslangicTarihi is null or o.baslangicTarihi <= :tarih)")
    List<DaireOturum> findKisininAktifOturumlari(Long kisiId, LocalDate tarih);

}
