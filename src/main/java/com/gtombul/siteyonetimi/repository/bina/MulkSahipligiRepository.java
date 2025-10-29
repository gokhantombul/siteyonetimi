package com.gtombul.siteyonetimi.repository.bina;

import com.gtombul.siteyonetimi.model.bina.MulkSahipligi;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MulkSahipligiRepository extends BaseRepository<MulkSahipligi, Long> {

    @Query("select m from MulkSahipligi m where m.daire.id = :daireId and m.durum <> 'SILINDI' and (m.bitisTarihi is null or m.bitisTarihi >= :tarih) and (m.baslangicTarihi is null or m.baslangicTarihi <= :tarih)")
    List<MulkSahipligi> findAktifSahipler(Long daireId, LocalDate tarih);

}
