package com.gtombul.siteyonetimi.controller.demirbas;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.demirbas.DemirbasDto;
import com.gtombul.siteyonetimi.service.demirbas.DemirbasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/demirbas")
public class DemirbasController extends BaseController<DemirbasDto, Long> {

    protected DemirbasController(DemirbasService service) {
        super(service);
    }

}
