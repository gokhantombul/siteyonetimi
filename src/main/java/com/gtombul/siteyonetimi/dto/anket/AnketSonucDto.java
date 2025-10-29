package com.gtombul.siteyonetimi.dto.anket;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnketSonucDto {

    private Long anketId;
    private String baslik;
    private String tur;
    private long toplamOy;

    // ANKET türü için
    private List<SecenekSonucDto> secenekSonuclari;

    // KARAR türü için
    private long evetSayisi;
    private long hayirSayisi;
    private long cekimserSayisi;

}
