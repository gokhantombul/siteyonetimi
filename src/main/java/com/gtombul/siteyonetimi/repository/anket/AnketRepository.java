package com.gtombul.siteyonetimi.repository.anket;

import com.gtombul.siteyonetimi.model.anket.Anket;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnketRepository extends BaseRepository<Anket, Long> {

    @Query("select a from Anket a where a.id = :id and a.durum <> com.gtombul.siteyonetimi.model.enums.Durum.SILINDI")
    Optional<Anket> aktifBulById(Long id);

    boolean existsByBaslikIgnoreCaseAndDurumNot(String baslik, Durum durum);

}
