package com.gtombul.siteyonetimi.model.anket;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "ANKET_SECENEK")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class AnketSecenek extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anket_id", nullable = false)
    @ToString.Exclude
    private Anket anket;

    @Column(name = "metin", length = 500, nullable = false)
    private String metin;

    @Column(name = "sira_no", nullable = false)
    private Integer siraNo;

}