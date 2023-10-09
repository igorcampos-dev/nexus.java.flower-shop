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
    private String image;
    private String name;

    public Flowers(FlowerDTO data){
        this.name = data.name();
        this.image = data.image();
    }
}