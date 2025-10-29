package com.gtombul.siteyonetimi.model.talep;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "talep_kategori")
public class TalepKategori extends BaseEntity {

    @Column(name = "ad", nullable = false, length = 100)
    private String ad;

    @Column(name = "aciklama", length = 500)
    private String aciklama;

    // Bir kategorinin birden fazla talebi olabilir.
    // Düşük maliyet için bu ilişkiyi Talep tarafından yönetiyoruz (mappedBy).
    @OneToMany(mappedBy = "kategori")
    private Set<Talep> talepler;

}