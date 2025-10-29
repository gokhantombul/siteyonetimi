package com.gtombul.siteyonetimi.dto.talep;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.GorevDurum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GorevDto extends BaseDto {

    // Enum'un kendisini döndürüyoruz, @JsonFormat(Shape.OBJECT) sayesinde
    // {id, value, label} olarak JSON'a çevrilecek.
    private GorevDurum gorevDurum;

    private String yoneticiNotu;
    private String personelNotu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime planlananBaslangicTarihi;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime planlananBitisTarihi;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime tamamlanmaTarihi;

    private String atananPersonelAdi; // Personel Entity'sinden
    private UUID talepUuid; // Görevin bağlı olduğu talep UUID'si
}