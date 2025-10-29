package com.gtombul.siteyonetimi.service.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.DemirbasDto;
import com.gtombul.siteyonetimi.mapper.demirbas.DemirbasMapper;
import com.gtombul.siteyonetimi.model.demirbas.Demirbas;
import com.gtombul.siteyonetimi.repository.demirbas.DemirbasRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemirbasServiceImpl extends BaseServiceImpl<Demirbas, DemirbasDto, Long> implements DemirbasService {

    private final DemirbasRepository repository;

    public DemirbasServiceImpl(DemirbasRepository repository, DemirbasMapper mapper) {
        super(repository, mapper, Demirbas.class);
        this.repository = repository;
    }

    // ad soft-delete ile unique olsun istiyorsan burada kontrol et
    @Override
    @Transactional
    public DemirbasDto create(DemirbasDto dto) {
        if (repository.existsByAdIgnoreCase(dto.getAd())) {
            // Durum != SILINDI olanları filtreleyen BaseRepository varsa sorun yok.
            throw new IllegalArgumentException("Aynı adla kayıt mevcut: " + dto.getAd());
        }
        return super.create(dto);
    }

}