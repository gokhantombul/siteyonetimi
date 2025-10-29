package com.gtombul.siteyonetimi.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean basarili;
    private T veri;
    private String mesaj;
    private final LocalDateTime zamanDamgasi = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().basarili(true).veri(data).build();
    }

    public static ApiResponse<Void> success() {
        return ApiResponse.<Void>builder().basarili(true).build();
    }
}