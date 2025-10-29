package com.gtombul.siteyonetimi.dto.dosya;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DosyaResponseDto extends BaseDto {
    private String orijinalAdi;
    private String dosyaTipi;
    private Long boyut;
    private String url; // <-- Dosyaya erişim için public URL
}