package com.gtombul.siteyonetimi.repository.ilce;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.adres.Ilce;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;

public interface IlceRepository extends BaseRepository<Ilce, Long> {

    List<Ilce> findByIlIdAndDurum(Long ilId, Durum durum);

}
