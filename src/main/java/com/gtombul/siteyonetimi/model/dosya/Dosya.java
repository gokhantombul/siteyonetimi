package com.gtombul.siteyonetimi.model.dosya;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dosyalar")
public class Dosya extends BaseEntity {

    @Column(name = "orijinal_adi", nullable = false)
    private String orijinalAdi;

    @Column(name = "benzersiz_adi", nullable = false, unique = true)
    private String benzersizAdi; // UUID.jpg veya timestamp-orijinal-ad.pdf gibi

    @Column(name = "dosya_yolu", nullable = false)
    private String dosyaYolu; // "duyurular/2025/10/" gibi

    @Column(name = "dosya_tipi", nullable = false) // image/jpeg, application/pdf
    private String dosyaTipi;

    @Column(name = "boyut", nullable = false)
    private Long boyut; // Byte cinsinden

    @Enumerated(EnumType.STRING)
    @Column(name = "depolama_tipi", nullable = false)
    private DepolamaTipi depolamaTipi; // LOCAL, S3, AZURE_BLOB vs.

}