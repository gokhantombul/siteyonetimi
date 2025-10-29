package com.gtombul.siteyonetimi.model.anket;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ANKET")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Anket extends BaseEntity {

    @Column(name = "baslik", length = 255, nullable = false)
    private String baslik;

    @Column(name = "aciklama", length = 2000)
    private String aciklama;

    @Enumerated(EnumType.STRING)
    @Column(name = "tur", nullable = false, length = 20)
    private AnketTuru tur;

    @Column(name = "anonim_mi", nullable = false)
    private boolean anonimMi;

    @Column(name = "baslangic_tarihi", nullable = false)
    private LocalDateTime baslangicTarihi;

    @Column(name = "bitis_tarihi", nullable = false)
    private LocalDateTime bitisTarihi;

    // ANKET türünde seçenekler; KARAR türünde boş kalır.
    @OneToMany(mappedBy = "anket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<AnketSecenek> secenekler = new ArrayList<>();

}