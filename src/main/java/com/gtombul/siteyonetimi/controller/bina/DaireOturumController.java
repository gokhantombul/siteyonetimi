package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.DaireOturumDto;
import com.gtombul.siteyonetimi.service.bina.DaireOturumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/oturum")
public class DaireOturumController extends BaseController<DaireOturumDto, Long> {

    private final DaireOturumService daireOturumService;

    protected DaireOturumController(DaireOturumService service) {
        super(service);
        this.daireOturumService = service;
    }

    @GetMapping("/daire/{daireId}/aktif")
    public List<DaireOturumDto> aktifByDaire(@PathVariable Long daireId,
                                             @RequestParam(required = false) String tarih) {
        LocalDate t = tarih == null ? LocalDate.now() : LocalDate.parse(tarih);
        return daireOturumService.aktifOturumlarByDaire(daireId, t);
    }

    @GetMapping("/kisi/{kisiId}/aktif")
    public List<DaireOturumDto> aktifByKisi(@PathVariable Long kisiId,
                                            @RequestParam(required = false) String tarih) {
        LocalDate t = tarih == null ? LocalDate.now() : LocalDate.parse(tarih);
        return daireOturumService.aktifOturumlarByKisi(kisiId, t);
    }

}
