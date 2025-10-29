package com.gtombul.siteyonetimi.mapper.duyuru;

import com.gtombul.siteyonetimi.dto.dosya.DosyaResponseDto;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruCreateRequest;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruResponse;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruUpdateRequest;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper; // <-- BU SATIRI EKLEDİK
import com.gtombul.siteyonetimi.mapper.dosya.DosyaMapper;
import com.gtombul.siteyonetimi.model.duyuru.Duyuru;
import com.gtombul.siteyonetimi.model.duyuru.DuyuruDosyasi;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {DosyaMapper.class})
public interface DuyuruMapper extends BaseMapper<Duyuru, DuyuruResponse> { // <-- BaseMapper'ı IMPLEMENTE ET

    // --- 1. ÖZEL METOTLAR (Bizim özel 'olustur' ve 'guncelle' servislerimiz için) ---

    Duyuru createRequestToEntity(DuyuruCreateRequest createRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRequestToEntity(DuyuruUpdateRequest updateRequest, @MappingTarget Duyuru entity);


    // --- 2. BASEMAPPER SÖZLEŞMESİ METOTLARI (BaseService'in 'get', 'list', 'search' metotları için) ---

    /**
     * Entity -> Response DTO (BaseService'in `get` ve `list` metotları bunu kullanacak)
     * (Eski 'entityToResponse' metodumuzun adını 'toDto' olarak değiştirdik)
     */
    @Override
    @Mapping(target = "dosyalar", source = "dosyalar", qualifiedByName = "duyuruDosyalarindanDosyaDtoSetine")
    DuyuruResponse toDto(Duyuru entity);

    /**
     * Response DTO -> Entity (BaseService'in 'create' ve 'update' metotları bunu ister)
     * ANCAK: Biz bu metotları zaten ezdik ve kullanmıyoruz.
     * Mimarimiz gereği Response'dan Entity'ye dönüşüm olmamalıdır.
     * Bu yüzden bu metodu bilinçli olarak desteklemediğimizi belirtiyoruz.
     */
    @Override
    default Duyuru toEntity(DuyuruResponse dto) {
        throw new UnsupportedOperationException("DuyuruResponse'dan Entity'ye dönüşüm desteklenmiyor. Lütfen DuyuruCreateRequest kullanın.");
    }

    /**
     * BaseMapper'daki bu metot da sözleşme gereği zorunludur.
     * Bunu da bilinçli olarak desteklemiyoruz.
     */
    @Override
    default void updateEntityFromDto(DuyuruResponse dto, @MappingTarget Duyuru entity) {
        throw new UnsupportedOperationException("DuyuruResponse'dan Entity'ye güncelleme desteklenmiyor. Lütfen DuyuruUpdateRequest kullanın.");
    }


    // --- 3. ÖZEL YARDIMCI METOT (@Named) ---

    @Named("duyuruDosyalarindanDosyaDtoSetine")
    default Set<DosyaResponseDto> duyuruDosyalarindanDosyaDtoSetine(Set<DuyuruDosyasi> duyuruDosyalari) {
        if (duyuruDosyalari == null) {
            return null;
        }
        // Merkezi DosyaMapper'ı al
        DosyaMapper dosyaMapper = Mappers.getMapper(DosyaMapper.class);

        return duyuruDosyalari.stream()
                .map(DuyuruDosyasi::getDosya) // DuyuruDosyasi -> Dosya
                .map(dosyaMapper::toDto)      // Dosya -> DosyaResponseDto (URL'ler dahil)
                .collect(Collectors.toSet());
    }
}