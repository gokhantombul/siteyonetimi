package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.FirmaDto;
import com.gtombul.siteyonetimi.service.bina.FirmaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/firma")
public class FirmaController extends BaseController<FirmaDto, Long> {

    protected FirmaController(FirmaService service) {
        super(service);
    }

}
