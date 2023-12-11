package com.flowershop.back.domain.flower;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "flowers")
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
    @Column(columnDefinition = "LONGBLOB")
    private byte[] file;
    private String fileName;

    public Flowers(FlowerGetDatabase data){
        this.fileName = data.fileName();
        this.file = data.file();
    }
}