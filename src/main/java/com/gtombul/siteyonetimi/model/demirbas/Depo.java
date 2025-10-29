package com.gtombul.siteyonetimi.model.demirbas;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "DEPO")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Depo extends BaseEntity {

    @Column(name = "ad", length = 255, nullable = false)
    private String ad;

    @Column(name = "adres", length = 1024)
    private String adres;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}