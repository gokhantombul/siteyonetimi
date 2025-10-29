package com.gtombul.siteyonetimi.repository.arac;

import com.gtombul.siteyonetimi.model.arac.Arac;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface AracRepository extends BaseRepository<Arac, Long> {

    Optional<Arac> findByPlakaIgnoreCaseAndDurumNot(String plaka, Durum durum);

    List<Arac> findByKisiIdAndDurumNot(Long kisiId, Durum durum);

}
