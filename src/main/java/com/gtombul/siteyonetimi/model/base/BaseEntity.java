package com.gtombul.siteyonetimi.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gtombul.siteyonetimi.model.enums.Durum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = BaseEntityListener.class)
@SuperBuilder
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "olusturma_tarihi", updatable = false, nullable = false)
    @JsonProperty("olusturma_tarihi")
    @CreatedDate
    private LocalDateTime olusturmaTarihi;

    @Column(name = "guncelleme_tarihi")
    @JsonProperty("guncelleme_tarihi")
    @LastModifiedDate
    private LocalDateTime guncellemeTarihi;

    @CreatedBy
    @Column(name = "olusturan", nullable = false, updatable = false)
    private Long olusturan;

    @LastModifiedBy
    @Column(name = "guncelleyen")
    private Long guncelleyen;

    @Column(name = "durum", length = 10, nullable = false)
    @ColumnDefault("'AKTIF'")
    @Enumerated(EnumType.STRING)
    private Durum durum;

}
