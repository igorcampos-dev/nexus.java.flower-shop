package com.nexus.back.domain.entity;

import com.nexus.back.domain.dto.flower.FlowerGetDatabase;
import com.nexus.validations.NonNullOrBlank;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "flowers", indexes = {
        @Index(name = "id_idx", columnList = "id", unique = true),
        @Index(name = "filename_idx", columnList = "filename", unique = true)
})
@Entity(name = "flowers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Data
public class Flowers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Lob
    @Column(columnDefinition = "LONGBLOB", unique = true)
    private String file;

    @NonNullOrBlank
    @Column(unique = true)
    private String filename;

    public Flowers(FlowerGetDatabase flowerGet){
        this.filename = flowerGet.filename();
        this.file = flowerGet.file();
    }
}