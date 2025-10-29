package com.gtombul.siteyonetimi.repository.muhasebe;

import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import com.gtombul.siteyonetimi.model.muhasebe.DovizKuru;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DovizKuruRepository extends BaseRepository<DovizKuru, Long> {

    Optional<DovizKuru> findFirstByKaynakAndHedefAndTarihLessThanEqualOrderByTarihDesc(ParaBirimi kaynak, ParaBirimi hedef, LocalDate tarih);

}
