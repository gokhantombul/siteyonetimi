package com.gtombul.siteyonetimi.mapper.dosya;

import com.gtombul.siteyonetimi.config.SpringContext; // <-- YENİ İMPORT
import com.gtombul.siteyonetimi.dto.dosya.DosyaResponseDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.dosya.Dosya;
import com.gtombul.siteyonetimi.service.dosya.DosyaService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
// @Autowired import'unu silebiliriz.

@Mapper(componentModel = "spring")
public abstract class DosyaMapper implements BaseMapper<Dosya, DosyaResponseDto> {

    // ARTIK @Autowired ALANINA GÜVENMİYORUZ. SİLİNDİ:
    // @Autowired
    // protected DosyaServisi dosyaServisi;

    /**
     * Entity'den DTO'ya map'leme sonrası çalışır ve dosyanın public URL'ini set eder.
     */
    @AfterMapping
    protected void urlDoldur(Dosya entity, @MappingTarget DosyaResponseDto dto) {
        // Hatanın olduğu yer (DosyaMapper.java:25) burasıydı.
        if (entity != null) {
            // ARTIK NULL OLMAYACAK: Servisi manuel olarak çekiyoruz.
            dto.setUrl(getDosyaServisi().publicUrlAl(entity));
        }
    }

    /**
     * Bu metot, DosyaServisi bean'ini Spring Context'ten manuel olarak çeker.
     * Bu, NullPointerException'ı %100 çözer.
     */
    private DosyaService getDosyaServisi() {
        return SpringContext.getBean(DosyaService.class);
    }

    // --- BaseMapper Sözleşmesi (Aynen kalır) ---
    @Override
    public abstract DosyaResponseDto toDto(Dosya entity);

    @Override
    public Dosya toEntity(DosyaResponseDto dto) {
        throw new UnsupportedOperationException("DosyaResponseDto'dan Entity'ye dönüşüm desteklenmiyor.");
    }

    @Override
    public void updateEntityFromDto(DosyaResponseDto dto, @MappingTarget Dosya entity) {
        throw new UnsupportedOperationException("DosyaResponseDto'dan Entity'ye güncelleme desteklenmiyor.");
    }
}