package com.gtombul.siteyonetimi.repository.duyuru;

import com.gtombul.siteyonetimi.model.duyuru.Duyuru;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.time.LocalDate;
import java.util.List;

public interface DuyuruRepository extends BaseRepository<Duyuru, Long> {

    List<Duyuru> findAllByGecerlilikTarihiAfterAndDurum(LocalDate tarih, Durum durum);

    List<Duyuru> findAllByDurumAndGecerlilikTarihiBefore(Durum durum, LocalDate tarih);

}
