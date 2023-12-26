package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.IsValid;
import com.flowershop.back.configuration.annotations.ValidBase64;
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
    @ValidBase64
    @Column(columnDefinition = "LONGBLOB", unique = true)
    private String file;

    @IsValid
    @Column(unique = true)
    private String filename;

    public Flowers(FlowerGetDatabase flowerGet){
        this.filename = flowerGet.filename();
        this.file = flowerGet.file();
    }
}