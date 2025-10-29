package com.gtombul.siteyonetimi.model.insan;


import com.gtombul.siteyonetimi.model.adres.IletisimBilgisi;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "kisi",
        indexes = {
                @Index(name = "ix_kisi_ad", columnList = "ad,soyad"),
                @Index(name = "ix_kisi_tc_no", columnList = "tc_kimlik_no")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_kisi_tc", columnNames = {"tc_kimlik_no"})
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Kisi extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String ad;

    @Column(nullable = false, length = 120)
    private String soyad;

    @Column(name = "tc_kimlik_no", length = 11)
    private String tcKimlikNo;

    @Embedded
    private IletisimBilgisi iletisim;

}
