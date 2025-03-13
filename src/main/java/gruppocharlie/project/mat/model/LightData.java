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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData user;

    public LightData(double daylightHours, double nightHours, LocalDateTime timestamp, UserData user) {
        this.daylightHours = daylightHours;
        this.nightHours = nightHours;
        this.timestamp = timestamp;
        this.user = user;
    }
}