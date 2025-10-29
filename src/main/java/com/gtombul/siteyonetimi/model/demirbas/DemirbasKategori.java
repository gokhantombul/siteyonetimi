package com.gtombul.siteyonetimi.model.demirbas;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "DEMIRBAS_KATEGORI")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class DemirbasKategori extends BaseEntity {

    @Column(name = "ad", length = 255, nullable = false)
    private String ad;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}
