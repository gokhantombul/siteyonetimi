package com.gtombul.siteyonetimi.controller.bina;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.bina.MulkSahipligiDto;
import com.gtombul.siteyonetimi.service.bina.MulkSahipligiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/mulk-sahiplikligi")
public class MulkSahipligiController extends BaseController<MulkSahipligiDto, Long> {

    private final MulkSahipligiService mulkSahipligiService;

    public MulkSahipligiController(MulkSahipligiService mulkSahipligiService) {
        super(mulkSahipligiService);
        this.mulkSahipligiService = mulkSahipligiService;
    }

    @GetMapping("/daire/{daireId}/aktif")
    public List<MulkSahipligiDto> aktifSahipler(@PathVariable Long daireId,
                                                @RequestParam(required = false) String tarih) {
        LocalDate t = tarih == null ? LocalDate.now() : LocalDate.parse(tarih);
        return mulkSahipligiService.aktifSahipler(daireId, t);
    }

}
