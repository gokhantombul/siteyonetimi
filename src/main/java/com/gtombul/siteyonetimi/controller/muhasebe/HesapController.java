package com.gtombul.siteyonetimi.controller.muhasebe;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.muhasebe.EkstreSatirDto;
import com.gtombul.siteyonetimi.dto.muhasebe.HesapDto;
import com.gtombul.siteyonetimi.service.muhasebe.HesapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/hesap")
public class HesapController extends BaseController<HesapDto, Long> {

    private final HesapService hesapService;

    protected HesapController(HesapService hesapService) {
        super(hesapService);
        this.hesapService = hesapService;
    }

    @GetMapping("/{id}/bakiye")
    public ResponseEntity<BigDecimal> bakiye(@PathVariable Long id) {
        return ResponseEntity.ok(hesapService.bakiye(id));
    }

    @GetMapping("/{id}/ekstre")
    public ResponseEntity<List<EkstreSatirDto>> ekstre(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate baslangic,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bitis) {
        return ResponseEntity.ok(hesapService.ekstre(id, baslangic, bitis));
    }

}