package gruppocharlie.project.mat.controller;

import gruppocharlie.project.mat.DataTransferObject.DataRandom;
import gruppocharlie.project.mat.model.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class DataController {

    @GetMapping("/data")
    public Data getData() {
        return new Data(
                DataRandom.getUmiditaAria(),
                DataRandom.getTemperaturaAria(),
                DataRandom.getUmiditaTerreno(),
                DataRandom.getTemperaturaTerreno(),
                DataRandom.getIntensitaLuce(),
                DataRandom.getVelocitaVento()
        );
    }
}
