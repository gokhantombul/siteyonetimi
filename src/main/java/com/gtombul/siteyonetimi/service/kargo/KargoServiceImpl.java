package com.gtombul.siteyonetimi.service.kargo;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.kargo.KargoDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoListeDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoOlusturGuncelleDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoTeslimDto;
import com.gtombul.siteyonetimi.mapper.kargo.KargoMapper;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.kargo.Kargo;
import com.gtombul.siteyonetimi.model.kargo.KargoDurum;
import com.gtombul.siteyonetimi.repository.kargo.KargoRepository;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.search.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class KargoServiceImpl implements KargoService {

    private final KargoRepository kargoRepository;
    private final KargoMapper kargoMapper;

    @Override
    @Transactional
    public KargoDto olustur(KargoOlusturGuncelleDto dto) {
        kargoRepository.findByTakipNoAndDurumNot(dto.takipNo(), Durum.SILINDI)
                .ifPresent(k -> {
                    throw new IllegalArgumentException("Takip numarası zaten kayıtlı: " + dto.takipNo());
                });

        Kargo entity = kargoMapper.toEntity(dto);
        entity.setKargoDurum(KargoDurum.BEKLIYOR);
        entity.setGirisZamani(OffsetDateTime.now());
        log.info("Yeni kargo girişi: takipNo={} daireId={}", entity.getTakipNo(), entity.getDaireId());

        return kargoMapper.toDto(kargoRepository.save(entity));
    }

    @Override
    @Transactional
    public KargoDto guncelle(Long id, KargoOlusturGuncelleDto dto) {
        Kargo entity = getirById(id);
        kargoMapper.guncelle(entity, dto);
        log.info("Kargo güncellendi: id={} takipNo={}", id, entity.getTakipNo());
        return kargoMapper.toDto(kargoRepository.save(entity));
    }

    @Override
    @Transactional
    public void silSoft(Long id) {
        Kargo entity = getirById(id);
        entity.setDurum(Durum.SILINDI);
        log.warn("Kargo soft delete yapıldı: id={} takipNo={}", id, entity.getTakipNo());
        kargoRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public KargoDto getir(Long id) {
        return kargoMapper.toDto(kargoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Kargo.class.getSimpleName() + " bulunamadı: id=" + id)));
    }

    @Transactional(readOnly = true)
    public Kargo getirById(Long id) {
        return kargoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Kargo.class.getSimpleName() + " bulunamadı: id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<KargoListeDto> ara(SearchRequest request) {
        Specification<Kargo> spec = SpecificationBuilder.build(Kargo.class, request.keyword(), request.filters());
        Pageable pageable = pageable(request.sort(),
                request.page() == null ? 0 : request.page(),
                request.size() == null ? 20 : request.size());
        Page<Kargo> page = kargoRepository.findAll(spec, pageable);
        return new PageResponse<>(
                page.map(kargoMapper::toListeDto).getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }

    private Pageable pageable(String sort, int page, int size) {
        if (sort == null || sort.isBlank()) return PageRequest.of(page, size);
        String[] parts = sort.split(",");
        Sort s = parts.length == 2 && parts[1].equalsIgnoreCase("desc")
                ? Sort.by(parts[0]).descending()
                : Sort.by(parts[0]).ascending();
        return PageRequest.of(page, size, s);
    }

    @Override
    @Transactional
    public KargoDto teslimEt(KargoTeslimDto dto) {
        Kargo entity = getirById(dto.id());

        if (entity.getKargoDurum() == KargoDurum.TESLIM_EDILDI) {
            throw new IllegalStateException("Kargo zaten teslim edilmiş.");
        }

        entity.setTeslimAlan(dto.teslimAlan());
        entity.setTeslimKodu(dto.teslimKodu());
        entity.setTeslimZamani(OffsetDateTime.now());
        entity.setKargoDurum(KargoDurum.TESLIM_EDILDI);

        log.info("Kargo teslim edildi: id={} takipNo={} teslimAlan={}",
                entity.getId(), entity.getTakipNo(), entity.getTeslimAlan());

        return kargoMapper.toDto(kargoRepository.save(entity));
    }

    @Override
    @Transactional
    public KargoDto iadeEt(Long id, String notlar) {
        Kargo entity = getirById(id);
        entity.setKargoDurum(KargoDurum.IADE_EDILDI);
        entity.setNotlar(notlar);
        entity.setTeslimZamani(null);
        entity.setTeslimAlan(null);
        entity.setTeslimKodu(null);
        log.warn("Kargo iade edildi: id={} takipNo={} notlar={}", id, entity.getTakipNo(), notlar);
        return kargoMapper.toDto(kargoRepository.save(entity));
    }

    @Override
    @Transactional
    public KargoDto kayipIsaretle(Long id, String notlar) {
        Kargo entity = getirById(id);
        entity.setKargoDurum(KargoDurum.KAYIP);
        entity.setNotlar(notlar);
        log.error("Kargo kayıp işaretlendi: id={} takipNo={} notlar={}", id, entity.getTakipNo(), notlar);
        return kargoMapper.toDto(kargoRepository.save(entity));
    }

}

