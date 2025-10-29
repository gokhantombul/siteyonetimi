package com.gtombul.siteyonetimi.model.adres;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.Durum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "IL")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Il extends BaseEntity {

    @Column(nullable = false)
    private String ad;

    @Column(nullable = false)
    @Min(1)
    private Integer plakaKodu;

    @Column(nullable = false)
    @Min(1)
    private Integer telefonKodu;

    //@OneToMany(mappedBy = "il", cascade = CascadeType.ALL, orphanRemoval = true) soft delete için değiştirildi.
    @OneToMany(mappedBy = "il", fetch = LAZY, cascade = {PERSIST, MERGE, REFRESH})
    private List<Ilce> ilceler = new ArrayList<>();

    // Yardımcı metotlar: ilişkiyi güvenle yönet
    public void addIlce(Ilce ilce) {
        ilceler.add(ilce);
        ilce.setIl(this);
    }

    /**
     * Soft remove: orphanRemoval kullanmıyoruz; burada statüyü değiştiriyoruz.
     */
    public void removeIlce(Ilce ilce) {
        if (ilceler.remove(ilce)) {
            ilce.setDurum(Durum.SILINDI);   // DB'ye yansıyacak (merge/save çağrısında)
            //ilce.setIl(null);
        }
    }

}
