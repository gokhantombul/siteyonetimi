package com.gtombul.siteyonetimi.controller.base;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.service.base.BaseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public abstract class BaseController<D, ID extends Serializable> {

    protected final BaseService<D, ID> service;

    protected BaseController(BaseService<D, ID> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<D> create(@RequestBody @Valid D dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<D> update(@PathVariable UUID uuid, @RequestBody @Valid D dto) {
        return ResponseEntity.ok(service.update(uuid, dto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<D> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getAndDurum(uuid, Durum.AKTIF));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<D>> list(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size,
                                                @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(service.list(page, size, sort));
    }

    @PostMapping("/ara")
    public ResponseEntity<PageResponse<D>> search(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(service.search(request));
    }

    @GetMapping("/tamami-liste")
    public ResponseEntity<List<D>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<D>> findAllPage(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

}