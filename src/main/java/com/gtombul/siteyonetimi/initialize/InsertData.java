package com.gtombul.siteyonetimi.initialize;

import com.gtombul.siteyonetimi.config.CustomUserDetailsService;
import com.gtombul.siteyonetimi.dto.RegisterRequest;
import com.gtombul.siteyonetimi.dto.adres.il.IlDto;
import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.dto.banka.BankaDto;
import com.gtombul.siteyonetimi.dto.bina.FirmaDto;
import com.gtombul.siteyonetimi.dto.demirbas.DemirbasDto;
import com.gtombul.siteyonetimi.dto.demirbas.DemirbasKategoriDto;
import com.gtombul.siteyonetimi.dto.demirbas.DepoDto;
import com.gtombul.siteyonetimi.dto.demirbas.StokHareketDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoOlusturGuncelleDto;
import com.gtombul.siteyonetimi.dto.muhasebe.AidatPlaniDto;
import com.gtombul.siteyonetimi.dto.muhasebe.HesapDto;
import com.gtombul.siteyonetimi.dto.personel.PersonelDto;
import com.gtombul.siteyonetimi.dto.ziyaretci.ZiyaretciDto;
import com.gtombul.siteyonetimi.mapper.muhasebe.AidatPlaniMapper;
import com.gtombul.siteyonetimi.model.adres.AdresBilgisi;
import com.gtombul.siteyonetimi.model.adres.IletisimBilgisi;
import com.gtombul.siteyonetimi.model.anket.Anket;
import com.gtombul.siteyonetimi.model.anket.AnketSecenek;
import com.gtombul.siteyonetimi.model.anket.AnketTuru;
import com.gtombul.siteyonetimi.model.arac.Arac;
import com.gtombul.siteyonetimi.model.bina.*;
import com.gtombul.siteyonetimi.model.enums.*;
import com.gtombul.siteyonetimi.model.ilan.*;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import com.gtombul.siteyonetimi.model.muhasebe.AidatPlani;
import com.gtombul.siteyonetimi.repository.anket.AnketRepository;
import com.gtombul.siteyonetimi.repository.arac.AracRepository;
import com.gtombul.siteyonetimi.repository.bina.*;
import com.gtombul.siteyonetimi.repository.ilan.IlanKategoriRepository;
import com.gtombul.siteyonetimi.repository.ilan.IlanRepository;
import com.gtombul.siteyonetimi.repository.insan.KisiRepository;
import com.gtombul.siteyonetimi.repository.muhasebe.AidatPlaniRepository;
import com.gtombul.siteyonetimi.service.banka.BankaService;
import com.gtombul.siteyonetimi.service.bina.FirmaService;
import com.gtombul.siteyonetimi.service.demirbas.DemirbasKategoriService;
import com.gtombul.siteyonetimi.service.demirbas.DemirbasService;
import com.gtombul.siteyonetimi.service.demirbas.DepoService;
import com.gtombul.siteyonetimi.service.demirbas.StokHareketService;
import com.gtombul.siteyonetimi.service.il.IlService;
import com.gtombul.siteyonetimi.service.ilce.IlceService;
import com.gtombul.siteyonetimi.service.kargo.KargoService;
import com.gtombul.siteyonetimi.service.muhasebe.AidatPlaniService;
import com.gtombul.siteyonetimi.service.muhasebe.HesapService;
import com.gtombul.siteyonetimi.service.muhasebe.MuhasebeService;
import com.gtombul.siteyonetimi.service.personel.PersonelService;
import com.gtombul.siteyonetimi.service.ziyaretci.ZiyaretciService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Profile({"dev", "local"}) // prod’da çalışmaz yada aşadağıdaki kontrolde yapilabilir
//@ConditionalOnProperty(prefix = "demo", name = "veri-yukle", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
@Transactional
@Order(100) // başka runner’lar varsa sıra
public class InsertData implements CommandLineRunner {

    private static final ZoneId IST = ZoneId.of("Europe/Istanbul");

    private final CustomUserDetailsService customUserDetailsService;
    private final BankaService bankaService;
    private final IlService ilService;
    private final IlceService ilceService;
    private final FirmaService firmaService;
    private final SiteRepository siteRepository;
    private final BlokRepository blokRepository;
    private final KatRepository katRepository;
    private final DaireRepository daireRepository;
    private final AracRepository aracRepository;
    private final KisiRepository kisiRepository;
    private final MulkSahipligiRepository mulkSahipligiRepository;
    private final KiraSozlesmesiRepository kiraSozlesmesiRepository;
    private final DaireOturumRepository daireOturumRepository;

    private final HesapService hesapService;
    private final MuhasebeService muhasebeService;
    private final AidatPlaniService aidatPlaniService;
    private final AidatPlaniMapper aidatPlaniMapper;
    private final AidatPlaniRepository aidatPlaniRepository;
    private final PersonelService personelService;
    private final ZiyaretciService ziyaretciService;

    private final DemirbasKategoriService demirbasKategoriService;
    private final DepoService depoService;
    private final DemirbasService demirbasService;
    private final StokHareketService stokHareketService;
    private final AnketRepository anketRepository;
    private final KargoService kargoService;
    private final IlanKategoriRepository kategoriRepo;
    private final IlanRepository ilanRepo;

    @Override
    public void run(String... args) {
        kullaniciOlustur();
        illeriOlustur();
        ilceleriOlustur();
        bankaOlustur();
        firmaOlustur();
        siteKatBlokDaireOlustur();

        hesapVeDigerleriniOlustur();

        personelOlustur();
        ziyaretciOlustur();
        demirbasOlustur();

        anketKararOrnekleriOlustur();
        kargoDataOlustur();

        ilanOlustur();
    }

    private void ilanOlustur() {
        if (kategoriRepo.count() == 0) {
            var esya = kategoriRepo.save(IlanKategori.builder().ad("Ev Eşyaları").aciklama("Mobilya, beyaz eşya, dekor").build());
            var hizmet = kategoriRepo.save(IlanKategori.builder().ad("Hizmet").aciklama("Temizlik, tamirat, özel ders").build());
            var arac = kategoriRepo.save(IlanKategori.builder().ad("Araç").aciklama("Araba, motosiklet, bisiklet").build());

            var i1 = Ilan.builder()
                    .baslik("Çalışma Masası (az kullanılmış)")
                    .aciklama("Çiziksiz, Anadolu yakası teslim")
                    .turu(IlanTuru.ESYA)
                    .kategori(esya)
                    .fiyat(new BigDecimal("2500.00"))
                    .yayimTarihi(LocalDate.now())
                    .ilanDurum(IlanDurum.AKTIF)
                    .onayDurumu(IlanOnayDurumu.ONAYLI)
                    .build();

            var r1 = IlanResim.builder().ilan(i1).kaynakKayitId(1001L).siraNo(0).build();
            i1.setResimler(List.of(r1));

            var i2 = Ilan.builder()
                    .baslik("Temizlik Hizmeti (saatlik)")
                    .aciklama("Güvenilir ve titiz temizlik")
                    .turu(IlanTuru.HIZMET)
                    .kategori(hizmet)
                    .fiyat(new BigDecimal("500.00"))
                    .yayimTarihi(LocalDate.now())
                    .ilanDurum(IlanDurum.AKTIF)
                    .onayDurumu(IlanOnayDurumu.INCELEME)
                    .build();

            var i3 = Ilan.builder()
                    .baslik("Şehir içi bisiklet")
                    .aciklama("Bakımlı, 21 vites")
                    .turu(IlanTuru.ARAC)
                    .kategori(arac)
                    .fiyat(new BigDecimal("4500.00"))
                    .yayimTarihi(LocalDate.now())
                    .ilanDurum(IlanDurum.AKTIF)
                    .onayDurumu(IlanOnayDurumu.REDDEDILDI)
                    .reddetmeNedeni("Yetersiz açıklama")
                    .build();

            ilanRepo.saveAll(List.of(i1, i2, i3));
            log.info("İlan & Kategori örnek verileri yüklendi.");
        }
    }

    private void demirbasOlustur() {
        var kategori = demirbasKategoriService.create(
                DemirbasKategoriDto.builder().ad("Elektronik").aciklama("Elektronik teçhizat").build());

        var depo = depoService.create(
                DepoDto.builder().ad("Merkez Depo").adres("A Blok -1").aciklama("Ana depo").build());

        var sandalye = demirbasService.create(
                DemirbasDto.builder()
                        .ad("Toplantı Sandalyesi")
                        .barkod("BRKD-0001")
                        .etiketNo("ETK-1001")
                        .kategoriId(kategori.getId())
                        .depoId(depo.getId())
                        .miktar(new java.math.BigDecimal("50"))
                        .birim("ADET")
                        .minimumSeviye(new java.math.BigDecimal("10"))
                        .aciklama("Konferans salonu")
                        .build()
        );

        stokHareketService.create(
                StokHareketDto.builder()
                        .demirbasId(sandalye.getId())
                        .depoId(depo.getId())
                        .islemTuru(StokIslemTuru.GIRIS)
                        .miktar(new java.math.BigDecimal("50"))
                        .referansNo("IR-2025-0001")
                        .aciklama("İlk giriş")
                        .build()
        );
    }

    private void kullaniciOlustur() {
        RegisterRequest registerRequest = new RegisterRequest("gokhantombul@hotmail.com", "1234");
        customUserDetailsService.saveUser(registerRequest);
    }

    private void ziyaretciOlustur() {
        // Henüz gelmemiş (davetli) — daire null
        ziyaretciService.create(ZiyaretciDto.builder()
                .ad("Mehmet").soyad("Kaya")
                .telefon("05551112233")
                .aciklama("İlk kez davet edildi")
                .yakinlikDerecesi(YakinlikDerecesi.ARKADAS)
                .ziyaretciDurum(ZiyaretciDurum.DAVETLI)
                .build());

        // Gelmiş — daire zorunlu
        ziyaretciService.create(ZiyaretciDto.builder()
                .ad("Sinem").soyad("Çelik")
                .telefon("05553334455")
                .aciklama("Kardeşini ziyaret")
                .yakinlikDerecesi(YakinlikDerecesi.AILE)
                .ziyaretciDurum(ZiyaretciDurum.GELDI)
                .daireId(2L)
                .build());

        // Çıkmış — daire null yapılabilir
        ziyaretciService.create(ZiyaretciDto.builder()
                .ad("Ali").soyad("Demir")
                .telefon("05557778899")
                .aciklama("Geldi, ayrıldı")
                .yakinlikDerecesi(YakinlikDerecesi.DIGER)
                .ziyaretciDurum(ZiyaretciDurum.CIKTI)
                .daireId(null)
                .build());

        log.info("👉 Dummy ziyaretçiler eklendi.");
    }

    private void personelOlustur() {
        // Kisi oluştur
        var kisi1 = Kisi.builder()
                .ad("Ahmet")
                .soyad("YILMAZ")
                .build();
        Kisi kisiDb = kisiRepository.save(kisi1);

        // Personel oluştur
        var p1 = PersonelDto.builder()
                .kisiId(kisiDb.getId())
                .gorev(PersonelGorev.GUVENLIK)
                .iseGirisTarihi(LocalDate.now().minusYears(1))
                .maas(new BigDecimal("30000.00"))
                .aciklama("Gece vardiyası")
                .build();
        personelService.create(p1);
    }

    private void firmaOlustur() {
        List<FirmaDto> firmaListesi = new ArrayList<>();
        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Emlak Konut GYO A.Ş.")
                .kisaAd("Emlak Konut")
                .adres("Atatürk Mah. Çitlenbik Cad. No:4 Ataşehir / İstanbul")
                .telefon("+90 216 555 00 00")        // (uydurma)
                .internetAdresi("https://www.emlakkonut.com.tr")
                .tur(FirmaTuru.SAHIP)
                .faks("+90 216 555 00 01")          // (uydurma)
                .email("info@emlakkonut.com.tr")    // (tahmini)
                .vkn("1112223334")                  // (uydurma)
                .vergiDairesi("Ataşehir VD")        // (uydurma)
                .aciklama("Gayrimenkul yatırım ortaklığı; konut ve karma projeler.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Sinpaş GYO A.Ş.")
                .kisaAd("Sinpaş")
                .adres("Altunizade Mah. Kısıklı Cad. No:45 Üsküdar / İstanbul")
                .telefon("+90 216 444 19 74")       // (uydurma)
                .internetAdresi("https://www.sinpas.com.tr")
                .tur(FirmaTuru.SAHIP)
                .faks("+90 216 444 19 75")          // (uydurma)
                .email("info@sinpas.com.tr")        // (tahmini)
                .vkn("2233445566")                  // (uydurma)
                .vergiDairesi("Üsküdar VD")         // (uydurma)
                .aciklama("Konut ve ticari gayrimenkul geliştiricisi.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Tekfen İnşaat ve Tesisat A.Ş.")
                .kisaAd("Tekfen İnşaat")
                .adres("Büyükdere Cad. No:209 Levent / İstanbul")
                .telefon("+90 212 359 33 00")       // (uydurma format)
                .internetAdresi("https://www.tekfen.com.tr")
                .tur(FirmaTuru.YUKLENICI)
                .faks("+90 212 359 33 01")          // (uydurma)
                .email("info@tekfen.com.tr")        // (tahmini)
                .vkn("9876543210")                  // (uydurma)
                .vergiDairesi("Beşiktaş VD")        // (uydurma)
                .aciklama("Türkiye ve yurtdışında altyapı, endüstriyel tesis, üstyapı projeleri.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Rönesans İnşaat")
                .kisaAd("Rönesans")
                .adres("İnönü Mah. Cumhuriyet Cad. No:50 Çankaya / Ankara")
                .telefon("+90 312 444 80 80")       // (uydurma)
                .internetAdresi("https://www.ronesans.com")
                .tur(FirmaTuru.YUKLENICI)
                .faks("+90 312 444 80 81")          // (uydurma)
                .email("info@ronesans.com")         // (tahmini)
                .vkn("3344556677")                  // (uydurma)
                .vergiDairesi("Çankaya VD")         // (uydurma)
                .aciklama("Hastane, AVM, endüstriyel tesis ve altyapı müteahhidi.")
                .ilce(new IlceDto(6L))              // Ankara
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("ENKA İnşaat ve Sanayi A.Ş.")
                .kisaAd("ENKA")
                .adres("Balmumcu Mah. Zincirlikuyu Yolu No:10 Beşiktaş / İstanbul")
                .telefon("+90 212 276 00 00")       // (uydurma)
                .internetAdresi("https://www.enka.com")
                .tur(FirmaTuru.YUKLENICI)
                .faks("+90 212 276 00 01")          // (uydurma)
                .email("info@enka.com")             // (tahmini)
                .vkn("4455667788")                  // (uydurma)
                .vergiDairesi("Beşiktaş VD")        // (uydurma)
                .aciklama("Enerji tesisleri, altyapı ve üstyapı projeleri.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Eczacıbaşı Yapı Gereçleri (VitrA)")
                .kisaAd("VitrA")
                .adres("Ayazağa Mah. Mimar Sinan Cad. No:41 Sarıyer / İstanbul")
                .telefon("+90 212 289 10 10")       // (uydurma)
                .internetAdresi("https://www.vitra.com.tr")
                .tur(FirmaTuru.TEDARIKCI)
                .faks("+90 212 289 10 11")          // (uydurma)
                .email("info@vitra.com.tr")         // (tahmini)
                .vkn("5566778899")                  // (uydurma)
                .vergiDairesi("Sarıyer VD")         // (uydurma)
                .aciklama("Seramik, vitrifiye ve banyo çözümleri tedarikçisi.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("İzocam Ticaret ve Sanayi A.Ş.")
                .kisaAd("İzocam")
                .adres("Gebze Organize Sanayi Bölgesi, 3000. Cad. No:3 Gebze / Kocaeli")
                .telefon("+90 262 751 10 00")       // (uydurma)
                .internetAdresi("https://www.izocam.com.tr")
                .tur(FirmaTuru.TEDARIKCI)
                .faks("+90 262 751 10 01")          // (uydurma)
                .email("info@izocam.com.tr")        // (tahmini)
                .vkn("6677889900")                  // (uydurma)
                .vergiDairesi("Gebze VD")           // (uydurma)
                .aciklama("Isı ve ses yalıtım malzemeleri tedarikçisi.")
                .ilce(new IlceDto(41L))             // Kocaeli
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("HES Kablo Sanayi ve Ticaret A.Ş.")
                .kisaAd("HES Kablo")
                .adres("Organize Sanayi Bölgesi 6. Cad. No:11 Kocasinan / Kayseri")
                .telefon("+90 352 444 04 37")       // (uydurma)
                .internetAdresi("https://www.hes.com.tr")
                .tur(FirmaTuru.TEDARIKCI)
                .faks("+90 352 444 04 38")          // (uydurma)
                .email("info@hes.com.tr")           // (tahmini)
                .vkn("7788990011")                  // (uydurma)
                .vergiDairesi("Kocasinan VD")       // (uydurma)
                .aciklama("Enerji ve iletişim kabloları üreticisi.")
                .ilce(new IlceDto(38L))             // Kayseri
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("KONE Asansör San. ve Tic. A.Ş.")
                .kisaAd("KONE")
                .adres("Fatih Sultan Mehmet Mah. Balkan Cad. No:62 Ümraniye / İstanbul")
                .telefon("+90 216 665 16 00")       // (uydurma format)
                .internetAdresi("https://www.kone.com.tr")
                .tur(FirmaTuru.BAKIM)
                .faks("+90 216 665 16 01")          // (uydurma)
                .email("info@kone.com.tr")          // (tahmini)
                .vkn("8899001122")                  // (uydurma)
                .vergiDairesi("Ümraniye VD")        // (uydurma)
                .aciklama("Asansör ve yürüyen merdiven bakım/modernizasyon hizmetleri.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("OTIS Asansör San. ve Tic. A.Ş.")
                .kisaAd("OTIS")
                .adres("İçerenköy Mah. Kayışdağı Cad. No:130 Ataşehir / İstanbul")
                .telefon("+90 216 578 00 00")       // (uydurma)
                .internetAdresi("https://www.otis.com/tr")
                .tur(FirmaTuru.BAKIM)
                .faks("+90 216 578 00 01")          // (uydurma)
                .email("info.tr@otis.com")          // (tahmini)
                .vkn("9900112233")                  // (uydurma)
                .vergiDairesi("Ataşehir VD")        // (uydurma)
                .aciklama("Asansör montaj, bakım ve arıza hizmetleri.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Alarko Carrier Servis Ağı")
                .kisaAd("Alarko Carrier")
                .adres("Yenibosna Merkez Mah. Sanayi Cad. No:45 Bahçelievler / İstanbul")
                .telefon("+90 212 444 12 22")       // (uydurma)
                .internetAdresi("https://www.alarko-carrier.com.tr")
                .tur(FirmaTuru.BAKIM)
                .faks("+90 212 444 12 23")          // (uydurma)
                .email("servis@alarko-carrier.com.tr") // (tahmini)
                .vkn("1011121314")                  // (uydurma)
                .vergiDairesi("Bahçelievler VD")    // (uydurma)
                .aciklama("Isıtma-soğutma sistemleri için yetkili bakım ve servis.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Pronet Güvenlik Hizmetleri A.Ş.")
                .kisaAd("Pronet")
                .adres("Esentepe Mah. Büyükdere Cad. No:201 Şişli / İstanbul")
                .telefon("+90 212 367 64 00")       // (uydurma format)
                .internetAdresi("https://www.pronet.com.tr")
                .tur(FirmaTuru.GUVENLIK)
                .faks("+90 212 367 64 01")          // (uydurma)
                .email("info@pronet.com.tr")        // (tahmini)
                .vkn("1213141516")                  // (uydurma)
                .vergiDairesi("Şişli VD")           // (uydurma)
                .aciklama("Alarm, kamera ve özel güvenlik çözümleri.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Securitas Güvenlik Hizmetleri A.Ş.")
                .kisaAd("Securitas")
                .adres("Küçükbakkalköy Mah. Kayışdağı Cad. No:68 Ataşehir / İstanbul")
                .telefon("+90 216 444 18 81")       // (uydurma)
                .internetAdresi("https://www.securitas.com.tr")
                .tur(FirmaTuru.GUVENLIK)
                .faks("+90 216 444 18 82")          // (uydurma)
                .email("info@securitas.com.tr")     // (tahmini)
                .vkn("1314151617")                  // (uydurma)
                .vergiDairesi("Ataşehir VD")        // (uydurma)
                .aciklama("Özel güvenlik, izleme ve risk yönetimi hizmetleri.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Tepe Savunma ve Güvenlik Sistemleri")
                .kisaAd("Tepe Güvenlik")
                .adres("Bilkent Plaza B1 Blok Çankaya / Ankara")
                .telefon("+90 312 444 07 57")       // (uydurma)
                .internetAdresi("https://www.tepesavunma.com.tr")
                .tur(FirmaTuru.GUVENLIK)
                .faks("+90 312 444 07 58")          // (uydurma)
                .email("info@tepesavunma.com.tr")   // (tahmini)
                .vkn("1415161718")                  // (uydurma)
                .vergiDairesi("Çankaya VD")         // (uydurma)
                .aciklama("Fiziki güvenlik ve elektronik güvenlik çözümleri.")
                .ilce(new IlceDto(6L))              // Ankara
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("ISS Tesis Yönetim Hizmetleri A.Ş.")
                .kisaAd("ISS Türkiye")
                .adres("Maslak Mah. Ahi Evran Cad. No:6 Sarıyer / İstanbul")
                .telefon("+90 212 444 47 77")       // (uydurma)
                .internetAdresi("https://www.tr.issworld.com")
                .tur(FirmaTuru.DIGER)
                .faks("+90 212 444 47 78")          // (uydurma)
                .email("info.tr@issworld.com")      // (tahmini)
                .vkn("1516171819")                  // (uydurma)
                .vergiDairesi("Sarıyer VD")         // (uydurma)
                .aciklama("Entegre tesis ve site yönetimi, temizlik, teknik ve destek hizmetleri.")
                .ilce(new IlceDto(34L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Tepe Servis ve Yönetim A.Ş.")
                .kisaAd("Tepe Servis")
                .adres("Bilkent Plaza B1 Blok Çankaya / Ankara")
                .telefon("+90 312 444 07 56")       // (uydurma)
                .internetAdresi("https://www.tepeservis.com.tr")
                .tur(FirmaTuru.DIGER)
                .faks("+90 312 444 07 59")          // (uydurma)
                .email("info@tepeservis.com.tr")    // (tahmini)
                .vkn("1617181920")                  // (uydurma)
                .vergiDairesi("Çankaya VD")         // (uydurma)
                .aciklama("Profesyonel tesis ve site yönetimi, temizlik ve yardımcı hizmetler.")
                .ilce(new IlceDto(6L))
                .build());

        firmaListesi.add(FirmaDto.builder()
                .firmaUnvani("Konut Tesis Yönetimi A.Ş.")
                .kisaAd("Konut Yönetim")
                .adres("Teyfikbey Mah. Halkalı Cad. No:132/1 Küçükçekmece / İstanbul")
                .telefon("+90 212 603 66 86")       // (uydurma format)
                .internetAdresi("https://www.konutas.com")
                .tur(FirmaTuru.DIGER)
                .faks("+90 212 603 66 87")          // (uydurma)
                .email("info@konutas.com")          // (tahmini)
                .vkn("1718192021")                  // (uydurma)
                .vergiDairesi("Küçükçekmece VD")    // (uydurma)
                .aciklama("Site ve rezidans yönetimi; teknik, temizlik, bahçe ve güvenlik koordinasyonu.")
                .ilce(new IlceDto(34L))
                .build());

        firmaService.tamaminiKaydet(firmaListesi);
    }

    private void bankaOlustur() {
        List<BankaDto> bankaDtoList = new ArrayList<>();
        bankaDtoList.add(BankaDto.builder().ad("Akbank").telefon("444 25 25").postaKod(34330).merkezAdresIlce(new IlceDto(430L)).merkezAdres("Sabancı Center, 4. Levent, Beşiktaş/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Albaraka Türk Katılım Bankası").telefon("444 5 666").postaKod(34768).merkezAdresIlce(new IlceDto(457L)).merkezAdres("Saray Mah., Dr. Adnan Büyükdeniz Cad. No:6, Ümraniye/İstanbul").aciklama("Katılım bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Alternatifbank").telefon("444 00 55").postaKod(34485).merkezAdresIlce(new IlceDto(450L)).merkezAdres("Ayazağa Mah., Cendere Cad., Vadistanbul Bulvar No:108 D Blok, Sarıyer/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Anadolubank").telefon("444 55 55").postaKod(34676).merkezAdresIlce(new IlceDto(458L)).merkezAdres("Kısıklı Cad. No:45, Üsküdar/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Arap Türk Bankası").telefon("0212 373 63 00").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:129, Esentepe, Şişli/İstanbul").aciklama("Yabancı sermayeli mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Birleşik Fon Bankası").telefon("0212 214 10 00").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:143/1, Esentepe, Şişli/İstanbul").aciklama("Fon bankası (mevduat)").build());
        bankaDtoList.add(BankaDto.builder().ad("Burgan Bank").telefon("0850 222 8 822").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:209, Esentepe, Şişli/İstanbul").aciklama("Kuveyt merkezli özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Citibank A.Ş.").telefon("0212 319 50 00").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:173, 1. Levent, Şişli/İstanbul").aciklama("ABD merkezli yabancı banka (kurumsal ağırlık)").build());
        bankaDtoList.add(BankaDto.builder().ad("DenizBank").telefon("0850 222 0 800").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:141, Torun Center D Blok, Esentepe, Şişli/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Deutsche Bank A.Ş.").telefon("0212 317 01 00").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:191, Levent, Şişli/İstanbul").aciklama("Almanya merkezli yabancı banka (kurumsal)").build());
        bankaDtoList.add(BankaDto.builder().ad("Emlak Katılım Bankası").telefon("0850 222 26 56").postaKod(34746).merkezAdresIlce(new IlceDto(423L)).merkezAdres("İstanbul Finans Merkezi, Begonya Sk. No:9A, Ataşehir/İstanbul").aciklama("Kamu katılım bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Fibabanka").telefon("444 88 88").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Esentepe Mah., Büyükdere Cad. No:129, Şişli/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Garanti BBVA").telefon("444 0 333").postaKod(34340).merkezAdresIlce(new IlceDto(430L)).merkezAdres("Nisbetiye Mah., Aytar Cad. No:2, Levent, Beşiktaş/İstanbul").aciklama("Özel mevduat bankası (BBVA ortaklığı)").build());
        bankaDtoList.add(BankaDto.builder().ad("Halkbank").telefon("0850 222 0 400").postaKod(34758).merkezAdresIlce(new IlceDto(457L)).merkezAdres("İstanbul Finans Merkezi, A Blok, Sitare Cad. No:3, Ümraniye/İstanbul").aciklama("Kamu mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("HSBC Bank A.Ş.").telefon("0850 211 0 111").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:128, Esentepe, Şişli/İstanbul").aciklama("İngiltere merkezli yabancı banka").build());
        bankaDtoList.add(BankaDto.builder().ad("ICBC Turkey Bank").telefon("444 00 50").postaKod(34398).merkezAdresIlce(new IlceDto(450L)).merkezAdres("Maslak Mah., AOS 55. Sok. No:2, Sarıyer/İstanbul").aciklama("Çin merkezli yabancı banka").build());
        bankaDtoList.add(BankaDto.builder().ad("ING Türkiye").telefon("0850 222 0 600").postaKod(34467).merkezAdresIlce(new IlceDto(450L)).merkezAdres("Reşitpaşa Mah., Eski Büyükdere Cad. No:8, Sarıyer/İstanbul").aciklama("Hollanda merkezli yabancı banka (mevduat)").build());
        bankaDtoList.add(BankaDto.builder().ad("Kuveyt Türk Katılım Bankası").telefon("444 0 123").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:129/1, Esentepe, Şişli/İstanbul").aciklama("Katılım bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("MUFG Bank Turkey A.Ş.").telefon("0212 384 20 00").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Levent 199, Büyükdere Cad. No:199, Şişli/İstanbul").aciklama("Japonya merkezli yabancı banka (kurumsal)").build());
        bankaDtoList.add(BankaDto.builder().ad("Odeabank").telefon("444 8 444").postaKod(34768).merkezAdresIlce(new IlceDto(457L)).merkezAdres("Saray Mah., Site Yolu Sk. No:3, Ümraniye/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("QNB Finansbank").telefon("0850 222 0 900").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Kristal Kule, Büyükdere Cad. No:215, Esentepe, Şişli/İstanbul").aciklama("Özel mevduat bankası (QNB grubu)").build());
        bankaDtoList.add(BankaDto.builder().ad("Şekerbank").telefon("0850 222 78 78").postaKod(34406).merkezAdresIlce(new IlceDto(444L)).merkezAdres("Emniyet Evleri Mah., Eski Büyükdere Cad. No:1/1, Kağıthane/İstanbul").aciklama("Özel mevduat bankası (KOBİ odaklı)").build());
        bankaDtoList.add(BankaDto.builder().ad("TEB (Türk Ekonomi Bankası)").telefon("0850 200 0 666").postaKod(34768).merkezAdresIlce(new IlceDto(457L)).merkezAdres("Saray Mah., Sokullu Mehmet Paşa Cad. No:7, Ümraniye/İstanbul").aciklama("Özel mevduat bankası (BNP Paribas ortaklığı)").build());
        bankaDtoList.add(BankaDto.builder().ad("Turkland Bank (T-Bank)").telefon("0850 222 82 65").postaKod(34394).merkezAdresIlce(new IlceDto(455L)).merkezAdres("Büyükdere Cad. No:156, Esentepe, Şişli/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Türkiye Finans Katılım Bankası").telefon("0850 222 22 44").postaKod(34768).merkezAdresIlce(new IlceDto(457L)).merkezAdres("İnkılap Mah., Sokullu Mehmet Paşa Cad. No:6/3, Ümraniye/İstanbul").aciklama("Katılım bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Türkiye İş Bankası").telefon("0850 724 0 724").postaKod(34330).merkezAdresIlce(new IlceDto(430L)).merkezAdres("İş Kuleleri, 34330 Levent, Beşiktaş/İstanbul").aciklama("Özel mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Vakıf Katılım Bankası").telefon("0850 202 1 250").postaKod(34768).merkezAdresIlce(new IlceDto(457L)).merkezAdres("İstanbul Finans Merkezi, Ümraniye/İstanbul").aciklama("Kamu katılım bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("VakıfBank").telefon("0850 222 0 724").postaKod(34768).merkezAdresIlce(new IlceDto(457L)).merkezAdres("İstanbul Finans Merkezi, A2 Blok, Ümraniye/İstanbul").aciklama("Kamu mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Yapı Kredi").telefon("0850 222 0 444").postaKod(34330).merkezAdresIlce(new IlceDto(430L)).merkezAdres("Yapı Kredi Plaza, Levent, Beşiktaş/İstanbul").aciklama("Özel mevduat bankası (Koç Grubu)").build());
        bankaDtoList.add(BankaDto.builder().ad("Ziraat Bankası").telefon("0850 258 00 00").postaKod(6510).merkezAdresIlce(new IlceDto(58L)).merkezAdres("Hacı Bayram Mah., Atatürk Bulvarı No:8, Altındağ/Ankara").aciklama("Kamu mevduat bankası").build());
        bankaDtoList.add(BankaDto.builder().ad("Ziraat Katılım Bankası").telefon("0850 220 50 00").postaKod(34758).merkezAdresIlce(new IlceDto(457L)).merkezAdres("İstanbul Finans Merkezi, Ümraniye/İstanbul").aciklama("Kamu katılım bankası").build());
        bankaService.tamaminiKaydet(bankaDtoList);
    }

    private void illeriOlustur() {
        List<IlDto> ilDtoList = new ArrayList<>();
        ilDtoList.add(IlDto.builder().ad("ADANA").plakaKodu(1).telefonKodu(322).build());
        ilDtoList.add(IlDto.builder().ad("ADIYAMAN").plakaKodu(2).telefonKodu(416).build());
        ilDtoList.add(IlDto.builder().ad("AFYONKARAHİSAR").plakaKodu(3).telefonKodu(272).build());
        ilDtoList.add(IlDto.builder().ad("AĞRI").plakaKodu(4).telefonKodu(472).build());
        ilDtoList.add(IlDto.builder().ad("AMASYA").plakaKodu(5).telefonKodu(358).build());
        ilDtoList.add(IlDto.builder().ad("ANKARA").plakaKodu(6).telefonKodu(312).build());
        ilDtoList.add(IlDto.builder().ad("ANTALYA").plakaKodu(7).telefonKodu(242).build());
        ilDtoList.add(IlDto.builder().ad("ARTVİN").plakaKodu(8).telefonKodu(466).build());
        ilDtoList.add(IlDto.builder().ad("AYDIN").plakaKodu(9).telefonKodu(256).build());
        ilDtoList.add(IlDto.builder().ad("BALIKESİR").plakaKodu(10).telefonKodu(266).build());
        ilDtoList.add(IlDto.builder().ad("BİLECİK").plakaKodu(11).telefonKodu(228).build());
        ilDtoList.add(IlDto.builder().ad("BİNGÖL").plakaKodu(12).telefonKodu(426).build());
        ilDtoList.add(IlDto.builder().ad("BİTLİS").plakaKodu(13).telefonKodu(434).build());
        ilDtoList.add(IlDto.builder().ad("BOLU").plakaKodu(14).telefonKodu(374).build());
        ilDtoList.add(IlDto.builder().ad("BURDUR").plakaKodu(15).telefonKodu(248).build());
        ilDtoList.add(IlDto.builder().ad("BURSA").plakaKodu(16).telefonKodu(224).build());
        ilDtoList.add(IlDto.builder().ad("ÇANAKKALE").plakaKodu(17).telefonKodu(286).build());
        ilDtoList.add(IlDto.builder().ad("ÇANKIRI").plakaKodu(18).telefonKodu(376).build());
        ilDtoList.add(IlDto.builder().ad("ÇORUM").plakaKodu(19).telefonKodu(364).build());
        ilDtoList.add(IlDto.builder().ad("DENİZLİ").plakaKodu(20).telefonKodu(258).build());
        ilDtoList.add(IlDto.builder().ad("DİYARBAKIR").plakaKodu(21).telefonKodu(412).build());
        ilDtoList.add(IlDto.builder().ad("EDİRNE").plakaKodu(22).telefonKodu(284).build());
        ilDtoList.add(IlDto.builder().ad("ELAZIĞ").plakaKodu(23).telefonKodu(424).build());
        ilDtoList.add(IlDto.builder().ad("ERZİNCAN").plakaKodu(24).telefonKodu(446).build());
        ilDtoList.add(IlDto.builder().ad("ERZURUM").plakaKodu(25).telefonKodu(442).build());
        ilDtoList.add(IlDto.builder().ad("ESKİŞEHİR").plakaKodu(26).telefonKodu(222).build());
        ilDtoList.add(IlDto.builder().ad("GAZİANTEP").plakaKodu(27).telefonKodu(342).build());
        ilDtoList.add(IlDto.builder().ad("GİRESUN").plakaKodu(28).telefonKodu(454).build());
        ilDtoList.add(IlDto.builder().ad("GÜMÜŞHANE").plakaKodu(29).telefonKodu(456).build());
        ilDtoList.add(IlDto.builder().ad("HAKKARİ").plakaKodu(30).telefonKodu(438).build());
        ilDtoList.add(IlDto.builder().ad("HATAY").plakaKodu(31).telefonKodu(326).build());
        ilDtoList.add(IlDto.builder().ad("ISPARTA").plakaKodu(32).telefonKodu(246).build());
        ilDtoList.add(IlDto.builder().ad("MERSİN").plakaKodu(33).telefonKodu(324).build());
        ilDtoList.add(IlDto.builder().ad("İSTANBUL").plakaKodu(34).telefonKodu(212).build());
        ilDtoList.add(IlDto.builder().ad("İZMİR").plakaKodu(35).telefonKodu(232).build());
        ilDtoList.add(IlDto.builder().ad("KARS").plakaKodu(36).telefonKodu(474).build());
        ilDtoList.add(IlDto.builder().ad("KASTAMONU").plakaKodu(37).telefonKodu(366).build());
        ilDtoList.add(IlDto.builder().ad("KAYSERİ").plakaKodu(38).telefonKodu(352).build());
        ilDtoList.add(IlDto.builder().ad("KIRKLARELİ").plakaKodu(39).telefonKodu(288).build());
        ilDtoList.add(IlDto.builder().ad("KIRŞEHİR").plakaKodu(40).telefonKodu(386).build());
        ilDtoList.add(IlDto.builder().ad("KOCAELİ").plakaKodu(41).telefonKodu(262).build());
        ilDtoList.add(IlDto.builder().ad("KONYA").plakaKodu(42).telefonKodu(332).build());
        ilDtoList.add(IlDto.builder().ad("KÜTAHYA").plakaKodu(43).telefonKodu(274).build());
        ilDtoList.add(IlDto.builder().ad("MALATYA").plakaKodu(44).telefonKodu(422).build());
        ilDtoList.add(IlDto.builder().ad("MANİSA").plakaKodu(45).telefonKodu(236).build());
        ilDtoList.add(IlDto.builder().ad("KAHRAMANMARAŞ").plakaKodu(46).telefonKodu(344).build());
        ilDtoList.add(IlDto.builder().ad("MARDİN").plakaKodu(47).telefonKodu(482).build());
        ilDtoList.add(IlDto.builder().ad("MUĞLA").plakaKodu(48).telefonKodu(252).build());
        ilDtoList.add(IlDto.builder().ad("MUŞ").plakaKodu(49).telefonKodu(436).build());
        ilDtoList.add(IlDto.builder().ad("NEVŞEHİR").plakaKodu(50).telefonKodu(384).build());
        ilDtoList.add(IlDto.builder().ad("NİĞDE").plakaKodu(51).telefonKodu(388).build());
        ilDtoList.add(IlDto.builder().ad("ORDU").plakaKodu(52).telefonKodu(452).build());
        ilDtoList.add(IlDto.builder().ad("RİZE").plakaKodu(53).telefonKodu(464).build());
        ilDtoList.add(IlDto.builder().ad("SAKARYA").plakaKodu(54).telefonKodu(264).build());
        ilDtoList.add(IlDto.builder().ad("SAMSUN").plakaKodu(55).telefonKodu(362).build());
        ilDtoList.add(IlDto.builder().ad("SİİRT").plakaKodu(56).telefonKodu(484).build());
        ilDtoList.add(IlDto.builder().ad("SİNOP").plakaKodu(57).telefonKodu(368).build());
        ilDtoList.add(IlDto.builder().ad("SİVAS").plakaKodu(58).telefonKodu(346).build());
        ilDtoList.add(IlDto.builder().ad("TEKİRDAĞ").plakaKodu(59).telefonKodu(282).build());
        ilDtoList.add(IlDto.builder().ad("TOKAT").plakaKodu(60).telefonKodu(356).build());
        ilDtoList.add(IlDto.builder().ad("TRABZON").plakaKodu(61).telefonKodu(462).build());
        ilDtoList.add(IlDto.builder().ad("TUNCELİ").plakaKodu(62).telefonKodu(428).build());
        ilDtoList.add(IlDto.builder().ad("ŞANLIURFA").plakaKodu(63).telefonKodu(414).build());
        ilDtoList.add(IlDto.builder().ad("UŞAK").plakaKodu(64).telefonKodu(276).build());
        ilDtoList.add(IlDto.builder().ad("VAN").plakaKodu(65).telefonKodu(432).build());
        ilDtoList.add(IlDto.builder().ad("YOZGAT").plakaKodu(66).telefonKodu(354).build());
        ilDtoList.add(IlDto.builder().ad("ZONGULDAK").plakaKodu(67).telefonKodu(372).build());
        ilDtoList.add(IlDto.builder().ad("AKSARAY").plakaKodu(68).telefonKodu(382).build());
        ilDtoList.add(IlDto.builder().ad("BAYBURT").plakaKodu(69).telefonKodu(458).build());
        ilDtoList.add(IlDto.builder().ad("KARAMAN").plakaKodu(70).telefonKodu(338).build());
        ilDtoList.add(IlDto.builder().ad("KIRIKKALE").plakaKodu(71).telefonKodu(318).build());
        ilDtoList.add(IlDto.builder().ad("BATMAN").plakaKodu(72).telefonKodu(488).build());
        ilDtoList.add(IlDto.builder().ad("ŞIRNAK").plakaKodu(73).telefonKodu(486).build());
        ilDtoList.add(IlDto.builder().ad("BARTIN").plakaKodu(74).telefonKodu(378).build());
        ilDtoList.add(IlDto.builder().ad("ARDAHAN").plakaKodu(75).telefonKodu(478).build());
        ilDtoList.add(IlDto.builder().ad("IĞDIR").plakaKodu(76).telefonKodu(476).build());
        ilDtoList.add(IlDto.builder().ad("YALOVA").plakaKodu(77).telefonKodu(226).build());
        ilDtoList.add(IlDto.builder().ad("KARABÜK").plakaKodu(78).telefonKodu(370).build());
        ilDtoList.add(IlDto.builder().ad("KİLİS").plakaKodu(79).telefonKodu(348).build());
        ilDtoList.add(IlDto.builder().ad("OSMANİYE").plakaKodu(80).telefonKodu(328).build());
        ilDtoList.add(IlDto.builder().ad("DÜZCE").plakaKodu(81).telefonKodu(380).build());

        ilService.tamaminiKaydet(ilDtoList);
    }

    private void ilceleriOlustur() {
        List<IlceDto> ilceDtoList = new ArrayList<>();
        // 01 Adana
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("ALADAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("CEYHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("ÇUKUROVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("FEKE").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("İMAMOĞLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("KARAİSALI").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("KARATAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("KOZAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("POZANTI").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("SAİMBEYLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("SARIÇAM").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("SEYHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("TUFANBEYLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("YUMURTALIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(1L).ad("YÜREĞİR").build());

        // 02 Adıyaman
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("BESNİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("ÇELİKHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("GERGER").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("GÖLBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("KAHTA").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("SAMSAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("SİNCİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(2L).ad("TUT").build());

        // 03 Afyonkarahisar
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("BAŞMAKÇI").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("BAYAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("BOLVADİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("ÇAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("ÇOBANLAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("DAZKIRI").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("DİNAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("EMİRDAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("EVCİLER").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("HOCALAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("İHSANİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("İSCEHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("KIZILÖREN").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("SANDIKLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("SİNANPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("SULTANDAĞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(3L).ad("ŞUHUT").build());

        // 04 Ağrı
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("DİYADİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("DOĞUBAYAZIT").build());
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("ELEŞKİRT").build());
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("HAMUR").build());
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("PATNOS").build());
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("TAŞLIÇAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(4L).ad("TUTAK").build());

        // 05 Amasya
        ilceDtoList.add(IlceDto.builder().ilId(5L).ad("GÖYNÜCEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(5L).ad("GÜMÜŞHACIKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(5L).ad("HAMAMÖZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(5L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(5L).ad("MERZİFON").build());
        ilceDtoList.add(IlceDto.builder().ilId(5L).ad("SULUOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(5L).ad("TAŞOVA").build());

        // 06 Ankara
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("ALTINDAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("AYAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("BALA").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("BEYPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("ÇAMLIDERE").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("ÇANKAYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("ÇUBUK").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("ELMADAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("ETİMESGUT").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("EVREN").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("GÖLBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("GÜDÜL").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("HAYMANA").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("KAHRAMANKAZAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("KALECİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("KEÇİÖREN").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("KIZILCAHAMAM").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("MAMAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("NALLIHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("POLATLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("PURSAKLAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("SİNCAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("ŞEREFLİKOÇHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(6L).ad("YENİMAHALLE").build());

        // 07 Antalya
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("AKSEKİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("AKSU").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("ALANYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("DEMRE").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("DÖŞEMEALTI").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("ELMALI").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("FİNİKE").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("GAZİPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("GÜNDOĞMUŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("İBRADI").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("KAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("KEMER").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("KEPEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("KONYAALTI").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("KORKUTELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("KUMLUCA").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("MANAVGAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("MURATPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(7L).ad("SERİK").build());

        // 08 Artvin
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("ARDANUÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("ARHAVİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("BORÇKA").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("HOPA").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("KEMALPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("MURGUL").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("ŞAVŞAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(8L).ad("YUSUFELİ").build());

        // 09 Aydın
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("BOZDOĞAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("BUHARKENT").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("ÇİNE").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("DİDİM").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("EFELER").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("GERMENCİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("İNCİRLİOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("KARACASU").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("KARPUZLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("KOÇARLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("KÖŞK").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("KUŞADASI").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("KUYUCAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("NAZİLLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("SÖKE").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("SULTANHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(9L).ad("YENİPAZAR").build());

        // 10 Balıkesir
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("ALTIEYLÜL").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("AYVALIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("BALYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("BANDIRMA").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("BİGADİÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("BURHANİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("DURSUNBEY").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("EDREMİT").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("ERDEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("GÖMEÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("GÖNEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("HAVRAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("İVRİNDİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("KARESİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("KEPSUT").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("MANYAS").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("MARMARA").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("SAVAŞTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("SINDIRGI").build());
        ilceDtoList.add(IlceDto.builder().ilId(10L).ad("SUSURLUK").build());

        // 11 Bilecik
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("BOZÜYÜK").build());
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("GÖLPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("İNHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("OSMANELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("PAZARYERİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("SÖĞÜT").build());
        ilceDtoList.add(IlceDto.builder().ilId(11L).ad("YENİPAZAR").build());

        // 12 Bingöl
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("ADAKLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("GENÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("KARLIOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("KİĞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("SOLHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("YAYLADERE").build());
        ilceDtoList.add(IlceDto.builder().ilId(12L).ad("YEDİSU").build());

        // 13 Bitlis
        ilceDtoList.add(IlceDto.builder().ilId(13L).ad("ADİLCEVAZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(13L).ad("AHLAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(13L).ad("GÜROYMAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(13L).ad("HİZAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(13L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(13L).ad("MUTKİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(13L).ad("TATVAN").build());

        // 14 Bolu
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("DÖRTDİVAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("GEREDE").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("GÖYNÜK").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("KIBRISCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("MENGEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("MUDURNU").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("SEBEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(14L).ad("YENİÇAĞA").build());

        // 15 Burdur
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("AĞLASUN").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("ALTINYAYLA").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("BUCAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("ÇAVDIR").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("ÇELTİKÇİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("GÖLHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("KARAMANLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("KEMER").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("TEFENNİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(15L).ad("YEŞİLOVA").build());

        // 16 Bursa
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("BÜYÜKORHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("GEMLİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("GÜRSU").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("HARMANCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("İNEGÖL").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("İZNİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("KARACABEY").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("KELES").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("KESTEL").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("MUDANYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("MUSTAFAKEMALPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("NİLÜFER").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("ORHANELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("ORHANGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("OSMANGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("YENİŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(16L).ad("YILDIRIM").build());

        // 17 Çanakkale
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("AYVACIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("BAYRAMİÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("BİGA").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("BOZCAADA").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("ÇAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("ECEABAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("EZİNE").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("GELİBOLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("GÖKÇEADA").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("LAPSEKİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(17L).ad("YENİCE").build());

        // 18 Çankırı
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("ATKARACALAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("BAYRAMÖREN").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("ÇERKEŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("ELDİVAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("ILGAZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("KIZILIRMAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("KORGUN").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("KURŞUNLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("ORTA").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("ŞABANÖZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(18L).ad("YAPRAKLI").build());

        // 19 Çorum
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("ALACA").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("BAYAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("BOĞAZKALE").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("DODURGA").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("İSKİLİP").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("KARGI").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("LAÇİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("MECİTÖZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("OĞUZLAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("ORTAKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("OSMANCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("SUNGURLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(19L).ad("UĞURLUDAĞ").build());

        // 20 Denizli
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("ACIPAYAM").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("BABADAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("BAKLAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("BEKİLLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("BEYAĞAÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("BOZKURT").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("BULDAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("ÇAL").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("ÇAMELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("ÇARDAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("ÇİVRİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("GÜNEY").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("HONAZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("KALE").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("MERKEZEFENDİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("PAMUKKALE").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("SARAYKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("SERİNHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(20L).ad("TAVAS").build());

        // 21 Diyarbakır
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("BAĞLAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("BİSMİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("ÇERMİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("ÇINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("ÇÜNGÜŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("DİCLE").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("EĞİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("ERGANİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("HANİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("HAZRO").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("KAYAPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("KOCAKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("KULP").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("LİCE").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("SİLVAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("SUR").build());
        ilceDtoList.add(IlceDto.builder().ilId(21L).ad("YENİŞEHİR").build());

        // 22 Edirne
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("ENEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("HAVSA").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("İPSALA").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("KEŞAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("LALAPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("MERİÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("SÜLOĞLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(22L).ad("UZUNKÖPRÜ").build());

        // 23 Elazığ
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("AĞIN").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("ALACAKAYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("ARICAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("BASKİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("KARAKOÇAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("KEBAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("KOVANCILAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("MADEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("PALU").build());
        ilceDtoList.add(IlceDto.builder().ilId(23L).ad("SİVRİCE").build());

        // 24 Erzincan
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("ÇAYIRLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("İLİÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("KEMAH").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("KEMALİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("OTLUKBELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("REFAHİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("TERCAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(24L).ad("ÜZÜMLÜ").build());

        // 25 Erzurum
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("AŞKALE").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("AZİZİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("ÇAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("HINIS").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("HORASAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("İSPİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("KARAÇOBAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("KARAYAZI").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("KÖPRÜKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("NARMAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("OLTU").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("OLUR").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("PALANDÖKEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("PASİNLER").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("PAZARYOLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("ŞENKAYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("TEKMAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("TORTUM").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("UZUNDERE").build());
        ilceDtoList.add(IlceDto.builder().ilId(25L).ad("YAKUTİYE").build());

        // 26 Eskişehir
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("ALPU").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("BEYLİKOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("ÇİFTELER").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("GÜNYÜZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("HAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("İNÖNÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("MAHMUDİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("MİHALGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("MİHALIÇÇIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("ODUNPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("SARICAKAYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("SEYİTGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("SİVRİHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(26L).ad("TEPEBAŞI").build());

        // 27 Gaziantep
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("ARABAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("İSLAHİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("KARKAMIŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("NİZİP").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("NURDAĞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("OĞUZELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("ŞAHİNBEY").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("ŞEHİTKAMİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(27L).ad("YAVUZELİ").build());

        // 28 Giresun
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("ALUCRA").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("BULANCAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("ÇAMOLUK").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("ÇANAKÇI").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("DERELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("DOĞANKENT").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("ESPİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("EYNESİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("GÖRELE").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("GÜCE").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("KEŞAP").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("PİRAZİZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("ŞEBİNKARAHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("TİREBOLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(28L).ad("YAĞLIDERE").build());

        // 29 Gümüşhane
        ilceDtoList.add(IlceDto.builder().ilId(29L).ad("KELKİT").build());
        ilceDtoList.add(IlceDto.builder().ilId(29L).ad("KÖSE").build());
        ilceDtoList.add(IlceDto.builder().ilId(29L).ad("KÜRTÜN").build());
        ilceDtoList.add(IlceDto.builder().ilId(29L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(29L).ad("ŞİRAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(29L).ad("TORUL").build());

        // 30 Hakkari
        ilceDtoList.add(IlceDto.builder().ilId(30L).ad("ÇUKURCA").build());
        ilceDtoList.add(IlceDto.builder().ilId(30L).ad("DERECİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(30L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(30L).ad("ŞEMDİNLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(30L).ad("YÜKSEKOVA").build());

        // 31 Hatay
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("ALTINÖZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("ANTAKYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("ARSUZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("BELEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("DEFNE").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("DÖRTYOL").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("ERZİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("HASSA").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("İSKENDERUN").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("KIRIKHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("KUMLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("PAYAS").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("REYHANLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("SAMANDAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(31L).ad("YAYLADAĞI").build());

        // 32 Isparta
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("AKSU").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("ATABEY").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("EĞİRDİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("GELENDOST").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("GÖNEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("KEÇİBORLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("SENİRKENT").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("SÜTÇÜLER").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("ŞARKİKARAAĞAÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("ULUBORLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("YALVAÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(32L).ad("YENİŞARBADEMLİ").build());

        // 33 Mersin
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("AKDENİZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("ANAMUR").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("AYDINCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("BOZYAZI").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("ÇAMLIYAYLA").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("ERDEMLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("GÜLNAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("MEZİTLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("MUT").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("SİLİFKE").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("TARSUS").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("TOROSLAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(33L).ad("YENİŞEHİR").build());

        // 34 İstanbul
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ADALAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ARNAVUTKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ATAŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("AVCILAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BAĞCILAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BAHÇELİEVLER").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BAKIRKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BAŞAKŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BAYRAMPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BEŞİKTAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BEYKOZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BEYLİKDÜZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BEYOĞLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("BÜYÜKÇEKMECE").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ÇATALCA").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ÇEKMEKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ESENLER").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ESENYURT").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("EYÜPSULTAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("FATİH").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("GAZİOSMANPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("GÜNGÖREN").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("KADIKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("KAĞITHANE").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("KARTAL").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("KÜÇÜKÇEKMECE").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("MALTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("PENDİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("SANCAKTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("SARIYER").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("SİLİVRİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("SULTANBEYLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("SULTANGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ŞİLE").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ŞİŞLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("TUZLA").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ÜMRANİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ÜSKÜDAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(34L).ad("ZEYTİNBURNU").build());

        // 35 İzmir
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("ALİAĞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("BALÇOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("BAYINDIR").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("BAYRAKLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("BERGAMA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("BEYDAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("BORNOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("BUCA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("ÇEŞME").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("ÇİĞLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("DİKİLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("FOÇA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("GAZİEMİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("GÜZELBAHÇE").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("KARABAĞLAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("KARABURUN").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("KARŞIYAKA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("KEMALPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("KINIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("KİRAZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("KONAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("MENDERES").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("MENEMEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("NARLIDERE").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("ÖDEMİŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("SEFERİHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("SELÇUK").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("TİRE").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("TORBALI").build());
        ilceDtoList.add(IlceDto.builder().ilId(35L).ad("URLA").build());

        // 36 Kars
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("AKYAKA").build());
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("ARPAÇAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("DİGOR").build());
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("KAĞIZMAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("SARIKAMIŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("SELİM").build());
        ilceDtoList.add(IlceDto.builder().ilId(36L).ad("SUSUZ").build());

        // 37 Kastamonu
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("ABANA").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("AĞLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("ARAÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("AZDAVAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("BOZKURT").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("CİDE").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("ÇATALZEYTİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("DADAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("DEVREKANİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("DOĞANYURT").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("HANÖNÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("İHSANGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("İNEBOLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("KÜRE").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("PINARBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("ŞENPAZAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("SEYDİLER").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("TAŞKÖPRÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(37L).ad("TOSYA").build());

        // 38 Kayseri
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("AKKIŞLA").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("BÜNYAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("DEVELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("FELAHİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("HACILAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("İNCESU").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("KOCASİNAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("MELİKGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("ÖZVATAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("PINARBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("SARIOĞLAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("SARIZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("TALAS").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("TOMARZA").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("YAHYALI").build());
        ilceDtoList.add(IlceDto.builder().ilId(38L).ad("YEŞİLHİSAR").build());

        // 39 Kırklareli
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("BABAESKİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("DEMİRKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("KOFÇAZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("LÜLEBURGAZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("PEHLİVANKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("PINARHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(39L).ad("VİZE").build());

        // 40 Kırşehir
        ilceDtoList.add(IlceDto.builder().ilId(40L).ad("AKÇAKENT").build());
        ilceDtoList.add(IlceDto.builder().ilId(40L).ad("AKPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(40L).ad("BOZTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(40L).ad("ÇİÇEKDAĞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(40L).ad("KAMAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(40L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(40L).ad("MUCUR").build());

        // 41 Kocaeli
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("BAŞİSKELE").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("ÇAYIROVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("DARICA").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("DERİNCE").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("DİLOVASI").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("GEBZE").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("GÖLCÜK").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("İZMİT").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("KANDIRA").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("KARAMÜRSEL").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("KARTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(41L).ad("KÖRFEZ").build());

        // 42 Konya
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("AHIRLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("AKÖREN").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("AKŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("ALTINEKİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("BEYŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("BOZKIR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("CİHANBEYLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("ÇELTİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("ÇUMRA").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("DERBENT").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("DEREBUCAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("DOĞANHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("EMİRGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("EREĞLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("GÜNEYSINIR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("HADİM").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("HALKAPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("HÜYÜK").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("ILGIN").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("KADINHANI").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("KARAPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("KARATAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("KULU").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("MERAM").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("SARAYÖNÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("SELÇUKLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("SEYDİŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("TAŞKENT").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("TUZLUKÇU").build());
        ilceDtoList.add(IlceDto.builder().ilId(42L).ad("YALIHÜYÜK").build());

        // 43 Kütahya
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("ALTINTAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("ASLANAPA").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("ÇAVDARHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("DOMANİÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("DUMLUPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("EMET").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("GEDİZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("HİSARCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("PAZARLAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("SİMAV").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("ŞAPHANE").build());
        ilceDtoList.add(IlceDto.builder().ilId(43L).ad("TAVŞANLI").build());

        // 44 Malatya
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("AKÇADAĞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("ARAPGİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("ARGUVAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("BATTALGAZİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("DARENDE").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("DOĞANŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("DOĞANYOL").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("HEKİMHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("KALE").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("KULUNCAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("PÜTÜRGE").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("YAZIHAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(44L).ad("YEŞİLYURT").build());

        // 45 Manisa
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("AHMETLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("AKHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("ALAŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("DEMİRCİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("GÖLMARMARA").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("GÖRDES").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("KIRKAĞAÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("KÖPRÜBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("KULA").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("SALİHLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("SARIGÖL").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("SARUHANLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("SELENDİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("SOMA").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("ŞEHZADELER").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("TURGUTLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(45L).ad("YUNUSEMRE").build());

        // 46 Kahramanmaraş
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("AFŞİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("ANDIRIN").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("ÇAĞLAYANCERİT").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("DULKADİROĞLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("EKİNÖZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("ELBİSTAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("GÖKSUN").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("NURHAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("ONİKİŞUBAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("PAZARCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(46L).ad("TÜRKOĞLU").build());

        // 47 Mardin
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("ARTUKLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("DARGEÇİT").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("DERİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("KIZILTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("MAZIDAĞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("MİDYAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("NUSAYBİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("ÖMERLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("SAVUR").build());
        ilceDtoList.add(IlceDto.builder().ilId(47L).ad("YEŞİLLİ").build());

        // 48 Muğla
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("BODRUM").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("DALAMAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("DATÇA").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("FETHİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("KAVAKLIDERE").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("KÖYCEĞİZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("MARMARİS").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("MENTEŞE").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("MİLAS").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("ORTACA").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("SEYDİKEMER").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("ULA").build());
        ilceDtoList.add(IlceDto.builder().ilId(48L).ad("YATAĞAN").build());

        // 49 Muş
        ilceDtoList.add(IlceDto.builder().ilId(49L).ad("BULANIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(49L).ad("HASKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(49L).ad("KORKUT").build());
        ilceDtoList.add(IlceDto.builder().ilId(49L).ad("MALAZGİRT").build());
        ilceDtoList.add(IlceDto.builder().ilId(49L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(49L).ad("VARTO").build());

        // 50 Nevşehir
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("ACIGÖL").build());
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("AVANOS").build());
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("DERİNKUYU").build());
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("GÜLŞEHİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("HACIBEKTAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("KOZAKLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(50L).ad("ÜRGÜP").build());

        // 51 Niğde
        ilceDtoList.add(IlceDto.builder().ilId(51L).ad("ALTUNHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(51L).ad("BOR").build());
        ilceDtoList.add(IlceDto.builder().ilId(51L).ad("ÇAMARDI").build());
        ilceDtoList.add(IlceDto.builder().ilId(51L).ad("ÇİFTLİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(51L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(51L).ad("ULUKIŞLA").build());

        // 52 Ordu
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("AKKUŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("ALTINORDU").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("AYBASTI").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("ÇAMAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("ÇATALPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("ÇAYBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("FATSA").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("GÖLKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("GÜLYALI").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("GÜRGENTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("İKİZCE").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("KABADÜZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("KABATAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("KORGAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("KUMRU").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("MESUDİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("PERŞEMBE").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("ULUBEY").build());
        ilceDtoList.add(IlceDto.builder().ilId(52L).ad("ÜNYE").build());

        // 53 Rize
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("ARDEŞEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("ÇAMLIHEMŞİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("ÇAYELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("DEREPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("FINDIKLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("GÜNEYSU").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("HEMŞİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("İKİZDERE").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("KALKANDERE").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(53L).ad("PAZAR").build());

        // 54 Sakarya
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("ADAPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("AKYAZI").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("ARİFİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("ERENLER").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("FERİZLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("GEYVE").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("HENDEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("KARAPÜRÇEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("KARASU").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("KAYNARCA").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("KOCAALİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("PAMUKOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("SAPANCA").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("SERDİVAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("SÖĞÜTLÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(54L).ad("TARAKLI").build());

        // 55 Samsun
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("ALAÇAM").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("ASARCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("ATAKUM").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("AYVACIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("BAFRA").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("CANİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("ÇARŞAMBA").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("HAVZA").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("İLKADIM").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("KAVAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("LADİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("SALIPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("TEKKEKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("TERME").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("VEZİRKÖPRÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(55L).ad("YAKAKENT").build());

        // 56 Siirt
        ilceDtoList.add(IlceDto.builder().ilId(56L).ad("BAYKAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(56L).ad("ERUH").build());
        ilceDtoList.add(IlceDto.builder().ilId(56L).ad("KURTALAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(56L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(56L).ad("PERVARİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(56L).ad("ŞİRVAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(56L).ad("TİLLO").build());

        // 57 Sinop
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("AYANCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("BOYABAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("DİKMEN").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("DURAĞAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("ERFELEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("GERZE").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("SARAYDÜZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(57L).ad("TÜRKELİ").build());

        // 58 Sivas
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("AKINCILAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("ALTINYAYLA").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("DİVRİĞİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("DOĞANŞAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("GEMEREK").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("GÖLOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("GÜRÜN").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("HAFİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("İMRANLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("KANGAL").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("KOYULHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("SUŞEHRİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("ŞARKIŞLA").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("ULAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("YILDIZELİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(58L).ad("ZARA").build());

        // 59 Tekirdağ
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("ÇERKEZKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("ÇORLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("ERGENE").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("HAYRABOLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("KAPAKLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("MALKARA").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("MARMARAEREĞLİSİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("MURATLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("SARAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("SÜLEYMANPAŞA").build());
        ilceDtoList.add(IlceDto.builder().ilId(59L).ad("ŞARKÖY").build());

        // 60 Tokat
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("ALMUS").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("ARTOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("BAŞÇİFTLİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("ERBAA").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("NİKSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("PAZAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("REŞADİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("SULUSARAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("TURHAL").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("YEŞİLYURT").build());
        ilceDtoList.add(IlceDto.builder().ilId(60L).ad("ZİLE").build());

        // 61 Trabzon
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("AKÇAABAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("ARAKLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("ARSİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("BEŞİKDÜZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("ÇARŞIBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("ÇAYKARA").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("DERNEKPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("DÜZKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("HAYRAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("KÖPRÜBAŞI").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("MAÇKA").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("OF").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("ORTAHİSAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("SÜRMENE").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("ŞALPAZARI").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("TONYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("VAKFIKEBİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(61L).ad("YOMRA").build());

        // 62 Tunceli
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("ÇEMİŞGEZEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("HOZAT").build());
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("MAZGİRT").build());
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("NAZIMİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("OVACIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("PERTEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(62L).ad("PÜLÜMÜR").build());

        // 63 Şanlıurfa
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("AKÇAKALE").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("BİRECİK").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("BOZOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("CEYLANPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("EYYÜBİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("HALFETİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("HALİLİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("HARRAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("HİLVAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("KARAKÖPRÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("SİVEREK").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("SURUÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(63L).ad("VİRANŞEHİR").build());

        // 64 Uşak
        ilceDtoList.add(IlceDto.builder().ilId(64L).ad("BANAZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(64L).ad("EŞME").build());
        ilceDtoList.add(IlceDto.builder().ilId(64L).ad("KARAHALLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(64L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(64L).ad("SİVASLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(64L).ad("ULUBEY").build());

        // 65 Van
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("BAHÇESARAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("BAŞKALE").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("ÇALDIRAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("ÇATAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("EDREMİT").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("ERCİŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("GEVAŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("GÜRPINAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("İPEKYOLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("MURADİYE").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("ÖZALP").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("SARAY").build());
        ilceDtoList.add(IlceDto.builder().ilId(65L).ad("TUŞBA").build());

        // 66 Yozgat
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("AKDAĞMADENİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("AYDINCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("BOĞAZLIYAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("ÇANDIR").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("ÇAYIRALAN").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("ÇEKEREK").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("KADIŞEHRİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("SARAYKENT").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("SARIKAYA").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("SORGUN").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("ŞEFAATLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("YENİFAKILI").build());
        ilceDtoList.add(IlceDto.builder().ilId(66L).ad("YERKÖY").build());

        // 67 Zonguldak
        ilceDtoList.add(IlceDto.builder().ilId(67L).ad("ALAPLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(67L).ad("ÇAYCUMA").build());
        ilceDtoList.add(IlceDto.builder().ilId(67L).ad("DEVREK").build());
        ilceDtoList.add(IlceDto.builder().ilId(67L).ad("EREĞLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(67L).ad("GÖKÇEBEY").build());
        ilceDtoList.add(IlceDto.builder().ilId(67L).ad("KİLİMLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(67L).ad("MERKEZ").build());

        // 68 Aksaray
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("AĞAÇÖREN").build());
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("ESKİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("GÜLAĞAÇ").build());
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("GÜZELYURT").build());
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("ORTAKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("SARIYAHŞİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(68L).ad("SULTANHANI").build());

        // 69 Bayburt
        ilceDtoList.add(IlceDto.builder().ilId(69L).ad("AYDINTEPE").build());
        ilceDtoList.add(IlceDto.builder().ilId(69L).ad("DEMİRÖZÜ").build());
        ilceDtoList.add(IlceDto.builder().ilId(69L).ad("MERKEZ").build());

        // 70 Karaman
        ilceDtoList.add(IlceDto.builder().ilId(70L).ad("AYRANCI").build());
        ilceDtoList.add(IlceDto.builder().ilId(70L).ad("BAŞYAYLA").build());
        ilceDtoList.add(IlceDto.builder().ilId(70L).ad("ERMENEK").build());
        ilceDtoList.add(IlceDto.builder().ilId(70L).ad("KAZIMKARABEKİR").build());
        ilceDtoList.add(IlceDto.builder().ilId(70L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(70L).ad("SARIVELİLER").build());

        // 71 Kırıkkale
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("BAHŞILI").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("BALIŞEYH").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("ÇELEBİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("DELİCE").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("KARAKEÇİLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("KESKİN").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("SULAKYURT").build());
        ilceDtoList.add(IlceDto.builder().ilId(71L).ad("YAHŞİHAN").build());

        // 72 Batman
        ilceDtoList.add(IlceDto.builder().ilId(72L).ad("BEŞİRİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(72L).ad("GERCÜŞ").build());
        ilceDtoList.add(IlceDto.builder().ilId(72L).ad("HASANKEYF").build());
        ilceDtoList.add(IlceDto.builder().ilId(72L).ad("KOZLUK").build());
        ilceDtoList.add(IlceDto.builder().ilId(72L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(72L).ad("SASON").build());

        // 73 Şırnak
        ilceDtoList.add(IlceDto.builder().ilId(73L).ad("BEYTÜŞŞEBAP").build());
        ilceDtoList.add(IlceDto.builder().ilId(73L).ad("CİZRE").build());
        ilceDtoList.add(IlceDto.builder().ilId(73L).ad("GÜÇLÜKONAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(73L).ad("İDİL").build());
        ilceDtoList.add(IlceDto.builder().ilId(73L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(73L).ad("SİLOPİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(73L).ad("ULUDERE").build());

        // 74 Bartın
        ilceDtoList.add(IlceDto.builder().ilId(74L).ad("AMASRA").build());
        ilceDtoList.add(IlceDto.builder().ilId(74L).ad("KURUCAŞİLE").build());
        ilceDtoList.add(IlceDto.builder().ilId(74L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(74L).ad("ULUS").build());

        // 75 Ardahan
        ilceDtoList.add(IlceDto.builder().ilId(75L).ad("ÇILDIR").build());
        ilceDtoList.add(IlceDto.builder().ilId(75L).ad("DAMAL").build());
        ilceDtoList.add(IlceDto.builder().ilId(75L).ad("GÖLE").build());
        ilceDtoList.add(IlceDto.builder().ilId(75L).ad("HANAK").build());
        ilceDtoList.add(IlceDto.builder().ilId(75L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(75L).ad("POSOF").build());

        // 76 Iğdır
        ilceDtoList.add(IlceDto.builder().ilId(76L).ad("ARALIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(76L).ad("KARAKOYUNLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(76L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(76L).ad("TUZLUCA").build());

        // 77 Yalova
        ilceDtoList.add(IlceDto.builder().ilId(77L).ad("ALTINOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(77L).ad("ARMUTLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(77L).ad("ÇINARCIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(77L).ad("ÇİFTLİKKÖY").build());
        ilceDtoList.add(IlceDto.builder().ilId(77L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(77L).ad("TERMAL").build());

        // 78 Karabük
        ilceDtoList.add(IlceDto.builder().ilId(78L).ad("EFLANİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(78L).ad("ESKİPAZAR").build());
        ilceDtoList.add(IlceDto.builder().ilId(78L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(78L).ad("OVACIK").build());
        ilceDtoList.add(IlceDto.builder().ilId(78L).ad("SAFRANBOLU").build());
        ilceDtoList.add(IlceDto.builder().ilId(78L).ad("YENİCE").build());

        // 79 Kilis
        ilceDtoList.add(IlceDto.builder().ilId(79L).ad("ELBEYLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(79L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(79L).ad("MUSABEYLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(79L).ad("POLATELİ").build());

        // 80 Osmaniye
        ilceDtoList.add(IlceDto.builder().ilId(80L).ad("BAHÇE").build());
        ilceDtoList.add(IlceDto.builder().ilId(80L).ad("DÜZİÇİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(80L).ad("HASANBEYLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(80L).ad("KADİRLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(80L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(80L).ad("SUMBAS").build());
        ilceDtoList.add(IlceDto.builder().ilId(80L).ad("TOPRAKKALE").build());

        // 81 Düzce
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("AKÇAKOCA").build());
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("CUMAYERİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("ÇİLİMLİ").build());
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("GÖLYAKA").build());
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("GÜMÜŞOVA").build());
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("KAYNAŞLI").build());
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("MERKEZ").build());
        ilceDtoList.add(IlceDto.builder().ilId(81L).ad("YIĞILCA").build());

        ilceService.tamaminiKaydet(ilceDtoList);
    }

    private void siteKatBlokDaireOlustur() {
        var site = Site.builder()
                .ad("Sitera Park")
                .kisaAd("SPARK")
                .build();
        var adres = new AdresBilgisi("Bahçe Sk. 1", null, "Merkez", "34000", "Üsküdar", "İstanbul");
        site.setAdres(adres);
        siteRepository.save(site);

        // ---------- En az 7 Blok (A..G) ----------
        List<Blok> bloklar = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            char kod = (char) ('A' + i);
            Blok b = Blok.builder()
                    .site(site)
                    .kod(String.valueOf(kod))
                    .ad(kod + " Blok")
                    .build();
            bloklar.add(blokRepository.save(b));
        }

        // ---------- En az 7 Kat (A Blok için 0..6) ----------
        Blok blokA = bloklar.get(0);
        List<Kat> katlar = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Kat k = Kat.builder()
                    .blok(blokA)
                    .siraNo(i) // 0: zemin
                    .ad(i == 0 ? "Zemin" : i + ". Kat")
                    .build();
            katlar.add(katRepository.save(k));
        }

        // ---------- En az 7 Daire (A Blokta, katlar 0..6 üzerinde no: 1..7) ----------
        List<Daire> daireler = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Daire d = Daire.builder()
                    .kat(katlar.get(i))
                    .blok(blokA)
                    .no(String.valueOf(i + 1))
                    .bagimsizBolumNo("BB" + (100 + i))
                    .tip(i % 2 == 0 ? DaireTipi.KONUT : DaireTipi.OFIS)
                    .metrekare(80 + i * 10)
                    .odaSayisi(i % 2 == 0 ? 2 : 3)
                    .build();
            daireler.add(daireRepository.save(d));
        }

        // ---------- En az 7 + 7 Kişi (7 mal sahibi, 7 kiracı) ----------
        List<Kisi> sahipler = new ArrayList<>();
        List<Kisi> kiracilar = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Kisi s = Kisi.builder()
                    .ad("Sahip" + (i + 1))
                    .soyad("Örnek")
                    .tcKimlikNo(String.format("100000000%02d", i)) // örnek
                    .iletisim(new IletisimBilgisi("sahip" + (i + 1) + "@sitera.local", "050000000" + i))
                    .build();
            sahipler.add(kisiRepository.save(s));

            Kisi k = Kisi.builder()
                    .ad("Kiraci" + (i + 1))
                    .soyad("Örnek")
                    .tcKimlikNo(String.format("200000000%02d", i))
                    .iletisim(new IletisimBilgisi("kiraci" + (i + 1) + "@sitera.local", "050000001" + i))
                    .build();
            kiracilar.add(kisiRepository.save(k));
        }

        // ---------- En az 7 Araç (sahiplere ait) ----------
        for (int i = 0; i < 7; i++) {
            Arac a = Arac.builder()
                    .plaka(String.format("34 SIT %03d", i + 1))
                    .kisi(sahipler.get(i))
                    .yil(2000)
                    .renk("Kırmızı")
                    .marka("Test Marka")
                    .model("Test Model")
                    .build();
            aracRepository.save(a);
        }

        // ---------- En az 7 Mülk Sahipliği (dairelerin tamamı için %100) ----------
        List<MulkSahipligi> sahiplikler = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            MulkSahipligi ms = MulkSahipligi.builder()
                    .daire(daireler.get(i))
                    .kisi(sahipler.get(i))
                    .payOrani(100)
                    .baslangicTarihi(LocalDate.of(2024, 1, 1))
                    .bitisTarihi(null) // halen malik
                    .build();
            sahiplikler.add(mulkSahipligiRepository.save(ms));
        }

        // ---------- En az 7 Kira Sözleşmesi (her daire için bir kiracı) ----------
        List<KiraSozlesmesi> sozlesmeler = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            KiraSozlesmesi ks = KiraSozlesmesi.builder()
                    .daire(daireler.get(i))
                    .kiraci(kiracilar.get(i))
                    .baslangicTarihi(LocalDate.of(2025, 1, 1))
                    .bitisTarihi(null) // devam ediyor
                    .kiraBedeli(BigDecimal.valueOf(15000 + i * 1000L))
                    .depozito(BigDecimal.valueOf(15000))
                    .paraBirimi("TRY")
                    .build();
            sozlesmeler.add(kiraSozlesmesiRepository.save(ks));
        }

        // ---------- En az 7 Daire Oturum (kiracılar oturuyor; kira sözleşmesine bağlı) ----------
        for (int i = 0; i < 7; i++) {
            DaireOturum o = DaireOturum.builder()
                    .daire(daireler.get(i))
                    .kisi(kiracilar.get(i))
                    .oturumTipi(OturumTipi.KIRACI)
                    .baslangicTarihi(LocalDate.of(2025, 1, 1))
                    .bitisTarihi(null)
                    .kiraSozlesmesi(sozlesmeler.get(i))
                    .build();
            daireOturumRepository.save(o);
        }

    }

    private void hesapVeDigerleriniOlustur() {
        log.info("=== Demo veri yükleyici başlıyor ===");

        // 1) Hesaplar: Kasa, Gelir, Daire-10, Daire-11 (yoksa oluştur)
        HesapDto kasa = hesapService.hesapGetirVeyaOlustur(HesapTipi.KASA, null, "Merkez Kasa");
        HesapDto gelir = hesapService.hesapGetirVeyaOlustur(HesapTipi.GELIR, null, "Aidat Gelirleri");
        HesapDto daire10 = hesapService.hesapGetirVeyaOlustur(HesapTipi.DAIRE, 101L, "A Blok Daire 10");
        HesapDto daire11 = hesapService.hesapGetirVeyaOlustur(HesapTipi.DAIRE, 102L, "A Blok Daire 11");

        log.info("Hesaplar hazır: kasa={}, gelir={}, daire10={}, daire11={}",
                kasa.getId(), gelir.getId(), daire10.getId(), daire11.getId());

        // 2) Aidat planı: "Standart Plan" (yoksa oluştur)
        AidatPlani plan = planHazirla("Standart Plan",
                HesaplamaTipi.SABIT, new BigDecimal("1500.00"),
                ParaBirimi.TRY, 10, new BigDecimal("0.0005"));

        log.info("Aidat planı hazır: {} (id={})", plan.getAd(), plan.getId());

        // 3) İçinde bulunulan dönem için aidat fişleri (idempotent)
        YearMonth donem = YearMonth.now(IST);
        aidatPlaniService.tumPlanlarIcinAidatOlustur(donem);
        log.info("Aidat fişleri oluşturuldu: donem={}", donem);

        // 4) Daire-10'dan 1000 TL tahsilat (örnek)
        muhasebeService.tahsilatGir(daire10.getId(), kasa.getId(), new BigDecimal("1000.00"), "Başlangıç tahsilat (demo)");
        log.info("Tahsilat girildi: daire10 -> kasa, 1000.00 TL");

        // 5) Hızlı bakiye özetleri
        var bDaire10 = hesapService.bakiye(daire10.getId());
        var bKasa = hesapService.bakiye(kasa.getId());
        var bGelir = hesapService.bakiye(gelir.getId());

        log.info("Bakiye özet — Daire10: {} TL, Kasa: {} TL, Gelir: {} TL", bDaire10, bKasa, bGelir);

        log.info("=== Demo veri yükleyici tamam ===");
    }

    private void anketKararOrnekleriOlustur() {
        // KARAR örneği
        var karar = Anket.builder()
                .baslik("Site giriş güvenlik prosedürü güncellemesi")
                .aciklama("Yeni prosedürün kabulü için oylama")
                .tur(AnketTuru.KARAR)
                .anonimMi(false)
                .baslangicTarihi(java.time.LocalDateTime.now().minusDays(1))
                .bitisTarihi(java.time.LocalDateTime.now().plusDays(7))
                .build();
        anketRepository.save(karar);

        // ANKET örneği
        var anket = Anket.builder()
                .baslik("Otopark çizgilerinin rengi")
                .aciklama("Sarı mı beyaz mı?")
                .tur(AnketTuru.ANKET)
                .anonimMi(true)
                .baslangicTarihi(java.time.LocalDateTime.now().minusDays(1))
                .bitisTarihi(java.time.LocalDateTime.now().plusDays(7))
                .build();

        anket.setSecenekler(new ArrayList<>());
        anket.getSecenekler().add(AnketSecenek.builder().anket(anket).metin("Sarı").siraNo(1).build());
        anket.getSecenekler().add(AnketSecenek.builder().anket(anket).metin("Beyaz").siraNo(2).build());

        anketRepository.save(anket);
    }

    private void kargoDataOlustur() {
        log.info("Kargo dummy verileri yükleniyor...");
        kargoService.olustur(new KargoOlusturGuncelleDto("Yurtiçi", "YK123456789TR", 101L, "Ahmet Yılmaz", "5300000000", "Gece teslim"));
        kargoService.olustur(new KargoOlusturGuncelleDto("Aras", "AR987654321TR", 202L, "Zeynep Demir", null, "Güvenliğe bırakılacak"));
        kargoService.olustur(new KargoOlusturGuncelleDto("MNG", "MN1122334455TR", 101L, "Ali Can", null, null));
    }

    /**
     * Aynı isimli aktif plan varsa onu döner; yoksa oluşturur.
     */
    private AidatPlani planHazirla(String ad,
                                   HesaplamaTipi hesaplamaTipi,
                                   BigDecimal tutar,
                                   ParaBirimi paraBirimi,
                                   int vadeGunu,
                                   BigDecimal gecikmeOraniGunluk) {
        // Aktif planı isme göre kaba kontrol (isim tekil değilse ilkini al)
        Optional<AidatPlani> mevcut = aidatPlaniRepository.findAll().stream()
                .filter(p -> p.getDurum() != Durum.SILINDI && ad.equalsIgnoreCase(p.getAd()))
                .findFirst();

        if (mevcut.isPresent()) return mevcut.get();

        AidatPlaniDto dto = AidatPlaniDto.builder()
                .ad(ad)
                .hesaplamaTipi(hesaplamaTipi)
                .tutar(tutar)
                .paraBirimi(paraBirimi)
                .vadeGunu(vadeGunu)
                .gecikmeOraniGunluk(gecikmeOraniGunluk)
                .build();

        return aidatPlaniMapper.toEntity(aidatPlaniService.create(dto));
    }

}
