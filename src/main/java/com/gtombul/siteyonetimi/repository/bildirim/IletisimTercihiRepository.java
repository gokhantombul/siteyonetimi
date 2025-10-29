package com.gtombul.siteyonetimi.repository.bildirim;

import com.gtombul.siteyonetimi.model.bildirim.IletisimTercihi;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.Optional;

public interface IletisimTercihiRepository extends BaseRepository<IletisimTercihi, Long> {

    Optional<IletisimTercihi> findByKisiId(Long kisiId);

}
