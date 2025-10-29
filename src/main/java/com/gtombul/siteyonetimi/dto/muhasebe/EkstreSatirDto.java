package com.gtombul.siteyonetimi.dto.muhasebe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EkstreSatirDto {

    private LocalDate tarih;

    private String fisNo;

    private BigDecimal borc;

    private BigDecimal alacak;

    private String aciklama;

}

