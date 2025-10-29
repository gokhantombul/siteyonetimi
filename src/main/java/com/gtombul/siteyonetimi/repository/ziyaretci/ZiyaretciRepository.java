package com.gtombul.siteyonetimi.repository.ziyaretci;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.ZiyaretciDurum;
import com.gtombul.siteyonetimi.model.ziyaretci.Ziyaretci;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZiyaretciRepository extends BaseRepository<Ziyaretci, Long> {

    Page<Ziyaretci> findByZiyaretciDurumAndDurumNot(ZiyaretciDurum ziyaretciDurum, Durum durum, Pageable pageable);

    Page<Ziyaretci> findByDaire_IdAndDurumNot(Long daireId, Durum durum, Pageable pageable);

    Page<Ziyaretci> findByAdContainingIgnoreCaseOrSoyadContainingIgnoreCaseAndDurumNot(
            String ad, String soyad, Durum durum, Pageable pageable);

}
