package com.gtombul.siteyonetimi.model.adres;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class AdresBilgisi {

    private String adresSatir1;
    private String adresSatir2;
    private String mahalle;
    private String postaKodu;
    private String ilceAd;
    private String ilAd;

}
