package com.gtombul.siteyonetimi.controller.talep;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.talep.TalepKategoriDto;
import com.gtombul.siteyonetimi.service.talep.TalepKategoriService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Mimarimize uygun: BaseController'ı kullanıyoruz.
@RestController
@RequestMapping("/api/v1/talep-kategorileri")
public class TalepKategoriController extends BaseController<TalepKategoriDto, Long> {

    // BaseController (list, get, create, update, delete, search) tüm standart
    // endpoint'leri sağlar. Ekstra bir şeye gerek yok.
    public TalepKategoriController(TalepKategoriService service) {
        super(service);
    }
}