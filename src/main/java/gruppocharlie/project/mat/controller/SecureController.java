package gruppocharlie.project.mat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {
    @GetMapping("/data")
    public String getProtectedData() {
        return "Questa è un'API protetta accessibile solo con JWT valido!";
    }
}
