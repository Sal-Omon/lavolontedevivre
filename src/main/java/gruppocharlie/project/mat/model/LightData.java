package gruppocharlie.project.mat.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LightData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double daylightHours; // h
    private double nightHours;    // h

    private LocalDateTime timestamp;
}