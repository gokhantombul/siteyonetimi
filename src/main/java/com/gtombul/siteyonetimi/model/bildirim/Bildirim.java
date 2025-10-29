package com.gtombul.siteyonetimi.model.bildirim;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "BILDIRIM", indexes = {
        @Index(name = "ix_bildirim_kanal", columnList = "kanal"),
        @Index(name = "ix_bildirim_durum", columnList = "durum")
})
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Bildirim extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "kanal", nullable = false)
    private BildirimKanali kanal;

    @Enumerated(EnumType.STRING)
    @Column(name = "bildirim_durumu", nullable = false)
    private BildirimDurumu bildirimDurumu;

    @Enumerated(EnumType.STRING)
    @Column(name = "oncelik", nullable = false)
    private BildirimOncelik oncelik;

    @Column(name = "hedef_telefon", length = 32)
    private String hedefTelefon;

    @Column(name = "hedef_token", length = 2048)
    private String hedefToken;

    @Column(name = "hedef_eposta", length = 320)
    private String hedefEposta;

    @Column(name = "baslik", length = 255)
    private String baslik;

    @Lob
    @Column(name = "icerik", nullable = false)
    private String icerik;

    @Column(nullable = false)
    private boolean icerikHtml = false;

    @Column(name = "hata_mesaji", length = 2048)
    private String hataMesaji;

    @Column(name = "referans_turu", length = 64)
    private String referansTuru; // ör: AIDAT_TAHSILAT

    @Column(name = "referans_id")
    private Long referansId;

}
