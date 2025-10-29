package com.gtombul.siteyonetimi.model.talep;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.model.enums.TalepDurum;
import com.gtombul.siteyonetimi.model.enums.TalepOncelik;
import com.gtombul.siteyonetimi.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "talep")
public class Talep extends BaseEntity {

    @Column(name = "konu", nullable = false, length = 200)
    private String konu;

    @Lob
    @Column(name = "aciklama", nullable = false)
    private String aciklama;

    @Enumerated(EnumType.STRING) // DB'ye 'ACIK', 'ISLEMDE' vs. yazılacak (Enum'daki 'value' alanı)
    @Column(name = "talep_durum", nullable = false)
    private TalepDurum talepDurum;

    @Enumerated(EnumType.STRING)
    @Column(name = "talep_oncelik", nullable = false)
    private TalepOncelik talepOncelik;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "olusturan_kullanici_id", referencedColumnName = "id", nullable = false)
    private UserEntity olusturanKullanici;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daire_id", referencedColumnName = "id", nullable = false)
    private Daire daire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id", referencedColumnName = "id", nullable = false)
    private TalepKategori kategori;

    @OneToMany(mappedBy = "talep", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("olusturmaTarihi DESC")
    private List<TalepGuncelleme> guncellemeler;

    // Soft delete'te talebi silince görevi de sil (cascade + orphanRemoval)
    @OneToOne(mappedBy = "talep", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Gorev gorev;
}