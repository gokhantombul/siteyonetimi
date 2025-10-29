package com.gtombul.siteyonetimi.controller.il;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.adres.il.IlDto;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.service.il.IlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/il")
public class IlController extends BaseController<IlDto, Long> {

    private final IlService ilService;

    protected IlController(IlService service) {
        super(service);
        this.ilService = service;
    }

    @GetMapping("/plaka/{plakaKodu}")
    public ResponseEntity<IlDto> getirPlakaVeDurumaGore(@PathVariable int plakaKodu) {
        if (plakaKodu < 1 || plakaKodu > 81) return ResponseEntity.badRequest().build();
        return ilService.findByPlakaAndDurum(plakaKodu, Durum.AKTIF)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*@PostMapping(value = "/ara")
    public ResponseEntity<Page<IlDto>> search(@RequestBody SearchRequest request) {
        return new ResponseEntity<>(ilService.ara(request).map(ilMapper::toDto), HttpStatus.OK);
    }*/

}
