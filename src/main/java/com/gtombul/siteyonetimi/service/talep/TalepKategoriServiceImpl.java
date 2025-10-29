package com.gtombul.siteyonetimi.service.talep;

import com.gtombul.siteyonetimi.dto.talep.TalepKategoriDto;
import com.gtombul.siteyonetimi.mapper.talep.TalepKategoriMapper;
import com.gtombul.siteyonetimi.model.talep.TalepKategori;
import com.gtombul.siteyonetimi.repository.talep.TalepKategoriRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TalepKategoriServiceImpl
        extends BaseServiceImpl<TalepKategori, TalepKategoriDto, Long> // <-- BaseServiceImpl'i kullanıyoruz
        implements TalepKategoriService {

    public TalepKategoriServiceImpl(TalepKategoriRepository repository, TalepKategoriMapper mapper) {
        // BaseServiceImpl'in constructor'ına repository, mapper ve entity class'ını iletiyoruz.
        // DTO'muz BaseDto'dan, Mapper'ımız BaseMapper'dan miras aldığı için
        // artık mimariyle tam uyumluyuz. Hata yok.
        super(repository, mapper, TalepKategori.class);
    }
}