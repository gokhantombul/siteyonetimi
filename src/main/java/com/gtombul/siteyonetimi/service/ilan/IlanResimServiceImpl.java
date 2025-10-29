package com.gtombul.siteyonetimi.service.ilan;

import com.gtombul.siteyonetimi.dto.ilan.IlanResimYanitDto;
import com.gtombul.siteyonetimi.model.ilan.Ilan;
import com.gtombul.siteyonetimi.model.ilan.IlanResim;
import com.gtombul.siteyonetimi.repository.ilan.IlanRepository;
import com.gtombul.siteyonetimi.storage.ilan.DosyaPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IlanResimServiceImpl implements IlanResimService {

    private final IlanRepository ilanRepository;
    private final DosyaPort dosyaPort;

    @Transactional
    @Override
    public List<IlanResimYanitDto> multipartYukle(Long ilanId, List<MultipartFile> dosyalar) {
        if (ObjectUtils.isEmpty(dosyalar)) return List.of();
        Ilan ilan = ilanRepository.findById(ilanId)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + ilanId));

        int sira = ilan.getResimler().size();
        List<IlanResimYanitDto> yanit = new ArrayList<>();

        for (MultipartFile f : dosyalar) {
            Long kid = dosyaPort.yukleVeIdDondur(f, "ilan/"+ilanId);
            var r = IlanResim.builder().ilan(ilan).kaynakKayitId(kid).siraNo(sira++).build();
            ilan.getResimler().add(r);

            yanit.add(IlanResimYanitDto.builder()
                    .id(r.getId())
                    .kaynakKayitId(kid)
                    .url(dosyaPort.publicUrl(kid))
                    .siraNo(r.getSiraNo())
                    .build());
        }
        ilanRepository.save(ilan);
        log.info("İlana {} adet resim eklendi (multipart). ilanId={}", dosyalar.size(), ilanId);
        return yanit;
    }

    @Transactional
    @Override
    public List<IlanResimYanitDto> onYukluKaynakIliskilendir(Long ilanId, List<Long> kaynakIdListesi) {
        if (ObjectUtils.isEmpty(kaynakIdListesi)) return List.of();
        Ilan ilan = ilanRepository.findById(ilanId)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + ilanId));

        int sira = ilan.getResimler().size();
        List<IlanResimYanitDto> yanit = new ArrayList<>();

        for (Long kid : kaynakIdListesi) {
            var r = IlanResim.builder().ilan(ilan).kaynakKayitId(kid).siraNo(sira++).build();
            ilan.getResimler().add(r);
            yanit.add(IlanResimYanitDto.builder()
                    .id(r.getId())
                    .kaynakKayitId(kid)
                    .url(dosyaPort.publicUrl(kid))
                    .siraNo(r.getSiraNo())
                    .build());
        }
        ilanRepository.save(ilan);
        log.info("İlana {} adet resim ilişkilendirildi (önceden yüklenmiş). ilanId={}", kaynakIdListesi.size(), ilanId);
        return yanit;
    }

    @Transactional
    @Override
    public void sil(Long ilanId, Long resimId, boolean fizikseliDeSil) {
        Ilan ilan = ilanRepository.findById(ilanId)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + ilanId));

        Long kaynak = null;
        var it = ilan.getResimler().iterator();
        while (it.hasNext()) {
            var r = it.next();
            if (Objects.equals(r.getId(), resimId)) {
                kaynak = r.getKaynakKayitId();
                it.remove();
                break;
            }
        }
        // sıra normalize
        int i = 0;
        for (var r : ilan.getResimler()) r.setSiraNo(i++);

        ilanRepository.save(ilan);

        if (fizikseliDeSil && kaynak != null) {
            dosyaPort.fizikselSil(kaynak);
        }
        log.info("İlan resmi silindi. ilanId={}, resimId={}, fiziksel={}", ilanId, resimId, fizikseliDeSil);
    }

    @Transactional(readOnly = true)
    @Override
    public List<IlanResimYanitDto> listele(Long ilanId) {
        Ilan ilan = ilanRepository.findById(ilanId)
                .orElseThrow(() -> new EntityNotFoundException("İlan bulunamadı: " + ilanId));

        return ilan.getResimler().stream()
                .sorted(Comparator.comparing(IlanResim::getSiraNo, Comparator.nullsLast(Integer::compareTo)))
                .map(r -> IlanResimYanitDto.builder()
                        .id(r.getId())
                        .kaynakKayitId(r.getKaynakKayitId())
                        .url(dosyaPort.publicUrl(r.getKaynakKayitId()))
                        .siraNo(r.getSiraNo())
                        .build()).collect(Collectors.toUnmodifiableList());
    }

}
