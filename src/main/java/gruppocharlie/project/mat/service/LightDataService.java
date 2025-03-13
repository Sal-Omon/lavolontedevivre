package gruppocharlie.project.mat.service;

import gruppocharlie.project.mat.model.LightData;
import gruppocharlie.project.mat.model.UserData;
import gruppocharlie.project.mat.repository.LightDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LightDataService {

    private final LightDataRepository lightDataRepository;

    public LightDataService(LightDataRepository lightDataRepository) {
        this.lightDataRepository = lightDataRepository;
    }

    //Salva un nuovo dato sulle ore di luce

    public LightData saveLightData(LightData lightData) {
        return lightDataRepository.save(lightData);
    }

    //Recupera i dati dell'utente autenticato

    public List<LightData> getLightDataByUser(UserData user) {
        return lightDataRepository.findByUser(user);
    }

}
