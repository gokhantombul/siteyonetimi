package com.gtombul.siteyonetimi.service.ilan;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.ilan.IlanDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanGuncelleDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanKaydetDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanListelemeDto;
import com.gtombul.siteyonetimi.mapper.ilan.IlanMapper;
import com.gtombul.siteyonetimi.model.ilan.*;
import com.gtombul.siteyonetimi.repository.ilan.IlanRepository;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.search.SpecificationBuilder;
import com.gtombul.siteyonetimi.storage.ilan.DosyaPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IlanServiceImpl implements IlanService {

    private final IlanRepository ilanRepository;
    private final IlanMapper ilanMapper;
    private final DosyaPort dosyaPort;

    @Transactional
    @Override
    public IlanDto olustur(IlanKaydetDto dto) {
        Ilan e = new Ilan();
        e.setBaslik(dto.getBaslik());
        e.setAciklama(dto.getAciklama());
        e.setTuru(dto.getTuru());
        e.setFiyat(dto.getFiyat());
        e.setYayimTarihi(java.time.LocalDate.now());
        e.setOnayDurumu(IlanOnayDurumu.TASLAK);
        e.setIlanDurum(IlanDurum.AKTIF);

        var kategori = new IlanKategori();
        kategori.setId(dto.getKategoriId());
        e.setKategori(kategori);

        if (dto.getOnYukluResimKaynakIdListesi() != null) {
            int i = 0;
            for (Long kid : dto.getOnYukluResimKaynakIdListesi()) {
                e.getResimler().add(IlanResim.builder()
                        .ilan(e).kaynakKayitId(kid).siraNo(i++).build());
            }
        }

        var kayit = ilanRepository.save(e);
        return ilanMapper.toDto(kayit, dosyaPort);
    }

    @Transactional
    @Override
    public IlanDto guncelle(IlanGuncelleDto dto) {
        var e = ilanRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + dto.getId()));

        e.setBaslik(dto.getBaslik());
        e.setAciklama(dto.getAciklama());
        e.setTuru(dto.getTuru());
        e.setFiyat(dto.getFiyat());
        e.setIlanDurum(dto.getIlanDurum());

        var kategori = new IlanKategori();
        kategori.setId(dto.getKategoriId());
        e.setKategori(kategori);

        e.getResimler().clear();
        if (dto.getResimKaynakIdListesi() != null) {
            int i = 0;
            for (Long kid : dto.getResimKaynakIdListesi()) {
                e.getResimler().add(IlanResim.builder()
                        .ilan(e).kaynakKayitId(kid).siraNo(i++).build());
            }
        }

        var g = ilanRepository.save(e);
        return ilanMapper.toDto(g, dosyaPort);
    }

    @Transactional(readOnly = true)
    @Override
    public IlanDto getir(Long id) {
        var e = ilanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + id));
        return ilanMapper.toDto(e, dosyaPort);
    }

    @Transactional
    @Override
    public void sil(Long id) {
        //super.deleteById(id); // soft delete
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<IlanListelemeDto> ara(SearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.page() == null ? 0 : request.page(),
                request.size() == null ? 20 : request.size(),
                parseSort(request.sort())
        );

        var spec = SpecificationBuilder.build(Ilan.class, request.keyword(), request.filters())
                .and((r, q, cb) -> cb.equal(r.get("onayDurumu"), IlanOnayDurumu.ONAYLI))
                .and((r, q, cb) -> cb.equal(r.get("ilanDurum"), IlanDurum.AKTIF));

        Page<Ilan> page = ilanRepository.findAll(spec, pageable);
        var content = ilanMapper.toListelemeDto(page.getContent(), dosyaPort);
        return new PageResponse<>(content, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @Override
    public IlanDto yayinTalepEt(Long ilanId) {
        var e = ilanRepository.findById(ilanId)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + ilanId));
        if (e.getOnayDurumu() == IlanOnayDurumu.ONAYLI) {
            return ilanMapper.toDto(e, dosyaPort);
        }
        e.setOnayDurumu(IlanOnayDurumu.INCELEME);
        e.setReddetmeNedeni(null);
        var g = ilanRepository.save(e);
        log.info("İlan INCELEME’ye gönderildi: {}", ilanId);
        return ilanMapper.toDto(g, dosyaPort);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public IlanDto onayla(Long ilanId, String aciklamaOpsiyonel) {
        var e = ilanRepository.findById(ilanId)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + ilanId));
        e.setOnayDurumu(IlanOnayDurumu.ONAYLI);
        e.setReddetmeNedeni(null);
        // e.setOnaylayanKisi(authService.getCurrentKisi());
        e.setOnayTarihi(java.time.LocalDateTime.now());
        if (e.getIlanDurum() == null) e.setIlanDurum(IlanDurum.AKTIF);
        var g = ilanRepository.save(e);
        log.info("İlan ONAYLI: {}", ilanId);
        return ilanMapper.toDto(g, dosyaPort);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public IlanDto reddet(Long ilanId, String reddetmeNedeni) {
        var e = ilanRepository.findById(ilanId)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + ilanId));
        e.setOnayDurumu(IlanOnayDurumu.REDDEDILDI);  // <- Doğru enum
        // e.setOnaylayanKisi(authService.getCurrentKisi());
        e.setOnayTarihi(java.time.LocalDateTime.now());
        e.setReddetmeNedeni(reddetmeNedeni);
        var g = ilanRepository.save(e);
        log.info("İlan REDDEDILDI: {} - {}", ilanId, reddetmeNedeni);
        return ilanMapper.toDto(g, dosyaPort);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public PageResponse<IlanListelemeDto> adminIncelemeListesi(SearchRequest request) {
        Specification<Ilan> spec = SpecificationBuilder.build(Ilan.class, request.keyword(), request.filters()).and((r, q, cb) -> cb.equal(r.get("onayDurumu"), IlanOnayDurumu.INCELEME));
        Pageable pageable = pageable(request.sort(),
                request.page() == null ? 0 : request.page(),
                request.size() == null ? 20 : request.size());
        Page<Ilan> page = ilanRepository.findAll(spec, pageable);
        var content = ilanMapper.toListelemeDto(page.getContent(), dosyaPort);
        return new PageResponse<>(
                content,
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

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "createdAt");
        String[] p = sort.split(",");
        return (p.length == 2) ? Sort.by(Sort.Direction.fromString(p[1]), p[0])
                : Sort.by(Sort.Direction.ASC, p[0]);
    }

}
