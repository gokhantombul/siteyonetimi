package com.gtombul.siteyonetimi.dto.anket;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecenekSonucDto {
    private Long secenekId;
    private String secenekMetin;
    private long oySayisi;
    private double yuzde; // 0-100
}
