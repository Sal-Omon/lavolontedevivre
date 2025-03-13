package gruppocharlie.project.mat.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WindData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double speed; // Velocità vento (m/s)

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData user;

    public WindData(double speed, LocalDateTime timestamp, UserData user) {
        this.speed = speed;
        this.timestamp = timestamp;
        this.user = user;
    }
}