package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.KiraSozlesmesiDto;
import com.gtombul.siteyonetimi.service.bina.KiraSozlesmesiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/kira-sozlesmesi")
public class KiraSozlesmesiController extends BaseController<KiraSozlesmesiDto, Long> {

    private final KiraSozlesmesiService service;

    public KiraSozlesmesiController(KiraSozlesmesiService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/daire/{daireId}/aktif")
    public List<KiraSozlesmesiDto> aktif(@PathVariable Long daireId,
                                         @RequestParam(required = false) String tarih) {
        LocalDate t = tarih == null ? LocalDate.now() : LocalDate.parse(tarih);
        return service.aktifSozlesmeler(daireId, t);
    }

}
