package com.gtombul.siteyonetimi.service.dosya;

import com.gtombul.siteyonetimi.model.dosya.DepolamaTipi;
import com.gtombul.siteyonetimi.model.dosya.Dosya;
import com.gtombul.siteyonetimi.repository.dosya.DosyaRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DosyaServiceImpl implements DosyaService {

    private final DosyaRepository dosyaRepository;
    private final Map<DepolamaTipi, DepolamaService> depolamaServisleri;
    private DepolamaService aktifDepolamaServisi;

    @Value("${dosya.depolama.aktif-servis:LOCAL}")
    private DepolamaTipi aktifDepolamaTipi;

    public DosyaServiceImpl(DosyaRepository dosyaRepository, List<DepolamaService> servisler) {
        this.dosyaRepository = dosyaRepository;
        // Gelen tüm DepolamaService bean'lerini DepolamaTipi'ne göre bir Map'e yerleştirir.
        this.depolamaServisleri = servisler.stream()
                .collect(Collectors.toMap(DepolamaService::getDepolamaTipi, Function.identity()));
        // 2. Aktif servisi belirleme işini BURADAN KALDIRIYORUZ.
        // ESKİ HATALI KOD: this.aktifDepolamaServisi = this.depolamaServisleri.get(this.aktifDepolamaTipi);
    }

    /**
     * Bu metot, tüm @Value ve @Autowired enjeksiyonları tamamlandıktan sonra
     * Spring tarafından otomatik olarak çalıştırılır.
     * Aktif servisi belirlemek için en doğru yer burasıdır.
     */
    @PostConstruct
    public void initAktifServis() {
        this.aktifDepolamaServisi = this.depolamaServisleri.get(this.aktifDepolamaTipi);

        // Hata ayıklama için sağlam bir kontrol ekleyelim:
        if (this.aktifDepolamaServisi == null) {
            String hataMesaji = String.format("Aktif depolama servisi (%s) için bir bean bulunamadı! " +
                            "Mevcut servisler: %s. Lütfen 'application.properties' dosyasını ve " +
                            "DepolamaServisi implementasyonlarınızı (@Service anotasyonu) kontrol edin.",
                    this.aktifDepolamaTipi, this.depolamaServisleri.keySet());
            log.error(hataMesaji);
            throw new IllegalStateException(hataMesaji);
        }

        log.info("Aktif dosya depolama servisi başarıyla ayarlandı: {}", this.aktifDepolamaTipi);
    }

    @Override
    @Transactional
    public Dosya kaydet(MultipartFile dosya, String modulAdi) {
        // 1. Meta verileri oluştur
        String orijinalAd = dosya.getOriginalFilename();
        String uzanti = FilenameUtils.getExtension(orijinalAd);
        String benzersizAd = UUID.randomUUID() + "." + uzanti;
        LocalDate today = LocalDate.now();
        String dosyaYolu = String.format("%s/%d/%d", modulAdi, today.getYear(), today.getMonthValue());

        // 2. Fiziksel olarak kaydetmek için ilgili stratejiyi kullan
        aktifDepolamaServisi.kaydet(dosya, dosyaYolu, benzersizAd);

        // 3. Veritabanına meta veriyi kaydet
        Dosya dosyaEntity = Dosya.builder()
                .orijinalAdi(orijinalAd)
                .benzersizAdi(benzersizAd)
                .dosyaYolu(dosyaYolu)
                .dosyaTipi(dosya.getContentType())
                .boyut(dosya.getSize())
                .depolamaTipi(aktifDepolamaTipi)
                .build();

        return dosyaRepository.save(dosyaEntity);
    }

    @Override
    public Resource dosyaGetir(UUID uuid) {
        // Bu metodun implementasyonu gerekecek (Önceki önerilerde vardı)
        Dosya dosya = metaVeriGetir(uuid);
        DepolamaService servis = depolamaServisleri.get(dosya.getDepolamaTipi());
        return servis.yukle(dosya.getDosyaYolu(), dosya.getBenzersizAdi());
    }

    @Override
    public Dosya metaVeriGetir(UUID uuid) {
        return dosyaRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Dosya bulunamadı: uuid=" + uuid));
    }

    @Override
    @Transactional
    public void sil(UUID uuid) {
        Dosya dosya = dosyaRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Dosya bulunamadı: uuid=" + uuid));

        // Not: Burası artık daha güvenli. 'dosya.getDepolamaTipi()' ile dinamik olarak
        // doğru servisi seçmek en doğrusudur.
        DepolamaService depolamaServisi = depolamaServisleri.get(dosya.getDepolamaTipi());
        if (depolamaServisi == null) {
            throw new IllegalStateException(dosya.getDepolamaTipi() + " için tanımlı bir depolama servisi bulunamadı.");
        }

        log.warn("'{}' adlı dosya (UUID: {}) sistemden siliniyor...", dosya.getOrijinalAdi(), uuid);
        depolamaServisi.sil(dosya.getDosyaYolu(), dosya.getBenzersizAdi());
        dosyaRepository.delete(dosya);
        log.info("'{}' adlı dosya (UUID: {}) sistemden başarıyla silindi.", dosya.getOrijinalAdi(), uuid);
    }

    @Override
    public String publicUrlAl(Dosya dosya) {
        DepolamaService servis = depolamaServisleri.get(dosya.getDepolamaTipi());
        if (servis == null) {
            log.error("{} tipi için depolama servisi bulunamadı.", dosya.getDepolamaTipi());
            return null;
        }
        return servis.publicUrlAl(dosya.getDosyaYolu(), dosya.getBenzersizAdi());
    }

}