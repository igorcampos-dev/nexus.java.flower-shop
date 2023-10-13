package com.flowershop.back.domain.activities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity(name = "activities")
@Table(name = "activities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String user;
    private String remittent;
    private LocalDateTime localDateTime;

}
