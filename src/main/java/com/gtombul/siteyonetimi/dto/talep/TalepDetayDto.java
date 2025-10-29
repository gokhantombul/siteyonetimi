package com.gtombul.siteyonetimi.dto.talep;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.TalepDurum;
import com.gtombul.siteyonetimi.model.enums.TalepOncelik;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TalepDetayDto extends BaseDto {
    private String konu;
    private String aciklama;

    // Enum'ları tam nesne olarak döndür
    private TalepDurum talepDurum;
    private TalepOncelik talepOncelik;

    // İlişkili Veriler
    private String olusturanKullaniciAdi;
    private String daireBilgisi; // Örn: "A Blok - No: 5"
    private String kategoriAdi;
    private GorevDto gorev;
    private List<TalepGuncellemeDto> guncellemeler;
}