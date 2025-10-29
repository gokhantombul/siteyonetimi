package com.gtombul.siteyonetimi.model.bildirim;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "ILETISIM_TERCIHI", uniqueConstraints = {
        @UniqueConstraint(name = "uk_tercih_kisi", columnNames = {"kisi_id"})
})
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class IletisimTercihi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false)
    @ToString.Exclude
    private Kisi kisi;

    @Column(name = "sms_aktif", nullable = false)
    private boolean smsAktif = true;

    @Column(name = "push_aktif", nullable = false)
    private boolean pushAktif = true;

    @Column(name = "email_aktif", nullable = false)
    private boolean emailAktif = true;

    // Basit alanlar (ilişki maliyeti yok):
    @Column(name = "telefon", length = 32)
    private String telefon;

    @Column(name = "cihaz_token", length = 2048)
    private String cihazToken; // FCM token

    @Column(length = 320)
    private String eposta;

}
