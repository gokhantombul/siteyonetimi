package com.gtombul.siteyonetimi.dto.muhasebe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MutabakatSonucuDto {

    private List<String> eslesenRefler;

    private List<String> eslesmeyenRefler;

}