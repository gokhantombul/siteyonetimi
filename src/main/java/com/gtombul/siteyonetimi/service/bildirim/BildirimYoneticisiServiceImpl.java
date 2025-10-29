package com.gtombul.siteyonetimi.service.bildirim;

import com.gtombul.siteyonetimi.dto.bildirim.BildirimGonderRequestDto;
import com.gtombul.siteyonetimi.dto.bildirim.BildirimResponseDto;
import com.gtombul.siteyonetimi.model.bildirim.*;
import com.gtombul.siteyonetimi.repository.bildirim.BildirimRepository;
import com.gtombul.siteyonetimi.repository.bildirim.BildirimSablonuRepository;
import com.gtombul.siteyonetimi.repository.bildirim.IletisimTercihiRepository;
import com.gtombul.siteyonetimi.service.bildirim.saglayici.EmailSaglayici;
import com.gtombul.siteyonetimi.service.bildirim.saglayici.PushSaglayici;
import com.gtombul.siteyonetimi.service.bildirim.saglayici.SmsSaglayici;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BildirimYoneticisiServiceImpl implements BildirimYoneticisiService {

    private final IletisimTercihiRepository tercihRepo;
    private final BildirimSablonuRepository sablonRepo;
    private final BildirimRepository bildirimRepo;
    private SmsSaglayici smsSaglayici;
    private final PushSaglayici pushSaglayici;
    private final EmailSaglayici emailSaglayici;
    private final SablonMotoru sablonMotoru;

    @Override
    @Transactional
    public BildirimResponseDto gonder(BildirimGonderRequestDto istek) {
        var oncelik = Optional.ofNullable(istek.getOncelik()).orElse(BildirimOncelik.NORMAL);
        var tercih = (istek.getKisiId() != null) ? tercihRepo.findByKisiId(istek.getKisiId()).orElse(null) : null;

        // Şablon
        String baslik = istek.getBaslik();
        String icerik = istek.getIcerik();
        if (istek.getSablonKodu() != null) {
            var sablon = sablonRepo.findByKod(istek.getSablonKodu())
                    .orElseThrow(() -> new IllegalArgumentException("Şablon yok: " + istek.getSablonKodu()));
            baslik = (baslik == null) ? sablon.getBaslik() : baslik;
            icerik = (icerik == null) ? sablon.getIcerik() : icerik;
        }
        baslik = sablonMotoru.derle(baslik, istek.getSablonDegerleri());
        icerik = sablonMotoru.derle(icerik, istek.getSablonDegerleri());

        BildirimResponseDto cevap = new BildirimResponseDto(BildirimDurumu.GONDERILDI, "OK");

        for (var kanal : istek.getKanallar()) {
            try {
                switch (kanal) {
                    case SMS -> smsGonder(istek, tercih, icerik, oncelik);
                    case PUSH -> pushGonder(istek, tercih, baslik, icerik, oncelik);
                    case EMAIL -> emailGonder(istek, tercih, baslik, icerik, oncelik);
                }
            } catch (Exception ex) {
                log.error("Bildirim hatası: {}", ex.getMessage(), ex);
                cevap.setBildirimDurumu(BildirimDurumu.HATA);
                cevap.setMesaj(ex.getMessage());
                //kaydetLog(kanal, oncelik, istek, baslik, icerik, BildirimDurumu.HATA, ex.getMessage(), "",false);
            }
        }
        return cevap;
    }

    private void smsGonder(BildirimGonderRequestDto istek, IletisimTercihi tercih, String icerik, BildirimOncelik oncelik) throws Exception {
        String tel = Optional.ofNullable(istek.getTelefon()).orElse(tercih != null ? tercih.getTelefon() : null);
        if (tel == null || (tercih != null && !tercih.isSmsAktif())) return;
        //smsSaglayici.gonder(tel, icerik);
        kaydetLog(BildirimKanali.SMS, oncelik, istek, null, icerik, BildirimDurumu.GONDERILDI, null, tel, null, null, false);
    }

    private void pushGonder(BildirimGonderRequestDto istek, IletisimTercihi tercih, String baslik, String icerik, BildirimOncelik oncelik) throws Exception {
        String token = Optional.ofNullable(istek.getCihazToken()).orElse(tercih != null ? tercih.getCihazToken() : null);
        if (token == null || (tercih != null && !tercih.isPushAktif())) return;
        pushSaglayici.gonder(token, baslik, icerik, false);
        kaydetLog(BildirimKanali.PUSH, oncelik, istek, baslik, icerik, BildirimDurumu.GONDERILDI, null, null, token, null, false);
    }

    private void emailGonder(BildirimGonderRequestDto istek, IletisimTercihi tercih, String baslik, String icerik, BildirimOncelik oncelik) throws Exception {
        String eposta = Optional.ofNullable(istek.getEposta()).orElse(tercih != null ? tercih.getEposta() : null);
        if (eposta == null || (tercih != null && !tercih.isEmailAktif())) return;
        boolean html = Boolean.TRUE.equals(istek.getIcerikHtml());
        emailSaglayici.gonder(eposta, baslik, icerik, html);
        kaydetLog(BildirimKanali.EMAIL, oncelik, istek, baslik, icerik, BildirimDurumu.GONDERILDI, null, null, null, eposta, html);
    }

    private void kaydetLog(BildirimKanali kanal, BildirimOncelik oncelik,
                           BildirimGonderRequestDto istek, String baslik, String icerik,
                           BildirimDurumu durum, String hata,
                           String tel, String token, String eposta, boolean html) {
        var b = Bildirim.builder()
                .kanal(kanal)
                .oncelik(oncelik)
                .hedefTelefon(tel)
                .hedefToken(token)
                .hedefEposta(eposta)
                .baslik(baslik)
                .icerik(icerik)
                .icerikHtml(html)
                .bildirimDurumu(durum)
                .hataMesaji(hata)
                .referansTuru(istek.getReferansTuru())
                .referansId(istek.getReferansId())
                .build();
        bildirimRepo.save(b);
    }

}