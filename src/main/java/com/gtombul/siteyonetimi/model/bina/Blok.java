package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "blok",
        indexes = {
                @Index(name = "ix_blok_ad", columnList = "ad"),
                @Index(name = "ix_blok_site_id", columnList = "site_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_blok_site_kod", columnNames = {"site_id", "kod"})
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Blok extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    @ToString.Exclude
    private Site site;

    @Column(nullable = false, length = 50)
    private String kod; // Örn: A, B, C, 1,2

    @Column(nullable = false, length = 255)
    private String ad; // Örn: A Blok

}
