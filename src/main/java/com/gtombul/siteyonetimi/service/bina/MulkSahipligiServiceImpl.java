package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.MulkSahipligiDto;
import com.gtombul.siteyonetimi.mapper.bina.MulkSahipligiMapper;
import com.gtombul.siteyonetimi.model.bina.MulkSahipligi;
import com.gtombul.siteyonetimi.repository.bina.MulkSahipligiRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MulkSahipligiServiceImpl extends BaseServiceImpl<MulkSahipligi, MulkSahipligiDto, Long> implements MulkSahipligiService {

    private final MulkSahipligiRepository repository;
    private final MulkSahipligiMapper mapper;

    public MulkSahipligiServiceImpl(MulkSahipligiRepository repository, MulkSahipligiMapper mapper) {
        super(repository, mapper, MulkSahipligi.class);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<MulkSahipligiDto> aktifSahipler(Long daireId, LocalDate tarih) {
        return repository.findAktifSahipler(daireId, tarih)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

}
