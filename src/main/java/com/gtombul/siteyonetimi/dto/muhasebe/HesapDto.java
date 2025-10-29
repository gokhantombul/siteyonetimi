package com.gtombul.siteyonetimi.dto.muhasebe;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class HesapDto extends BaseDto {

    @NotBlank
    private String ad;

    @NotNull
    private HesapTipi hesapTipi;

    private Long referansId;

    @NotNull
    private ParaBirimi paraBirimi;

    private String aciklama;

}
