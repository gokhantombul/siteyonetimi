package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import com.gtombul.siteyonetimi.model.muhasebe.DovizKuru;
import com.gtombul.siteyonetimi.repository.muhasebe.DovizKuruRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DovizKuruServiceImpl implements DovizKuruService {

    private final DovizKuruRepository dovizKuruRepository;

    @Override
    public BigDecimal kurAl(ParaBirimi kaynak, ParaBirimi hedef, LocalDate tarih) {
        return dovizKuruRepository.findFirstByKaynakAndHedefAndTarihLessThanEqualOrderByTarihDesc(kaynak, hedef, tarih)
                .map(DovizKuru::getOran)
                .orElseThrow(() -> new IllegalStateException("Kur bulunamadı: " + kaynak + "->" + hedef + " @ " + tarih));
    }

}