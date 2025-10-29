package com.gtombul.siteyonetimi.controller.demirbas;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.demirbas.StokHareketDto;
import com.gtombul.siteyonetimi.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/demirbas/stok-hareket")
public class StokHareketController extends BaseController<StokHareketDto, Long> {

    protected StokHareketController(BaseService<StokHareketDto, Long> service) {
        super(service);
    }

}
