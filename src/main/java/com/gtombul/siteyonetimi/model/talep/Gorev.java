package com.gtombul.siteyonetimi.model.talep;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.GorevDurum;
import com.gtombul.siteyonetimi.model.personel.Personel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "gorev")
public class Gorev extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talep_id", referencedColumnName = "id", nullable = false, unique = true)
    private Talep talep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personel_id", referencedColumnName = "id")
    private Personel atananPersonel;

    @Enumerated(EnumType.STRING)
    @Column(name = "gorev_durum", nullable = false)
    private GorevDurum gorevDurum;

    @Lob
    @Column(name = "yonetici_notu")
    private String yoneticiNotu;

    @Lob
    @Column(name = "personel_notu")
    private String personelNotu;

    @Column(name = "planlanan_baslangic_tarihi")
    private LocalDateTime planlananBaslangicTarihi;

    @Column(name = "planlanan_bitis_tarihi")
    private LocalDateTime planlananBitisTarihi;

    @Column(name = "tamamlanma_tarihi")
    private LocalDateTime tamamlanmaTarihi;
}