package com.gtombul.siteyonetimi.service.personel;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.personel.PersonelDto;
import com.gtombul.siteyonetimi.mapper.personel.PersonelMapper;
import com.gtombul.siteyonetimi.model.personel.Personel;
import com.gtombul.siteyonetimi.repository.personel.PersonelRepository;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PersonelServiceImpl extends BaseServiceImpl<Personel, PersonelDto, Long> implements PersonelService {

    public PersonelServiceImpl(PersonelRepository repository, PersonelMapper mapper) {
        super(repository, mapper, Personel.class);
    }

    @Override
    public PageResponse<PersonelDto> search(SearchRequest request) {
        return super.search(request);
    }
}
