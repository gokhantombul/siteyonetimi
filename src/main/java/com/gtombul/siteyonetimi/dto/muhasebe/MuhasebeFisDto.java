package com.gtombul.siteyonetimi.dto.muhasebe;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MuhasebeFisDto extends BaseDto {

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
