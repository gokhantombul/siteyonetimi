package com.gtombul.siteyonetimi.controller.personel;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.personel.PersonelDto;
import com.gtombul.siteyonetimi.service.personel.PersonelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/personel")
public class PersonelController extends BaseController<PersonelDto, Long> {

    public PersonelController(PersonelService service) {
        super(service);
    }

}