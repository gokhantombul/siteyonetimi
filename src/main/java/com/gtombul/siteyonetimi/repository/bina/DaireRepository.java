package com.gtombul.siteyonetimi.repository.bina;

import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface DaireRepository extends BaseRepository<Daire, Long> {

    List<Daire> findByBlokIdAndDurumNotOrderByNoAsc(Long blokId, Durum durum);

    Optional<Daire> findByBlokIdAndNoIgnoreCaseAndDurumNot(Long blokId, String no, Durum durum);

}
