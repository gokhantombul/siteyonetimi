package com.gtombul.siteyonetimi.model.ilan;


import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "ILAN_KATEGORI",
        uniqueConstraints = @UniqueConstraint(name = "uk_ilan_kategori_ad", columnNames = "ad"))
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class IlanKategori extends BaseEntity {

    @Column(name = "ad", length = 255, nullable = false)
    private String ad;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}
