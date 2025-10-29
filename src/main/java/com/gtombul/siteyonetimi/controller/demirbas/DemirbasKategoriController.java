package com.gtombul.siteyonetimi.controller.demirbas;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.demirbas.DemirbasKategoriDto;
import com.gtombul.siteyonetimi.service.demirbas.DemirbasKategoriService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/demirbas/kategori")
public class DemirbasKategoriController extends BaseController<DemirbasKategoriDto, Long> {

    protected DemirbasKategoriController(DemirbasKategoriService service) {
        super(service);
    }

}
