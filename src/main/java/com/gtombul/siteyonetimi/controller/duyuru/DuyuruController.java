package com.gtombul.siteyonetimi.controller.duyuru;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.base.ApiResponse;
import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruCreateRequest;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruResponse;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruUpdateRequest;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.service.duyuru.DuyuruService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/duyuru")
public class DuyuruController extends BaseController<DuyuruResponse, Long> {

    private final DuyuruService duyuruService;

    public DuyuruController(DuyuruService duyuruService) {
        super(duyuruService);
        this.duyuruService = duyuruService;
    }

    /**
     * YENİ OLUŞTURMA ENDPOINT'İ
     * BaseController'daki create metodunu ezer (override).
     * Hem JSON hem de dosyaları multipart/form-data olarak kabul eder.
     */
    //@PreAuthorize("hasAuthority('YONETICI')") // Güvenlik Eklemesi
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<DuyuruResponse>> olustur(
            @RequestPart("duyuru") @Valid DuyuruCreateRequest request,
            @RequestPart(value = "dosyalar", required = false) MultipartFile[] dosyalar
    ) {
        DuyuruResponse response = duyuruService.olustur(request, dosyalar);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * YENİ GÜNCELLEME ENDPOINT'İ
     * BaseController'daki update metodunu ezer (override).
     * Hem JSON hem de dosyaları multipart/form-data olarak kabul eder.
     */
    //@PreAuthorize("hasAuthority('YONETICI')") // Güvenlik Eklemesi
    @PutMapping(value = "/{uuid}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<DuyuruResponse>> guncelle(
            @PathVariable UUID uuid,
            @RequestPart("duyuru") @Valid DuyuruUpdateRequest request,
            @RequestPart(value = "yeniDosyalar", required = false) MultipartFile[] yeniDosyalar
    ) {
        DuyuruResponse response = duyuruService.guncelle(uuid, request, yeniDosyalar);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // --- MİRAS ALINAN VE STANDARTLAŞTIRILAN ENDPOINT'LER ---
    // BaseController'daki metotları override ederek standart ApiResponse zarfı ile dönüyoruz.

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<DuyuruResponse> get(@PathVariable UUID uuid) {
        // BaseController'daki get metodunu olduğu gibi kullanabiliriz.
        // İstersek ApiResponse.success() ile zarflayabiliriz.
        return super.get(uuid);
    }

    @Override
    @DeleteMapping("/{uuid}")
    //@PreAuthorize("hasAuthority('YONETICI')")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        // BaseController'daki delete metodu mükemmel çalışır.
        return super.delete(uuid);
    }

    @Override
    @GetMapping
    public ResponseEntity<PageResponse<DuyuruResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size,
                                                             @RequestParam(required = false) String sort) {
        // BaseController'daki list metodu mükemmel çalışır.
        return super.list(page, size, sort);
    }

    @Override
    @PostMapping("/ara")
    public ResponseEntity<PageResponse<DuyuruResponse>> search(@RequestBody SearchRequest request) {
        // BaseController'daki search metodu mükemmel çalışır.
        return super.search(request);
    }

    // BaseController'daki create(D) ve update(UUID, D) metotlarının
    // @RequestBody bekleyen versiyonları, bizim @RequestPart'lı metotlarımız tarafından
    // "override" edildiği için artık erişilemez durumdadır. Bu, tam da istediğimiz şeydir.
}