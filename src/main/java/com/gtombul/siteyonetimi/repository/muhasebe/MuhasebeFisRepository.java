package com.gtombul.siteyonetimi.repository.muhasebe;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.muhasebe.MuhasebeFis;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.Optional;

public interface MuhasebeFisRepository extends BaseRepository<MuhasebeFis, Long> {

    Optional<MuhasebeFis> findByFisNoAndDurumNot(String fisNo, Durum durum);

    Optional<MuhasebeFis> findByFisNo(String fisNo);

}
