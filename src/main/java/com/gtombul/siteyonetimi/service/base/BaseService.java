package com.gtombul.siteyonetimi.service.base;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.search.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface BaseService<D, ID extends Serializable> {

    D create(D dto);

    D update(ID id, D dto);

    D update(UUID uuid, D dto);

    void delete(ID id);

    void delete(UUID uuid);

    D get(ID id);

    D get(UUID uuid);

    D getAndDurum(ID id, Durum durum);

    D getAndDurum(UUID uuid, Durum durum);

    List<D> findAll();

    Page<D> findAll(Pageable pageable);

    PageResponse<D> list(int page, int size, String sort);

    PageResponse<D> search(SearchRequest request);

}