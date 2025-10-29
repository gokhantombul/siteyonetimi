package com.gtombul.siteyonetimi.controller.demirbas;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.demirbas.DepoDto;
import com.gtombul.siteyonetimi.service.demirbas.DepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/demirbas/depo")
public class DepoController extends BaseController<DepoDto, Long> {

    protected DepoController(DepoService service) {
        super(service);
    }

}
