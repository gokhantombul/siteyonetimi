package com.gtombul.siteyonetimi.mapper.banka;

import com.gtombul.siteyonetimi.dto.banka.BankaDto;
import com.gtombul.siteyonetimi.mapper.base.BaseMapper;
import com.gtombul.siteyonetimi.mapper.ilce.IlceMapper;
import com.gtombul.siteyonetimi.model.banka.Banka;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {IlceMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankaMapper extends BaseMapper<Banka, BankaDto> {
//Burada önemli olan uses = {IlceMapper.class} kısmı.
//Çünkü Banka içindeki Ilce ↔ IlceDto dönüşümünü senin zaten yazdığın IlceMapper halledecek.

}
