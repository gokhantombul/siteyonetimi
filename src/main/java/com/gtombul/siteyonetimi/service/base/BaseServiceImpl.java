package com.gtombul.siteyonetimi.service.base;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.base.BaseRepository;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.search.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public abstract class BaseServiceImpl<E extends BaseEntity, D, ID extends Serializable> implements BaseService<D, ID> {

    protected final BaseRepository<E, ID> repository;
    protected final BaseMapper<E, D> mapper;
    protected final Class<E> entityClass;

    protected BaseServiceImpl(BaseRepository<E, ID> repository, BaseMapper<E, D> mapper, Class<E> entityClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityClass = entityClass;
    }

    /* =======================
     HOOK NOKTALARI (no-op)
     Alt sınıflar override edebilir
     ======================= */
    protected void beforeCreate(E entity) {}
    protected void afterCreate(E entity) {}
    protected void beforeUpdate(E entity) {}
    protected void afterUpdate(E entity) {}
    protected void beforeDelete(E entity) {}
    protected void afterDelete(E entity) {}

    /** SİLİNDİ olmayan kayıtlar için ortak Specification */
    protected Specification<E> aktifKayitSpec() {
        return (root, q, cb) -> cb.notEqual(root.get("durum"), Durum.SILINDI);
    }

    @Override
    @Transactional
    public D create(D dto) {
        E entity = mapper.toEntity(dto);
        beforeCreate(entity);
        entity = repository.save(entity);
        afterCreate(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public D update(ID id, D dto) {
        E entity = repository.findById(id).orElseThrow(() -> notFound(id));
        mapper.updateEntityFromDto(dto, entity);
        beforeUpdate(entity);
        entity = repository.save(entity);
        afterUpdate(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public D update(UUID uuid, D dto) {
        E entity = repository.findByUuidAndDurum(uuid, Durum.AKTIF).orElseThrow(() -> notFound(uuid));
        mapper.updateEntityFromDto(dto, entity);
        beforeUpdate(entity);
        entity = repository.save(entity);
        afterUpdate(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        E entity = repository.findById(id).orElseThrow(() -> notFound(id));
        beforeDelete(entity);
        entity.setDurum(Durum.SILINDI);
        repository.save(entity);
        afterDelete(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        E entity = repository.findByUuidAndDurum(id, Durum.AKTIF).orElseThrow(() -> notFound(id));
        beforeDelete(entity);
        entity.setDurum(Durum.SILINDI);
        repository.save(entity);
        afterDelete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public D get(ID id) {
        E e = repository.findById(id)
                .filter(x -> x.getDurum() != Durum.SILINDI)
                .orElseThrow(() -> notFound(id));
        return mapper.toDto(e);
    }

    @Override
    @Transactional(readOnly = true)
    public D get(UUID uuid) {
        E e = repository.findByUuid(uuid).filter(x -> x.getDurum() != Durum.SILINDI)
                .orElseThrow(() -> notFound(uuid));
        return mapper.toDto(e);
    }

    @Override
    @Transactional(readOnly = true)
    public D getAndDurum(ID id, Durum durum) {
        E e = repository.findByIdAndDurum(id, durum).orElseThrow(() -> notFound(id));
        return mapper.toDto(e);
    }

    @Override
    @Transactional(readOnly = true)
    public D getAndDurum(UUID uuid, Durum durum) {
        E e = repository.findByUuidAndDurum(uuid, durum).orElseThrow(() -> notFound(uuid));
        return mapper.toDto(e);
    }

    @Transactional(readOnly = true)
    public E getEntityAndDurum(ID id, Durum durum) {
        return repository.findByIdAndDurum(id, durum).orElseThrow(() -> notFound(id));
    }

    @Transactional(readOnly = true)
    public E getEntity(ID id) {
        return repository.findById(id).orElseThrow(() -> notFound(id));
    }

    @Transactional(readOnly = true)
    public E getEntityAndDurum(UUID uuid, Durum durum) {
        return repository.findByUuidAndDurum(uuid, durum).orElseThrow(() -> notFound(uuid));
    }

    @Transactional(readOnly = true)
    public E getEntity(UUID uuid) {
        return repository.findByUuid(uuid).orElseThrow(() -> notFound(uuid));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<D> list(int page, int size, String sort) {
        Pageable pageable = pageable(sort, page, size);
        Page<E> p = repository.findAll(aktifKayitSpec(), pageable);
        return toPageResponse(p);
    }

    // 1) ŞABLON: Alt sınıflar burayı OVERRIDE ederek ek filtrelerini ekleyebilir.
    protected Specification<E> buildSpecification(SearchRequest request) {
        // Mevcut generic builder + soft delete filtresi birlikte kullanılıyor
        Specification<E> base = SpecificationBuilder.build(entityClass, request.keyword(), request.filters());
        return base.and(aktifKayitSpec());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<D> search(SearchRequest request) {
        Pageable pageable = pageable(request.sort(),
                request.page() == null ? 0 : request.page(),
                request.size() == null ? 20 : request.size());

        Specification<E> spec = buildSpecification(request);
        Page<E> p = repository.findAll(spec, pageable);
        return toPageResponse(p);
    }

    protected RuntimeException notFound(ID id) {
        return new EntityNotFoundException(entityClass.getSimpleName() + " bulunamadı: id=" + id);
    }

    protected RuntimeException notFound(UUID uuid) {
        return new EntityNotFoundException(entityClass.getSimpleName() + " bulunamadı: uuid=" + uuid);
    }

    private Pageable pageable(String sort, int page, int size) {
        if (sort == null || sort.isBlank()) return PageRequest.of(page, size);
        String[] parts = sort.split(",");
        Sort s = parts.length == 2 && parts[1].equalsIgnoreCase("desc")
                ? Sort.by(parts[0]).descending()
                : Sort.by(parts[0]).ascending();
        return PageRequest.of(page, size, s);
    }

    private PageResponse<D> toPageResponse(Page<E> p) {
        return new PageResponse<>(
                p.map(mapper::toDto).getContent(),
                p.getTotalElements(),
                p.getTotalPages(),
                p.getNumber(),
                p.getSize()
        );
    }

    @Override
    public List<D> findAll() {
        return mapper.toDto(repository.findAllDurum());
    }

    @Override
    public Page<D> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

}