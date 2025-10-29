package com.gtombul.siteyonetimi.model.adres;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IletisimBilgisi {

    private String eposta;
    private String telefon;

}
