package com.gtombul.siteyonetimi.repository.bildirim;

import com.gtombul.siteyonetimi.model.bildirim.BildirimSablonu;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

import java.util.Optional;

public interface BildirimSablonuRepository extends BaseRepository<BildirimSablonu, Long> {

    Optional<BildirimSablonu> findByKod(String kod);

}
