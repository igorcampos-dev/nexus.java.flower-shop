package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;
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
    private byte[] file;

    @isValid
    @Column(unique = true)
    private String filename;

    public Flowers(FlowerGetDatabase data){
        this.filename = data.fileName();
        this.file = data.file();
    }
}