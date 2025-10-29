package com.gtombul.siteyonetimi.dto.talep;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TalepGuncellemeDto extends BaseDto {
    private String aciklama;
    private String eskiDurum;
    private String yeniDurum;
    private String kullaniciAdi; // UserEntity'den (Ad Soyad)
}