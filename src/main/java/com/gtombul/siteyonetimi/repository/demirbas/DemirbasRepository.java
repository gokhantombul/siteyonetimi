package com.gtombul.siteyonetimi.repository.demirbas;

import com.gtombul.siteyonetimi.model.demirbas.Demirbas;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;

public interface DemirbasRepository extends BaseRepository<Demirbas, Long> {

    boolean existsByAdIgnoreCase(String ad); // servis katmanında soft-delete’e göre kontrol et

}
