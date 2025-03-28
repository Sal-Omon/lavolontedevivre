package gruppocharlie.project.mat.util;

import gruppocharlie.project.mat.model.UserData;
import gruppocharlie.project.mat.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crea l'utente admin se non esiste
        if (userRepository.findByUsername("admin") == null) {
            UserData admin = new UserData();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setAdmin(true);
            userRepository.save(admin);
            System.out.println("Utente admin creato.");
        } else {
            System.out.println("Utente admin già esistente.");
        }

        // Crea l'utente normale se non esiste
        if (userRepository.findByUsername("test") == null) {
            UserData user = new UserData();
            user.setUsername("test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setAdmin(false);
            userRepository.save(user);
            System.out.println("Utente test creato.");
        } else {
            System.out.println("Utente test già esistente.");
        }
    }
}