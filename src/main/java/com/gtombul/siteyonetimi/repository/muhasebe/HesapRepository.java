package com.gtombul.siteyonetimi.repository.muhasebe;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.muhasebe.Hesap;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface HesapRepository extends BaseRepository<Hesap, Long> {

    List<Hesap> findAllByHesapTipiAndDurumNot(HesapTipi tip, Durum durum);

    Optional<Hesap> findByHesapTipiAndReferansIdAndDurumNot(HesapTipi tip, Long referansId, Durum durum);

    Optional<Hesap> findByHesapTipiAndReferansId(HesapTipi tip, Long referansId);

}