package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.BankaHareketGirdi;
import com.gtombul.siteyonetimi.dto.muhasebe.MutabakatRequestDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MutabakatSonucuDto;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeKalemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankaMutabakatServiceImpl implements BankaMutabakatService {

    private final MuhasebeKalemRepository kalemRepo;

    @Override
    @Transactional
    public MutabakatSonucuDto mutabakatYap(MutabakatRequestDto istek) {
        List<String> ok = new ArrayList<>();
        List<String> nok = new ArrayList<>();

        int gun = Optional.ofNullable(istek.getGunEsneklik()).orElse(3);
        BigDecimal esik = Optional.ofNullable(istek.getTutarEsneklik()).orElse(new BigDecimal("0.50"));

        for (BankaHareketGirdi h : istek.getHareketler()) {
            LocalDate min = h.getTarih().minusDays(gun);
            LocalDate max = h.getTarih().plusDays(gun);
            BigDecimal tutar = h.getTutar().abs(); // TRY bazlı

            var adaylar = kalemRepo.adayKalemler(istek.getBankaHesapId(), min, max, tutar, esik);
            if (adaylar.isEmpty()) {
                nok.add(h.getDisRef() != null ? h.getDisRef() : h.getTarih() + "|" + tutar);
                continue;
            }

            // Basit strateji: ilk adayı seç, işaretle
            kalemRepo.mutabakatIsaretle(adaylar.subList(0, 1), h.getDisRef(), h.getTarih());
            ok.add(h.getDisRef() != null ? h.getDisRef() : h.getTarih() + "|" + tutar);
        }
        return new MutabakatSonucuDto(ok, nok);
    }

}