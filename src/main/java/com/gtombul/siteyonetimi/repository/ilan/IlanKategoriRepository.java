package com.gtombul.siteyonetimi.repository.ilan;

import com.gtombul.siteyonetimi.model.ilan.IlanKategori;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IlanKategoriRepository extends BaseRepository<IlanKategori, Long> {

    Optional<IlanKategori> findByAdIgnoreCase(String ad);

}
