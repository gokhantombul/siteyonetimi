package com.gtombul.siteyonetimi.model.ilan;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "ILAN_RESIM")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "ilan")
@SQLRestriction("DURUM <> 'SILINDI'")
public class IlanResim extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ilan_id", nullable = false)
    private Ilan ilan;

    /**
     * Senin upload kayıtlarının ID’si (Dosya/DokumanVersiyon vb.)
     */
    @Column(name = "kaynak_kayit_id", nullable = false)
    private Long kaynakKayitId;

    @Column(name = "sira_no")
    private Integer siraNo;

}