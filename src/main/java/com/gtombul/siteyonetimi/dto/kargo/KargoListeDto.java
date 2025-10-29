package com.gtombul.siteyonetimi.dto.kargo;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.kargo.KargoDurum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class KargoListeDto extends BaseDto {
    private String kargoSirketi;
    private String takipNo;
    private Long daireId;
    private String aliciAdSoyad;
    private KargoDurum kargoDurum;
    private OffsetDateTime girisZamani;
    private OffsetDateTime teslimZamani;
}
