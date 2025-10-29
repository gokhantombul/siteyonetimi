package com.gtombul.siteyonetimi.dto.muhasebe;

import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FisOlusturIstekDto {

    @NotBlank
    private String fisNo;

    @NotNull
    private LocalDate tarih;

    @NotNull
    private HareketTuru hareketTuru;

    private String aciklama;

    @NotEmpty
    private List<MuhasebeKalemDto> kalemler;

}
