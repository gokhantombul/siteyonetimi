package com.gtombul.siteyonetimi.repository.dosya;

import com.gtombul.siteyonetimi.model.dosya.Dosya;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DosyaRepository extends BaseRepository<Dosya, Long> {

    Optional<Dosya> findByBenzersizAdi(String benzersizAdi);

}