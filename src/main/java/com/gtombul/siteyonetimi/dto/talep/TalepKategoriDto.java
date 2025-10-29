package com.gtombul.siteyonetimi.dto.talep;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true) // BaseDto'daki alanları da dahil et
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TalepKategoriDto extends BaseDto {

    @NotBlank(message = "Kategori adı boş olamaz.")
    @Size(min = 3, max = 100, message = "Kategori adı 3 ile 100 karakter arasında olmalıdır.")
    private String ad;

    @Size(max = 500, message = "Açıklama en fazla 500 karakter olabilir.")
    private String aciklama;
}