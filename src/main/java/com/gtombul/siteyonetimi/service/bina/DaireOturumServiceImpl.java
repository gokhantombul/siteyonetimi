package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.DaireOturumDto;
import com.gtombul.siteyonetimi.mapper.bina.DaireOturumMapper;
import com.gtombul.siteyonetimi.model.bina.DaireOturum;
import com.gtombul.siteyonetimi.repository.bina.DaireOturumRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DaireOturumServiceImpl extends BaseServiceImpl<DaireOturum, DaireOturumDto, Long> implements DaireOturumService {

    private final DaireOturumRepository repository;
    private final DaireOturumMapper mapper;

    public DaireOturumServiceImpl(DaireOturumRepository repository, DaireOturumMapper mapper) {
        super(repository, mapper, DaireOturum.class);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DaireOturumDto> aktifOturumlarByDaire(Long daireId, LocalDate tarih) {
        return repository.findAktifOturumlar(daireId, tarih)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DaireOturumDto> aktifOturumlarByKisi(Long kisiId, LocalDate tarih) {
        return repository.findKisininAktifOturumlari(kisiId, tarih)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void tamaminiKaydet(List<DaireOturumDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
