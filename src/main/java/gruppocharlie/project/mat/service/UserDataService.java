package gruppocharlie.project.mat.service;

import gruppocharlie.project.mat.model.UserData;
import gruppocharlie.project.mat.repository.UserDataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataService{

    private final UserDataRepository userDataRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataService(UserDataRepository userDataRepository, PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //registrazione di un nuovo utente

    public UserData registerUser(UserData userData) {

        //Ottieni username e password dall'oggetto UserData

        String username = userData.getUsername();
        String password = userData.getPassword();


        if (userDataRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("username already taken");
        }
        UserData newUser = new UserData(username,passwordEncoder.encode(password));
        return userDataRepository.save(newUser);
    }

    //Trova un utente per username

    public Optional<UserData> findByUsername(String username) {
        return userDataRepository.findByUsername(username);
    }
}
