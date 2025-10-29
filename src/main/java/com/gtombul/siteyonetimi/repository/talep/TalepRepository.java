package com.gtombul.siteyonetimi.repository.talep;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.talep.Talep;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TalepRepository extends BaseRepository<Talep, Long>, JpaSpecificationExecutor<Talep> {

    // N+1 Optimizasyonu (Best Practice):
    // Talep detayını çekerken ilişkili tüm verileri tek sorguda (JOIN) getirelim.
    @EntityGraph(attributePaths = {
            "olusturanKullanici",
            "daire", "daire.blok", // Daire ve bağlı olduğu Blok
            "kategori",
            "gorev", "gorev.atananPersonel", // Görev ve bağlı olduğu Personel
            "guncellemeler", "guncellemeler.kullanici" // Güncellemeler ve yapan kullanıcılar
    })
    Optional<Talep> findByUuidAndDurum(UUID uuid, Durum durum);

    // Liste sorguları için daha hafif EntityGraph'lar
    @EntityGraph(attributePaths = {"kategori", "daire", "daire.blok", "olusturanKullanici", "gorev", "gorev.atananPersonel"})
    List<Talep> findAllByOlusturanKullaniciUuidAndDurum(UUID kullaniciUuid, Durum durum);

    @EntityGraph(attributePaths = {"kategori", "daire", "daire.blok", "olusturanKullanici", "gorev", "gorev.atananPersonel"})
    List<Talep> findAllByGorevAtananPersonelUuidAndDurum(UUID personelUuid, Durum durum);

    // Sayfalama için EntityGraph
    @EntityGraph(attributePaths = {"kategori", "daire", "daire.blok", "olusturanKullanici", "gorev", "gorev.atananPersonel"})
    Page<Talep> findAllByDurum(Durum durum, Pageable pageable);
}