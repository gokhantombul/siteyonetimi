package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.EkstreSatirDto;
import com.gtombul.siteyonetimi.dto.muhasebe.HesapDto;
import com.gtombul.siteyonetimi.mapper.muhasebe.HesapMapper;
import com.gtombul.siteyonetimi.mapper.muhasebe.MuhasebeKalemMapper;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import com.gtombul.siteyonetimi.model.muhasebe.Hesap;
import com.gtombul.siteyonetimi.repository.muhasebe.HesapRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeKalemRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class HesapServiceImpl extends BaseServiceImpl<Hesap, HesapDto, Long> implements HesapService {

    private final HesapRepository hesapRepository;
    private final MuhasebeKalemRepository muhasebeKalemRepository;
    private final MuhasebeKalemMapper muhasebeKalemMapper;

    public HesapServiceImpl(HesapRepository hesapRepository, HesapMapper hesapMapper, MuhasebeKalemRepository muhasebeKalemRepository,
                            MuhasebeKalemMapper muhasebeKalemMapper) {
        super(hesapRepository, hesapMapper, Hesap.class);
        this.hesapRepository = hesapRepository;
        this.muhasebeKalemRepository = muhasebeKalemRepository;
        this.muhasebeKalemMapper = muhasebeKalemMapper;
    }

    @Override
    public BigDecimal bakiye(Long hesapId) {
        return muhasebeKalemRepository.bakiye(hesapId).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public List<EkstreSatirDto> ekstre(Long hesapId, LocalDate baslangic, LocalDate bitis) {
        return muhasebeKalemRepository.ekstre(hesapId, baslangic, bitis).stream()
                .map(k -> new EkstreSatirDto(k.getFis().getTarih(), k.getFis().getFisNo(), k.getBorcTutar(), k.getAlacakTutar(), k.getAciklama()))
                .toList();
    }

    @Override
    @Transactional
    public HesapDto hesapGetirVeyaOlustur(HesapTipi tip, Long referansId, String ad) {
        return hesapRepository.findByHesapTipiAndReferansIdAndDurumNot(tip, referansId, Durum.SILINDI)
                .map(mapper::toDto)
                .orElseGet(() -> mapper.toDto(hesapRepository.save(
                        Hesap.builder().ad(ad).hesapTipi(tip).referansId(referansId).paraBirimi(ParaBirimi.TRY).build()
                )));
    }

    /* hook'lar (BaseServiceImpl patch'ini uyguladıysan) */
    @Override
    protected void beforeCreate(Hesap ent) {
        if (ent.getAd() != null) ent.setAd(ent.getAd().trim());
        hesapRepository.findByHesapTipiAndReferansIdAndDurumNot(ent.getHesapTipi(), ent.getReferansId(), Durum.SILINDI)
                .ifPresent(x -> {
                    throw new IllegalStateException("Bu tip ve referans için aktif hesap zaten var.");
                });
    }

    @Override
    protected void beforeUpdate(Hesap ent) {
        // Hareketi olan hesapta tip/para birimi değişmesin
        if (ent.getId() != null && muhasebeKalemRepository.existsByHesapIdAndDurumNot(ent.getId(), Durum.SILINDI)) {
            var eski = hesapRepository.findById(ent.getId()).orElseThrow();
            if (eski.getHesapTipi() != ent.getHesapTipi())
                throw new IllegalStateException("Hareketi olan hesabın tipi değiştirilemez.");
            if (eski.getParaBirimi() != ent.getParaBirimi())
                throw new IllegalStateException("Hareketi olan hesabın para birimi değiştirilemez.");
        }
    }

    @Override
    protected void beforeDelete(Hesap ent) {
        var bakiye = muhasebeKalemRepository.bakiye(ent.getId());
        if (bakiye.signum() != 0) throw new IllegalStateException("Bakiyesi sıfır olmayan hesap silinemez.");
        if (muhasebeKalemRepository.existsByHesapIdAndDurumNot(ent.getId(), Durum.SILINDI))
            throw new IllegalStateException("Hareketi olan hesap silinemez.");
    }

}
