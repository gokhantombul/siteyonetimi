package com.gtombul.siteyonetimi.dto.kargo;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.kargo.KargoDurum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class KargoDto extends BaseDto {

    @NotBlank
    private String kargoSirketi;

    @NotBlank
    private String takipNo;

    @NotNull
    private Long daireId;

    @NotBlank
    private String aliciAdSoyad;

    private String aliciTelefon;
    private OffsetDateTime girisZamani;
    private OffsetDateTime teslimZamani;
    private String teslimAlan;
    private String teslimKodu;
    private KargoDurum kargoDurum;
    private String notlar;

    private String girisKaydedenKullanici;
    private String teslimKaydedenKullanici;

}
