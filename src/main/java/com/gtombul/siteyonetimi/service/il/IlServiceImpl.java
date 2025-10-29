package com.gtombul.siteyonetimi.service.il;

import com.gtombul.siteyonetimi.dto.adres.il.IlDto;
import com.gtombul.siteyonetimi.mapper.il.IlMapper;
import com.gtombul.siteyonetimi.model.adres.Il;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.il.IlRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class IlServiceImpl extends BaseServiceImpl<Il, IlDto, Long> implements IlService {

    private final IlRepository ilRepository;
    private final IlMapper ilMapper;

    public IlServiceImpl(IlRepository ilRepository, IlMapper ilMapper) {
        super(ilRepository, ilMapper, Il.class);
        this.ilRepository = ilRepository;
        this.ilMapper = ilMapper;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Il il = getEntityAndDurum(id, Durum.AKTIF);
        il.setDurum(Durum.SILINDI);
        il.getIlceler().forEach(ilce -> {
            ilce.setDurum(Durum.SILINDI);     // soft delete
        });
        ilRepository.save(il);
    }

    @Override
    public Optional<IlDto> findByPlakaAndDurum(int plakaKodu, Durum durum) {
        return ilRepository.findByPlakaKoduAndDurum(plakaKodu, durum)
                .map(ilMapper::toDto);
    }

    @Override
    public void tamaminiKaydet(List<IlDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
