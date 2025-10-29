package com.gtombul.siteyonetimi.model.muhasebe;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "hesap",
        indexes = {
                @Index(name = "idx_hesap_referans", columnList = "referans_id"),
                @Index(name = "idx_hesap_durum",    columnList = "durum")
        }
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("DURUM <> 'SILINDI'")
public class Hesap extends BaseEntity {

    @Column(name = "ad", nullable = false, length = 255)
    private String ad;

    @Enumerated(EnumType.STRING)
    @Column(name = "hesap_tipi", nullable = false, length = 32)
    private HesapTipi hesapTipi;

    /** Daire/Banka/Kasa/… tablo kimliği — ilişki maliyetini düşürmek için sadece ID */
    @Column(name = "referans_id")
    private Long referansId;

    @Enumerated(EnumType.STRING)
    @Column(name = "para_birimi", nullable = false, length = 8)
    private ParaBirimi paraBirimi;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}
