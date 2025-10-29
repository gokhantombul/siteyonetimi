package com.gtombul.siteyonetimi.repository.anket;

import com.gtombul.siteyonetimi.model.anket.AnketOy;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnketOyRepository extends BaseRepository<AnketOy, Long> {

    boolean existsByAnket_IdAndKisi_IdAndDurumNot(Long anketId, Long kisiId, Durum durum);

    @Query("select count(o) from AnketOy o where o.anket.id = :anketId and o.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI")
    long sayToplamOy(Long anketId);

    @Query("select count(o) from AnketOy o where o.anket.id = :anketId and o.kararOyu = com.gtombul.siteyonetimi.model.anket.KararOyu.EVET and o.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI")
    long sayEvet(Long anketId);

    @Query("select count(o) from AnketOy o where o.anket.id = :anketId and o.kararOyu = com.gtombul.siteyonetimi.model.anket.KararOyu.HAYIR and o.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI")
    long sayHayir(Long anketId);

    @Query("select count(o) from AnketOy o where o.anket.id = :anketId and o.kararOyu = com.gtombul.siteyonetimi.model.anket.KararOyu.CEKIMSER and o.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI")
    long sayCekimser(Long anketId);

    @Query("""
            select o.secenek.id, count(o.id) 
            from AnketOy o 
            where o.anket.id = :anketId 
              and o.secenek.id is not null 
              and o.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI 
            group by o.secenek.id
            """)
    List<Object[]> saySecenekBazli(Long anketId);

}
