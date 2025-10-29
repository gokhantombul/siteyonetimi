package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.OturumTipi;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(
        name = "daire_oturum",
        indexes = {
                @Index(name = "ix_oturum_daire", columnList = "daire_id"),
                @Index(name = "ix_oturum_kisi", columnList = "kisi_id")
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class DaireOturum extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daire_id", nullable = false)
    @ToString.Exclude
    private Daire daire;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false)
    @ToString.Exclude
    private Kisi kisi; // oturan kişi (mülk sahibi, kiracı veya misafir)

    @Enumerated(EnumType.STRING)
    @Column(name = "oturum_tipi", nullable = false, length = 20)
    private OturumTipi oturumTipi;

    private LocalDate baslangicTarihi;

    private LocalDate bitisTarihi; // NULL => halen oturuyor

    // Kiracı ise bağlayıcı sözleşme referansı
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kira_sozlesmesi_id")
    @ToString.Exclude
    private KiraSozlesmesi kiraSozlesmesi;

}
