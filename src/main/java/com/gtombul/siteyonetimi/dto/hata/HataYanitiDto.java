package com.gtombul.siteyonetimi.dto.hata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder; // Lombok builder'ı için getter'dan ayrı import gerekebilir
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List; // Eklendi
import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HataYanitiDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private final LocalDateTime zamanDamgasi = LocalDateTime.now();

    private final int httpDurumu;
    private final String hataKodu;
    private final String mesaj;

    // Değişiklik burada
    private Map<String, List<String>> detaylar;

    // Constructor'ı da güncellemeniz gerekebilir eğer elle yazdıysanız.
    // Lombok @AllArgsConstructor kullanıyorsanız otomatik olarak güncellenir.
    public HataYanitiDto(int httpDurumu, String hataKodu, String mesaj, Map<String, List<String>> detaylar) {
        this.httpDurumu = httpDurumu;
        this.hataKodu = hataKodu;
        this.mesaj = mesaj;
        this.detaylar = detaylar;
    }

}