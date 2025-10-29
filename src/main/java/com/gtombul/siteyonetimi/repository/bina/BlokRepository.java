package com.gtombul.siteyonetimi.repository.bina;

import com.gtombul.siteyonetimi.model.bina.Blok;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface BlokRepository extends BaseRepository<Blok, Long> {

    List<Blok> findBySiteIdAndDurumNot(Long siteId, Durum durum);

    Optional<Blok> findBySiteIdAndKodIgnoreCaseAndDurumNot(Long siteId, String kod, Durum durum);

}
