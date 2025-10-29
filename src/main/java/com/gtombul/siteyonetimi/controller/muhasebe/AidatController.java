package com.gtombul.siteyonetimi.controller.muhasebe;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.muhasebe.AidatPlaniDto;
import com.gtombul.siteyonetimi.service.muhasebe.AidatPlaniService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/aidat")
public class AidatController extends BaseController<AidatPlaniDto, Long> {

    private final AidatPlaniService aidatPlaniService;

    protected AidatController(AidatPlaniService aidatPlaniService) {
        super(aidatPlaniService);
        this.aidatPlaniService = aidatPlaniService;
    }

    @PostMapping("/plan")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<AidatPlaniDto> planOlustur(@RequestBody AidatPlaniDto dto) {
        return ResponseEntity.ok(aidatPlaniService.olustur(dto));
    }

    @PostMapping("/olustur/{planId}")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<Void> planIcinAidatOlustur(
            @PathVariable Long planId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth donem) {
        aidatPlaniService.donemIcinAidatOlustur(planId, donem);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/olustur")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<Void> tumPlanlarIcinAidatOlustur(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth donem) {
        aidatPlaniService.tumPlanlarIcinAidatOlustur(donem);
        return ResponseEntity.ok().build();
    }

}
