package com.gtombul.siteyonetimi.repository.insan;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;

public interface KisiRepository extends BaseRepository<Kisi, Long> {

    List<Kisi> findByAdContainingIgnoreCaseOrSoyadContainingIgnoreCaseAndDurumNot(String ad, String soyad, Durum durum);

}
