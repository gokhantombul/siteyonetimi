package com.gtombul.siteyonetimi.repository.base;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.Durum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

    Optional<E> findByIdAndDurum(ID id, Durum durum);

    Optional<E> findByUuid(UUID uuid);

    Optional<E> findByUuidAndDurum(UUID uuid, Durum durum);

    List<E> findAllByDurum(Durum durum);

    default List<E> findAllDurum() {
        return findAllByDurum(Durum.AKTIF);
    }

}
