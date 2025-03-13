package gruppocharlie.project.mat.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature; // °C
    private double humidity;    // %

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData user;

    public AirData(double temperature, double humidity, LocalDateTime timestamp, UserData user) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.timestamp = timestamp;
        this.user = user;
    }
}
