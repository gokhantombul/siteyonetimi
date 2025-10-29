package com.gtombul.siteyonetimi.dto.sozlesme;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.SozlesmeTipi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class SozlesmeDto extends BaseDto {
    private SozlesmeTipi tip;
    private String konu;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;
    private boolean otomatikYenileme;
    private Long dokumanId;
    private String dokumanAd;
    private Integer aktifVersiyonNo;
    private String taraflarOzeti;
}
