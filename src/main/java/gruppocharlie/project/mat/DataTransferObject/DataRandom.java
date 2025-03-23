package gruppocharlie.project.mat.DataTransferObject;

import java.util.Random;

public class DataRandom {

    private static final Random random = new Random();

    public static float getUmiditaAria() {
        return 30 + (70 - 30) * random.nextFloat();
    }

    public static float getTemperaturaAria() {
        return 10 + (35 - 10) * random.nextFloat();
    }

    public static double getUmiditaTerreno() {
        return 20 + (80 - 20) * random.nextDouble();
    }

    public static double getTemperaturaTerreno() {
        return 15 + (40 - 15) * random.nextDouble();
    }

    public static int getIntensitaLuce() {
        return random.nextInt(1000);
    }

    public static double getVelocitaVento() {
        return random.nextDouble() * 20;
    }
}