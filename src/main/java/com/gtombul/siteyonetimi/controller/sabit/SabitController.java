package com.gtombul.siteyonetimi.controller.sabit;

import com.gtombul.siteyonetimi.model.anket.AnketTuru;
import com.gtombul.siteyonetimi.model.anket.KararOyu;
import com.gtombul.siteyonetimi.model.bildirim.BildirimDurumu;
import com.gtombul.siteyonetimi.model.bildirim.BildirimKanali;
import com.gtombul.siteyonetimi.model.bildirim.BildirimOncelik;
import com.gtombul.siteyonetimi.model.enums.StokIslemTuru;
import com.gtombul.siteyonetimi.model.enums.YakinlikDerecesi;
import com.gtombul.siteyonetimi.model.enums.ZiyaretciDurum;
import com.gtombul.siteyonetimi.model.kargo.KargoDurum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/sabit")
public class SabitController {

    @GetMapping("/yakinlik-derecesi")
    public ResponseEntity<List<YakinlikDerecesi>> getYakinlikDerecesiList() {
        return new ResponseEntity<>(Arrays.asList(YakinlikDerecesi.values()), HttpStatus.OK);
    }

    @GetMapping("/ziyaretci-durum")
    public ResponseEntity<List<ZiyaretciDurum>> getZiyaretciDurumList() {
        return new ResponseEntity<>(Arrays.asList(ZiyaretciDurum.values()), HttpStatus.OK);
    }

    @GetMapping("/stok-islem-turu")
    public ResponseEntity<List<StokIslemTuru>> getStokIslemTuruList() {
        return new ResponseEntity<>(Arrays.asList(StokIslemTuru.values()), HttpStatus.OK);
    }

    @GetMapping("/bildirim-kanali")
    public ResponseEntity<List<BildirimKanali>> getBildirimKanaliList() {
        return new ResponseEntity<>(Arrays.asList(BildirimKanali.values()), HttpStatus.OK);
    }

    @GetMapping("/bildirim-durumu")
    public ResponseEntity<List<BildirimDurumu>> getBildirimDurumuList() {
        return new ResponseEntity<>(Arrays.asList(BildirimDurumu.values()), HttpStatus.OK);
    }

    @GetMapping("/bildirim-oncelik")
    public ResponseEntity<List<BildirimOncelik>> getBildirimOncelikList() {
        return new ResponseEntity<>(Arrays.asList(BildirimOncelik.values()), HttpStatus.OK);
    }

    @GetMapping("/anket-turu")
    public ResponseEntity<List<AnketTuru>> getAnketTuruList() {
        return new ResponseEntity<>(Arrays.asList(AnketTuru.values()), HttpStatus.OK);
    }

    @GetMapping("/karar-oyu")
    public ResponseEntity<List<KararOyu>> getKararOyuList() {
        return new ResponseEntity<>(Arrays.asList(KararOyu.values()), HttpStatus.OK);
    }

    @GetMapping("/kargo-durum")
    public ResponseEntity<List<KargoDurum>> getKArgoDurumList() {
        return new ResponseEntity<>(Arrays.asList(KargoDurum.values()), HttpStatus.OK);
    }

}
