package gruppocharlie.project.mat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

    // Costruttore senza argomenti richiesto da JPA
    public UserData() {
    }

    // Costruttore per inizializzare username, password e isAdmin
    public UserData(String username, String encryptedPassword, boolean isAdmin) {
        this.username = username;
        this.password = encryptedPassword;
        this.isAdmin = isAdmin;
    }
}
