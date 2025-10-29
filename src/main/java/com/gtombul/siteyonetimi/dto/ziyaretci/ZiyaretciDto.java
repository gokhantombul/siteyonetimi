package com.gtombul.siteyonetimi.dto.ziyaretci;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.YakinlikDerecesi;
import com.gtombul.siteyonetimi.model.enums.ZiyaretciDurum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ZiyaretciDto extends BaseDto {

    @NotBlank @Size(max = 128)
    private String ad;

    @NotBlank @Size(max = 128)
    private String soyad;

    private String adSoyad; // Sunum kolaylığı

    @NotBlank @Size(max = 20)
    private String telefon;

    @Size(max = 512)
    private String aciklama;

    @NotNull
    private YakinlikDerecesi yakinlikDerecesi;

    @NotNull
    private ZiyaretciDurum ziyaretciDurum;

    // GELDI durumunda zorunlu; DAVETLI/CIKTI'da opsiyonel.
    private Long daireId;

}
