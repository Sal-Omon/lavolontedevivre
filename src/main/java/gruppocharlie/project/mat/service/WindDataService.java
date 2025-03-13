package gruppocharlie.project.mat.service;

import gruppocharlie.project.mat.model.UserData;
import gruppocharlie.project.mat.model.WindData;
import gruppocharlie.project.mat.repository.WindDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindDataService {
    private final WindDataRepository windDataRepository;

    public WindDataService(WindDataRepository windDataRepository) {
        this.windDataRepository = windDataRepository;
    }

    //salvataggio di una nuova velocità del vento
    public void saveWindData(WindData windData) {
        windDataRepository.save(windData);
    }

    //ricerca di tutte le velocità del vento di un utente
    public List<WindData> getWindDataByUser(UserData user) {
        return windDataRepository.findByUser(user);
    }
}
