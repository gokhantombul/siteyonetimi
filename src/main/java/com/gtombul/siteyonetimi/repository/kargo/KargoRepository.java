package com.gtombul.siteyonetimi.repository.kargo;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.kargo.Kargo;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.Optional;

public interface KargoRepository extends BaseRepository<Kargo, Long> {

    Optional<Kargo> findByTakipNoAndDurumNot(String takipNo, Durum durum);

}
