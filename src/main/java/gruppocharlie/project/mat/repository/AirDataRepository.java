package gruppocharlie.project.mat.repository;

import gruppocharlie.project.mat.model.AirData;
import gruppocharlie.project.mat.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirDataRepository extends JpaRepository<AirData, Long> {

    List<AirData> findByUser(UserData user);
}

//Spring Data JPA genera automaticamente le query SQL per trovare, salvare e cancellare dati.
//I metodi findByUser(UserData user) permettono di recuperare solo i dati dell'utente autenticato.