package gruppocharlie.project.mat.controller;

import gruppocharlie.project.mat.DataTransferObject.DataRandom;
import gruppocharlie.project.mat.model.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping("/data")
    public Data getData() {
        // Genera i dati
        Data data = new Data(
                DataRandom.getUmiditaAria(),
                DataRandom.getTemperaturaAria(),
                DataRandom.getUmiditaTerreno(),
                DataRandom.getTemperaturaTerreno(),
                DataRandom.getIntensitaLuce(),
                DataRandom.getVelocitaVento()
        );

        // Aggiungi un log per vedere il valore dei dati
        System.out.println("Dati generati: " + data);

        // Restituisci i dati
        return data;
    }
}
