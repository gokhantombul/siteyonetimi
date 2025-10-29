package com.gtombul.siteyonetimi.repository.bina;

import com.gtombul.siteyonetimi.model.bina.Site;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;

public interface SiteRepository extends BaseRepository<Site, Long> {

    List<Site> findByAdContainingIgnoreCaseAndDurumNot(String ad, Durum durum);

}