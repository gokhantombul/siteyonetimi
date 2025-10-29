package com.gtombul.siteyonetimi.repository.bina;

import com.gtombul.siteyonetimi.model.bina.KiraSozlesmesi;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface KiraSozlesmesiRepository extends BaseRepository<KiraSozlesmesi, Long> {

    @Query("select k from KiraSozlesmesi k where k.daire.id = :daireId and k.durum <> 'SILINDI' and (k.bitisTarihi is null or k.bitisTarihi >= :tarih) and (k.baslangicTarihi is null or k.baslangicTarihi <= :tarih)")
    List<KiraSozlesmesi> findAktifSozlesmeler(Long daireId, LocalDate tarih);

}
