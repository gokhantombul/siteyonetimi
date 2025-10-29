package com.gtombul.siteyonetimi.service.ilan;

import com.gtombul.siteyonetimi.dto.ilan.IlanResimYanitDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IlanResimService {
    List<IlanResimYanitDto> multipartYukle(Long ilanId, List<MultipartFile> dosyalar);

    List<IlanResimYanitDto> onYukluKaynakIliskilendir(Long ilanId, List<Long> kaynakIdListesi);

    void sil(Long ilanId, Long resimId, boolean fizikseliDeSil);

    List<IlanResimYanitDto> listele(Long ilanId);
}
