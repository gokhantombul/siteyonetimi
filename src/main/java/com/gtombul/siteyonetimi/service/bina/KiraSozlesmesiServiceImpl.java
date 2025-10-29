package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.KiraSozlesmesiDto;
import com.gtombul.siteyonetimi.mapper.bina.KiraSozlesmesiMapper;
import com.gtombul.siteyonetimi.model.bina.KiraSozlesmesi;
import com.gtombul.siteyonetimi.repository.bina.KiraSozlesmesiRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KiraSozlesmesiServiceImpl extends BaseServiceImpl<KiraSozlesmesi, KiraSozlesmesiDto, Long> implements KiraSozlesmesiService {

    private final KiraSozlesmesiRepository repository;
    private final KiraSozlesmesiMapper mapper;

    public KiraSozlesmesiServiceImpl(KiraSozlesmesiRepository repository, KiraSozlesmesiMapper mapper) {
        super(repository, mapper, KiraSozlesmesi.class);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<KiraSozlesmesiDto> aktifSozlesmeler(Long daireId, LocalDate tarih) {
        return repository.findAktifSozlesmeler(daireId, tarih)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void tamaminiKaydet(List<KiraSozlesmesiDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
