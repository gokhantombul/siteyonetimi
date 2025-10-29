package com.gtombul.siteyonetimi.dto.anket;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class AnketDto extends BaseDto {

    private String baslik;
    private String aciklama;
    private String tur;            // ANKET | KARAR
    private boolean anonimMi;
    private LocalDateTime baslangicTarihi;
    private LocalDateTime bitisTarihi;
    private List<SecenekDto> secenekler; // KARAR’da boş

}