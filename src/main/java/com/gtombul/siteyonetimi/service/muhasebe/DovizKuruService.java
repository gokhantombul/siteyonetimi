package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.model.enums.ParaBirimi;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DovizKuruService {

    BigDecimal kurAl(ParaBirimi kaynak, ParaBirimi hedef, LocalDate tarih);

}
