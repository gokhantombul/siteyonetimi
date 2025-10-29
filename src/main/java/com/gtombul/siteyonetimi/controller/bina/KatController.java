package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.KatDto;
import com.gtombul.siteyonetimi.service.bina.KatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/kat")
public class KatController extends BaseController<KatDto, Long> {

    protected KatController(KatService service) {
        super(service);
    }

}
