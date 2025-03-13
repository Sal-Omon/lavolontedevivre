package gruppocharlie.project.mat.service;

import gruppocharlie.project.mat.model.SoilData;
import gruppocharlie.project.mat.model.UserData;
import gruppocharlie.project.mat.repository.SoilDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoilDataService {

    private final SoilDataRepository soilDataRepository;

    public SoilDataService(SoilDataRepository soilDataRepository) {
        this.soilDataRepository = soilDataRepository;
    }
    //Salva un nuovo dato sul suolo

    public void saveSoilData(SoilData soilData) {
        soilDataRepository.save(soilData);
    }

    //Recupera tutti i dati sul suolo di un utente

    public List<SoilData> getSoilDataByUser(UserData user) {
        return soilDataRepository.findByUser(user);
    }
}
