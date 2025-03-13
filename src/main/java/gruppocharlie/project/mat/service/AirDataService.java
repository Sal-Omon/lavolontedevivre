package gruppocharlie.project.mat.service;

import gruppocharlie.project.mat.model.AirData;
import gruppocharlie.project.mat.model.UserData;
import gruppocharlie.project.mat.repository.AirDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirDataService {

    private final AirDataRepository airDataRepository;

    public AirDataService(AirDataRepository airDataRepository) {
        this.airDataRepository = airDataRepository;
    }

    //Salva un nuovo dato dell'aria

    public void saveAirData(AirData airData) {
        airDataRepository.save(airData);
    }

    //Recupera tutti i dati dell'aria di un utente

    public List<AirData> getAirDataByUser(UserData user) {
        return airDataRepository.findByUser(user);
    }

}
