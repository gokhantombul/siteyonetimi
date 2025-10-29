package com.gtombul.siteyonetimi.dto.duyuru;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.dto.dosya.DosyaResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

/**
 * Bir duyuru bilgisini API'den dışarıya döndürmek için kullanılır.
 * Tüm BaseDto alanlarını ve ilişkili dosyaların URL'lerini içerir.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class DuyuruResponse extends BaseDto {

    private String baslik;
    private String icerik;
    private LocalDate yayinlanmaTarihi;
    private LocalDate gecerlilikTarihi;

    /**
     * Bu duyuruya bağlı dosyaların listesi.
     * Artık merkezi DosyaResponseDto'sunu kullanıyoruz.
     */
    private Set<DosyaResponseDto> dosyalar;
}