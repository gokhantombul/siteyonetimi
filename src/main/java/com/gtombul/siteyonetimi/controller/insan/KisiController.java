package com.gtombul.siteyonetimi.controller.insan;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.insan.KisiDto;
import com.gtombul.siteyonetimi.service.insan.KisiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/kisi")
public class KisiController extends BaseController<KisiDto, Long> {

    protected KisiController(KisiService service) {
        super(service);
    }

}
