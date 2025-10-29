package com.gtombul.siteyonetimi.dto.bildirim;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.bildirim.BildirimKanali;
import com.gtombul.siteyonetimi.model.bildirim.BildirimOncelik;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class BildirimGonderRequestDto extends BaseDto {
    private Long kisiId;                 // varsa
    private String telefon;              // SMS için override edebilir
    private String cihazToken;           // PUSH için override edebilir
    private String eposta;
    private List<BildirimKanali> kanallar; // [SMS], [PUSH] veya ikisi
    private String sablonKodu;           // opsiyonel: şablon ile
    private String baslik;               // opsiyonel: sablon yoksa
    private String icerik;               // opsiyonel: sablon yoksa
    private Map<String, String> sablonDegerleri; // {ad}=..., {tutar}=...
    private BildirimOncelik oncelik;     // default NORMAL
    private String referansTuru;
    private Long referansId;
    private Boolean icerikHtml;
}
