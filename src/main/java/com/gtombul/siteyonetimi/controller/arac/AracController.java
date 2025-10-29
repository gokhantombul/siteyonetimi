package com.gtombul.siteyonetimi.controller.arac;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.arac.AracDto;
import com.gtombul.siteyonetimi.service.arac.AracService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/arac")
public class AracController extends BaseController<AracDto, Long> {

    protected AracController(AracService service) {
        super(service);
    }

}