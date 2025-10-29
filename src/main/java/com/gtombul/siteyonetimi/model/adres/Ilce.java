package com.gtombul.siteyonetimi.model.adres;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("DURUM <> 'SILINDI'")
public class Ilce extends BaseEntity {

    @Column(nullable = false)
    private String ad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "il_id", nullable = false)
    @ToString.Exclude
    private Il il;

}
