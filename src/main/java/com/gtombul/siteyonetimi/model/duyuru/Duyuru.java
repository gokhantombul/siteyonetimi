package com.gtombul.siteyonetimi.model.duyuru;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "duyuru")
public class Duyuru extends BaseEntity {

    @Column(name = "baslik", nullable = false, length = 200)
    private String baslik;

    @Column(name = "icerik", nullable = false, columnDefinition = "TEXT")
    private String icerik;

    /**
     * Duyurunun hangi tarihten itibaren görünür olacağını belirtir.
     * Bu tarih gelmeden duyuru listelerde görünmez.
     */
    @Column(name = "yayinlanma_tarihi", nullable = false) // <-- YENİ EKLENDİ
    private LocalDate yayinlanmaTarihi;

    /**
     * Duyurunun hangi tarihe kadar geçerli olduğunu belirtir.
     * Bu alan null ise, duyuru süresizdir.
     */
    @Column(name = "gecerlilik_tarihi")
    private LocalDate gecerlilikTarihi;

    /**
     * Bir duyurunun sahip olduğu dosyaların listesi.
     * mappedBy: İlişkinin sahibinin DuyuruDosyasi entity'sindeki "duyuru" alanı olduğunu belirtir.
     * cascade: Duyuru kaydedildiğinde/güncellendiğinde ilişkili dosyaların da otomatik olarak kaydedilmesini sağlar.
     * orphanRemoval: Duyuru'nun dosyalar listesinden bir dosya çıkarıldığında, o dosyanın veritabanından silinmesini tetikler.
     */
    @OneToMany(
            mappedBy = "duyuru",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default // Lombok builder kullanırken koleksiyonun null olmasını engeller.
    private Set<DuyuruDosyasi> dosyalar = new HashSet<>();

}
