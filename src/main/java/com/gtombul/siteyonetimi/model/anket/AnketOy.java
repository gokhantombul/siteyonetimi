package com.gtombul.siteyonetimi.model.anket;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "ANKET_OY",
        indexes = {
                @Index(name = "idx_anketoy_anket", columnList = "anket_id"),
                @Index(name = "idx_anketoy_kisi", columnList = "kisi_id")
        })
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class AnketOy extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anket_id", nullable = false)
    @ToString.Exclude
    private Anket anket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secenek_id")
    @ToString.Exclude
    private AnketSecenek secenek; // ANKET türünde dolu

    @Enumerated(EnumType.STRING)
    @Column(name = "karar_oyu", length = 20)
    private KararOyu kararOyu; // KARAR türünde dolu

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false)
    @ToString.Exclude
    private Kisi kisi;

    // İsteğe bağlı: daire bilgisi ilişki kurmadan id olarak tutulabilir (performans)
    @Column(name = "daire_id")
    private Long daireId;

}