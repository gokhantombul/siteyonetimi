package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.SiteDto;
import com.gtombul.siteyonetimi.service.bina.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/site")
public class SiteController extends BaseController<SiteDto, Long> {

    protected SiteController(SiteService service) {
        super(service);
    }

}
