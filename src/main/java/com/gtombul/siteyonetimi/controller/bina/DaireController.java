package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.DaireDto;
import com.gtombul.siteyonetimi.service.bina.DaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/daire")
public class DaireController extends BaseController<DaireDto, Long> {

    protected DaireController(DaireService service) {
        super(service);
    }

}
