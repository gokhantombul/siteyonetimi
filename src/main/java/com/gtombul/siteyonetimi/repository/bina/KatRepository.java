package com.gtombul.siteyonetimi.repository.bina;

import com.gtombul.siteyonetimi.model.bina.Kat;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;

public interface KatRepository extends BaseRepository<Kat, Long> {

    List<Kat> findByBlokIdAndDurumNotOrderBySiraNoAsc(Long blokId, Durum durum);

}