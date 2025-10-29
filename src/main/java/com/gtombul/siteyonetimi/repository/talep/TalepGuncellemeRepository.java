package com.gtombul.siteyonetimi.repository.talep;

import com.gtombul.siteyonetimi.model.talep.TalepGuncelleme;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalepGuncellemeRepository extends BaseRepository<TalepGuncelleme, Long> {
    // Genellikle Talep üzerinden erişilir, özel sorguya şimdilik gerek yok.
}