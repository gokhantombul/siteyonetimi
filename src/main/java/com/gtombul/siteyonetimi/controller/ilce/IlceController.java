package com.gtombul.siteyonetimi.controller.ilce;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.service.ilce.IlceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/ilce")
public class IlceController extends BaseController<IlceDto, Long> {

    private final IlceService ilceService;

    protected IlceController(IlceService service) {
        super(service);
        this.ilceService = service;
    }

    @GetMapping("/il-id/{ilId}")
    public ResponseEntity<List<IlceDto>> getirPlakaVeDurumaGore(@PathVariable Long ilId) {
        return ResponseEntity.ok(ilceService.findByIlIdAndDurum(ilId, Durum.AKTIF));
    }

}
