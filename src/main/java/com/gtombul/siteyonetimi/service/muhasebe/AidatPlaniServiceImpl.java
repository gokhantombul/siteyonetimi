package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.AidatPlaniDto;
import com.gtombul.siteyonetimi.dto.muhasebe.FisOlusturIstekDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MuhasebeKalemDto;
import com.gtombul.siteyonetimi.mapper.muhasebe.AidatPlaniMapper;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.muhasebe.AidatAtamaKaydi;
import com.gtombul.siteyonetimi.model.muhasebe.AidatPlani;
import com.gtombul.siteyonetimi.model.muhasebe.Hesap;
import com.gtombul.siteyonetimi.repository.muhasebe.AidatAtamaKaydiRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.AidatPlaniRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.HesapRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
public class AidatPlaniServiceImpl extends BaseServiceImpl<AidatPlani, AidatPlaniDto, Long> implements AidatPlaniService {

    private final AidatPlaniRepository aidatPlaniRepository;
    private final AidatAtamaKaydiRepository aidatAtamaKaydiRepository;
    private final HesapRepository hesapRepository;
    private final MuhasebeService muhasebeService;
    private final AidatPlaniMapper aidatPlaniMapper;

    public AidatPlaniServiceImpl(AidatPlaniRepository aidatPlaniRepository, AidatPlaniMapper aidatPlaniMapper, AidatAtamaKaydiRepository aidatAtamaKaydiRepository, HesapRepository hesapRepository, MuhasebeService muhasebeService) {
        super(aidatPlaniRepository, aidatPlaniMapper, AidatPlani.class);
        this.aidatPlaniRepository = aidatPlaniRepository;
        this.aidatAtamaKaydiRepository = aidatAtamaKaydiRepository;
        this.hesapRepository = hesapRepository;
        this.muhasebeService = muhasebeService;
        this.aidatPlaniMapper = aidatPlaniMapper;
    }

    @Override
    @Transactional
    public AidatPlaniDto olustur(AidatPlaniDto dto) {
        var ent = aidatPlaniMapper.toEntity(dto);
        ent = aidatPlaniRepository.save(ent);
        return aidatPlaniMapper.toDto(ent);
    }

    @Override
    @Transactional
    public void donemIcinAidatOlustur(Long planId, YearMonth donem) {
        var plan = aidatPlaniRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Aidat planı bulunamadı: " + planId));

        // Örnek: tüm daire hesapları (blok filtresi varsa onu uygula)
        List<Hesap> daireHesaplari = plan.getBlokKodu() == null
                ? hesapRepository.findAll().stream().filter(h -> h.getHesapTipi() == HesapTipi.DAIRE).toList()
                : hesapRepository.findAll().stream().filter(h -> h.getHesapTipi() == HesapTipi.DAIRE /* ve blok filtresi */).toList();

        for (Hesap daire : daireHesaplari) {
            var key = donem.toString(); // "YYYY-MM"
            if (aidatAtamaKaydiRepository.existsByPlanIdAndDonemYyyyMmAndDaireHesapIdAndDurumNot(plan.getId(), key, daire.getId(), Durum.SILINDI)) {
                continue; // idempotent: bu daire için bu dönem yazılmış
            }

            // Karşı hesap: GELIR hesabı (tekil olsun diye; yoksa oluşturulabilir)
            var gelirHesabi = hesapRepository.findByHesapTipiAndReferansId(HesapTipi.GELIR, null)
                    .orElseGet(() -> hesapRepository.save(Hesap.builder()
                            .ad("Aidat Gelirleri")
                            .hesapTipi(HesapTipi.GELIR)
                            .paraBirimi(plan.getParaBirimi())
                            .build()));

            // Fiş oluştur: Daire BORÇ, Gelir ALACAK
            muhasebeService.fisOlustur(new FisOlusturIstekDto() {{
                setFisNo("ADT-" + daire.getId() + "-" + key);
                setTarih(donem.atDay(1));
                setHareketTuru(HareketTuru.AIDAT);
                setAciklama("Aidat " + key + " - " + daire.getAd());
                setKalemler(List.of(
                        new MuhasebeKalemDto() {{
                            setHesapId(daire.getId());
                            setBorcTutar(plan.getTutar());
                        }},
                        new MuhasebeKalemDto() {{
                            setHesapId(gelirHesabi.getId());
                            setAlacakTutar(plan.getTutar());
                        }}
                ));
            }});

            // Atama kaydı
            aidatAtamaKaydiRepository.save(AidatAtamaKaydi.builder()
                    .plan(plan)
                    .donemYyyyMm(key)
                    .daireHesap(daire)
                    .build());
        }
    }

    @Override
    @Transactional
    public void tumPlanlarIcinAidatOlustur(YearMonth donem) {
        aidatPlaniRepository.findAll().forEach(p -> donemIcinAidatOlustur(p.getId(), donem));
    }

}
