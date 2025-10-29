package com.gtombul.siteyonetimi.model.bildirim;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "BILDIRIM_SABLONU", indexes = {
        @Index(name = "ix_bs_kod", columnList = "kod", unique = true)
})
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class BildirimSablonu extends BaseEntity {

    @Column(name = "kod", nullable = false, length = 128)
    private String kod; // ör: AIDAT_HATIRLATMA

    @Enumerated(EnumType.STRING)
    @Column(name = "kanal", nullable = false)
    private BildirimKanali kanal;

    @Column(name = "baslik", length = 255)
    private String baslik; // PUSH için

    @Lob
    @Column(name = "icerik", nullable = false)
    private String icerik; // {ad}, {tutar}, {tarih} gibi placeholder'lar

}
