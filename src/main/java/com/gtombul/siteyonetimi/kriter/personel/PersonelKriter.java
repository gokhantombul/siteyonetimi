package com.gtombul.siteyonetimi.kriter.personel;

import com.gtombul.siteyonetimi.model.base.BaseKriter;
import com.gtombul.siteyonetimi.model.enums.PersonelGorev;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonelKriter extends BaseKriter {

    // ör: isim gibi anahtar kelimeleri Kisi üzerinden LIKE ile arayacağız
    private String kisiAdSoyad;      // LIKE
    private PersonelGorev gorev;     // EQUALS
    private Boolean calisiyor;       // türetilmiş (iseCikisTarihi null ve durum AKTIF)
    private LocalDate iseGirisBaslangic;
    private LocalDate iseGirisBitis;

}
