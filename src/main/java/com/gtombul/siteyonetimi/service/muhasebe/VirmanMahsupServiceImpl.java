package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.*;
import com.gtombul.siteyonetimi.mapper.muhasebe.MuhasebeFisMapper;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.muhasebe.Hesap;
import com.gtombul.siteyonetimi.repository.muhasebe.HesapRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeFisRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.MuhasebeKalemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VirmanMahsupServiceImpl implements VirmanMahsupService {

    private final HesapRepository hesapRepository;
    private final MuhasebeKalemRepository muhasebeKalemRepository;
    private final MuhasebeFisRepository muhasebeFisRepository;
    private final MuhasebeFisMapper muhasebeFisMapper;
    private final MuhasebeService muhasebeService;

    @Override
    @Transactional
    public MuhasebeFisDto virmanYap(VirmanRequestDto virmanRequestDto) {
        if (virmanRequestDto.getKaynakHesapId().equals(virmanRequestDto.getHedefHesapId()))
            throw new IllegalArgumentException("Kaynak ve hedef hesap aynı olamaz.");

        Hesap kaynak = hesapRepository.findById(virmanRequestDto.getKaynakHesapId())
                .orElseThrow(() -> new IllegalArgumentException("Kaynak hesap bulunamadı: " + virmanRequestDto.getKaynakHesapId()));
        Hesap hedef = hesapRepository.findById(virmanRequestDto.getHedefHesapId())
                .orElseThrow(() -> new IllegalArgumentException("Hedef hesap bulunamadı: " + virmanRequestDto.getHedefHesapId()));

        // Para birimi kontrolü
        if (kaynak.getParaBirimi() != hedef.getParaBirimi())
            throw new IllegalStateException("Virman için hesapların para birimi aynı olmalıdır.");

        // Nakit hesaplar (KASA/BANKA) eksiye düşmesin
        BigDecimal bakiyeKaynak = muhasebeKalemRepository.bakiye(kaynak.getId());
        if ((kaynak.getHesapTipi() == HesapTipi.KASA || kaynak.getHesapTipi() == HesapTipi.BANKA)
                && bakiyeKaynak.compareTo(virmanRequestDto.getTutar()) < 0) {
            throw new IllegalStateException("Kaynak nakit hesabın bakiyesi yetersiz.");
        }

        // Idempotency: idemKey verilmişse aynı işlem tekrar oluşturulmasın
        String fisNo = virmanRequestDto.getIdemKey() != null && !virmanRequestDto.getIdemKey().isBlank()
                ? "VRM-" + virmanRequestDto.getIdemKey().trim()
                : "VRM-" + System.currentTimeMillis();

        var mevcut = muhasebeFisRepository.findByFisNoAndDurumNot(fisNo, Durum.SILINDI);
        if (mevcut.isPresent()) return muhasebeFisMapper.toDto(mevcut.get());

        var kBorç = MuhasebeKalemDto.builder().hesapId(hedef.getId()).borcTutar(virmanRequestDto.getTutar().setScale(2, RoundingMode.HALF_UP)).build();
        var kAlcak = MuhasebeKalemDto.builder().hesapId(kaynak.getId()).alacakTutar(virmanRequestDto.getTutar().setScale(2, RoundingMode.HALF_UP)).build();

        var fisIstek = new FisOlusturIstekDto();
        fisIstek.setFisNo(fisNo);
        fisIstek.setTarih(LocalDate.now());
        fisIstek.setHareketTuru(HareketTuru.VIRMAN);
        fisIstek.setAciklama(virmanRequestDto.getAciklama());
        fisIstek.setKalemler(List.of(kBorç, kAlcak));

        return muhasebeService.fisOlustur(fisIstek);
    }

    @Override
    @Transactional
    public MuhasebeFisDto mahsupOlustur(MahsupRequestDto mahsupRequestDto) {
        // XOR ve toplam eşitliği — delegasyon öncesi hızlı validasyon
        BigDecimal tb = BigDecimal.ZERO, ta = BigDecimal.ZERO;
        for (var k : mahsupRequestDto.getKalemler()) {
            boolean xor = (k.getBorcTutar() == null) ^ (k.getAlacakTutar() == null);
            if (!xor) throw new IllegalArgumentException("Her kalemde BORÇ XOR ALACAK dolu olmalı.");
            if (k.getBorcTutar() != null) tb = tb.add(k.getBorcTutar());
            if (k.getAlacakTutar() != null) ta = ta.add(k.getAlacakTutar());
        }
        if (tb.setScale(2, RoundingMode.HALF_UP).compareTo(ta.setScale(2, RoundingMode.HALF_UP)) != 0)
            throw new IllegalStateException("Mahsupta BORÇ ve ALACAK toplamları eşit olmalıdır.");

        // (Opsiyonel) para birimi tutarlılığı — tüm hesapların aynı PB olması kuralını uygula
        var ilkPB = hesapRepository.findById(mahsupRequestDto.getKalemler().get(0).getHesapId())
                .orElseThrow(() -> new IllegalArgumentException("Hesap yok: " + mahsupRequestDto.getKalemler().get(0).getHesapId()))
                .getParaBirimi();
        for (var k : mahsupRequestDto.getKalemler()) {
            var h = hesapRepository.findById(k.getHesapId()).orElseThrow(() -> new IllegalArgumentException("Hesap yok: " + k.getHesapId()));
            if (h.getParaBirimi() != ilkPB)
                throw new IllegalStateException("Mahsup için tüm hesapların para birimi aynı olmalıdır.");
        }

        var fisIstek = new FisOlusturIstekDto();
        fisIstek.setFisNo(mahsupRequestDto.getFisNo() == null || mahsupRequestDto.getFisNo().isBlank() ? "MHS-" + System.currentTimeMillis() : mahsupRequestDto.getFisNo().trim());
        fisIstek.setTarih(mahsupRequestDto.getTarih() == null ? LocalDate.now() : mahsupRequestDto.getTarih());
        fisIstek.setHareketTuru(mahsupRequestDto.getHareketTuru() == null ? HareketTuru.MAHSUP : mahsupRequestDto.getHareketTuru());
        fisIstek.setAciklama(mahsupRequestDto.getAciklama());
        fisIstek.setKalemler(mahsupRequestDto.getKalemler());

        return muhasebeService.fisOlustur(fisIstek);
    }

}
