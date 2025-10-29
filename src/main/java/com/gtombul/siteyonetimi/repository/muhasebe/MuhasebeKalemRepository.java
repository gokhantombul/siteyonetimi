package com.gtombul.siteyonetimi.repository.muhasebe;


import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.muhasebe.MuhasebeKalem;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MuhasebeKalemRepository extends BaseRepository<MuhasebeKalem, Long> {

    boolean existsByHesapIdAndDurumNot(Long hesapId, Durum durum);

    boolean existsByFisIdAndDurumNot(Long fisId, Durum durum);

    @Query("""
                select coalesce(sum(coalesce(k.borcTutar,0) - coalesce(k.alacakTutar,0)),0)
                  from MuhasebeKalem k
                 where k.hesap.id = :hesapId
                   and k.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
                   and k.fis.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
            """)
    BigDecimal bakiye(Long hesapId);

    @Query("""
              select coalesce(sum(
                 case when k.borcTutar is not null then coalesce(k.dovizTutar,0)
                      else -coalesce(k.dovizTutar,0) end
              ),0)
              from MuhasebeKalem k
              where k.hesap.id = :hesapId
                and k.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
                and k.fis.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
            """)
    BigDecimal dovizBakiye(Long hesapId);

    @Query("""
                select k from MuhasebeKalem k
                where k.hesap.id = :hesapId
                  and k.fis.tarih between :baslangic and :bitis
                  and k.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
                  and k.fis.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
                order by k.fis.tarih asc, k.id asc
            """)
    List<MuhasebeKalem> ekstre(Long hesapId, LocalDate baslangic, LocalDate bitis);

    @Modifying
    @Query("""
                update MuhasebeKalem k
                   set k.durum = com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
                 where k.fis.id = :fisId
                   and k.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
            """)
    int softDeleteByFisId(Long fisId);

    @Query("""
               select k.id from MuhasebeKalem k
               where k.hesap.id = :bankaHesapId
                 and k.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
                 and k.fis.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
                 and k.mutabakatRef is null
                 and k.fis.tarih between :tarihMin and :tarihMax
                 and (
                       (k.borcTutar is not null and abs(k.borcTutar - :tutar) <= :esik)
                    or (k.alacakTutar is not null and abs(k.alacakTutar - :tutar) <= :esik)
                 )
            """)
    List<Long> adayKalemler(Long bankaHesapId, java.time.LocalDate tarihMin, java.time.LocalDate tarihMax,
                            java.math.BigDecimal tutar, java.math.BigDecimal esik);

    @Modifying
    @Query("""
              update MuhasebeKalem k set k.mutabakatRef = :ref, k.mutabakatTarih = :tarih
              where k.id in :kalemIdleri
            """)
    int mutabakatIsaretle(java.util.List<Long> kalemIdleri, String ref, java.time.LocalDate tarih);

}