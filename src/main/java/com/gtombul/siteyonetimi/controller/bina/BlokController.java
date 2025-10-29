package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.BlokDto;
import com.gtombul.siteyonetimi.service.bina.BlokService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/blok")
public class BlokController extends BaseController<BlokDto, Long> {

    protected BlokController(BlokService service) {
        super(service);
    }

}
