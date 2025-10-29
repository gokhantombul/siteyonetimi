package com.gtombul.siteyonetimi.dto.demirbas;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class DemirbasKategoriDto extends BaseDto {

    @NotBlank
    @Size(max = 255)
    private String ad;

    @Size(max = 1024)
    private String aciklama;

}
