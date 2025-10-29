package com.gtombul.siteyonetimi.dto.ilan;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class IlanKategoriDto extends BaseDto {

    @NotBlank
    @Size(max = 255)
    private String ad;

    @Size(max = 1024)
    private String aciklama;

}

