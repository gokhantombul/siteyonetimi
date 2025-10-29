package com.gtombul.siteyonetimi.controller.banka;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.banka.BankaDto;
import com.gtombul.siteyonetimi.service.banka.BankaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/banka")
public class BankaController extends BaseController<BankaDto, Long> {

    public BankaController(BankaService service) {
        super(service);
    }

}
