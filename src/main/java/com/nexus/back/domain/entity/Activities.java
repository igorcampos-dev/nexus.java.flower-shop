package com.nexus.back.domain.entity;

import com.nexus.validations.NonNullOrBlank;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @NonNullOrBlank
    private String user;
    @NonNullOrBlank
    private String remittent;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime datetime;

}
