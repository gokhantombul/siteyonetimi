package com.gtombul.siteyonetimi.dto.talep;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.TalepDurum;
import com.gtombul.siteyonetimi.model.enums.TalepOncelik;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// Listeleme (grid) için daha az veri içeren DTO (Best practice)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TalepListeDto extends BaseDto {
    private String konu;
    private TalepDurum talepDurum;
    private TalepOncelik talepOncelik;
    private String daireBilgisi;
    private String kategoriAdi;
    private String olusturanKullaniciAdi;
    private String atananPersonelAdi; // GörevDto'dan değil, direkt Gorev'den alınabilir
}