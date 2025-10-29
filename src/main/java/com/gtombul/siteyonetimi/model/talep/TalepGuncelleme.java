package com.gtombul.siteyonetimi.model.talep;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "talep_guncelleme")
public class TalepGuncelleme extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talep_id", referencedColumnName = "id", nullable = false)
    private Talep talep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id", nullable = false)
    private UserEntity kullanici;

    @Lob
    @Column(name = "aciklama", nullable = false)
    private String aciklama;

    // Durum değişikliği loglanıyorsa. Artık 'value' değerlerini (string) tutabiliriz.
    @Column(name = "eski_durum", length = 50)
    private String eskiDurum; // Örn: "ACIK"

    @Column(name = "yeni_durum", length = 50)
    private String yeniDurum; // Örn: "ISLEMDE"
}