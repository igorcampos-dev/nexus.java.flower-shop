package com.flowershop.back.domain.activities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "activities")
@Table(name = "activities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String user;
    private String remetente;
    private LocalDateTime localDateTime;

    public Activities( ActivitiesDTO activitiesDTO) {
        this.user = activitiesDTO.user();
        this.remetente = activitiesDTO.remetente();
        this.localDateTime = activitiesDTO.localDateTime();
    }
}
