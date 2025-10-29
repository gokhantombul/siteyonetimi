package com.gtombul.siteyonetimi.repository.il;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.adres.Il;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.Optional;

public interface IlRepository extends BaseRepository<Il, Long> {

    Optional<Il> findByPlakaKoduAndDurum(Integer plakaKodu, Durum durum);

}
