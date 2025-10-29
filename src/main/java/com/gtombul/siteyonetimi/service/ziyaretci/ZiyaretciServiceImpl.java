package com.gtombul.siteyonetimi.service.ziyaretci;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.ziyaretci.ZiyaretciDto;
import com.gtombul.siteyonetimi.mapper.ziyaretci.ZiyaretciMapper;
import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.ZiyaretciDurum;
import com.gtombul.siteyonetimi.model.ziyaretci.Ziyaretci;
import com.gtombul.siteyonetimi.repository.ziyaretci.ZiyaretciRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ZiyaretciServiceImpl extends BaseServiceImpl<Ziyaretci, ZiyaretciDto, Long> implements ZiyaretciService {

    private final ZiyaretciRepository ziyaretciRepository;
    private final ZiyaretciMapper ziyaretciMapper;

    public ZiyaretciServiceImpl(ZiyaretciRepository repository, ZiyaretciMapper mapper) {
        super(repository, mapper, Ziyaretci.class);
        this.ziyaretciRepository = repository;
        this.ziyaretciMapper = mapper;
    }

    @Override
    public PageResponse<ZiyaretciDto> daireyeGore(Long daireId, Integer page, Integer size, String sort) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page,
                size == null ? 20 : size,
                (sort == null || sort.isBlank()) ? Sort.by(Sort.Direction.ASC, "ad", "soyad")
                        : Sort.by(sort.split(",")));
        Page<Ziyaretci> p = ziyaretciRepository.findByDaire_IdAndDurumNot(daireId, Durum.SILINDI, pageable);
        return PageResponse.of(p.map(ziyaretciMapper::toDto));
    }

    @Override
    public PageResponse<ZiyaretciDto> durumaGore(ZiyaretciDurum durum, Integer page, Integer size, String sort) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page,
                size == null ? 20 : size,
                (sort == null || sort.isBlank()) ? Sort.by(Sort.Direction.ASC, "ad", "soyad")
                        : Sort.by(sort.split(",")));
        Page<Ziyaretci> p = ziyaretciRepository.findByZiyaretciDurumAndDurumNot(durum, Durum.SILINDI, pageable);
        return PageResponse.of(p.map(ziyaretciMapper::toDto));
    }

    @Transactional
    @Override
    public ZiyaretciDto isaretleGeldi(Long ziyaretciId, Long daireId, String aciklama) {
        Ziyaretci z = ziyaretciRepository.findById(ziyaretciId)
                .filter(e -> e.getDurum() != Durum.SILINDI)
                .orElseThrow(() -> new EntityNotFoundException("Ziyaretçi bulunamadı"));

        if (daireId == null) {
            throw new IllegalArgumentException("GELDI durumuna almak için daireId zorunludur");
        }
        z.setZiyaretciDurum(ZiyaretciDurum.GELDI);
        z.setDaire(Daire.builder().id(daireId).build());
        if (aciklama != null && !aciklama.isBlank()) z.setAciklama(aciklama);

        return ziyaretciMapper.toDto(ziyaretciRepository.save(z));
    }

    @Transactional
    @Override
    public ZiyaretciDto isaretleCikti(Long ziyaretciId, String aciklama) {
        Ziyaretci z = ziyaretciRepository.findById(ziyaretciId)
                .filter(e -> e.getDurum() != Durum.SILINDI)
                .orElseThrow(() -> new EntityNotFoundException("Ziyaretçi bulunamadı"));

        z.setZiyaretciDurum(ZiyaretciDurum.CIKTI);
        // İstersek daire’yi de boşaltırız:
        z.setDaire(null);
        if (aciklama != null && !aciklama.isBlank()) z.setAciklama(aciklama);

        return ziyaretciMapper.toDto(ziyaretciRepository.save(z));
    }

}
