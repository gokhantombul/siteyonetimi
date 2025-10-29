package com.gtombul.siteyonetimi.repository.muhasebe;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.muhasebe.AidatAtamaKaydi;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AidatAtamaKaydiRepository extends BaseRepository<AidatAtamaKaydi, Long> {

    Optional<AidatAtamaKaydi> findByDaireHesapIdAndDonemYyyyMmAndDurumNot(Long daireHesapId, String donemYyyyMm, Durum durum);

    boolean existsByPlanIdAndDonemYyyyMmAndDaireHesapIdAndDurumNot(Long planId, String donem, Long daireHesapId, Durum durum);

    @Modifying
    @Query("""
        update AidatAtamaKaydi a
           set a.durum = com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
         where a.plan.id = :planId
           and a.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI
    """)
    int softDeleteByPlanId(Long planId);

}
