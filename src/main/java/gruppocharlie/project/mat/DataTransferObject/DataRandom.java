package gruppocharlie.project.mat.DataTransferObject;

import java.util.Random;

public final class DataRandom {

    private static final Random random = new Random();

    // Intervalli per ogni valore
    private static final float MIN_UMIDITA_ARIA = 30;
    private static final float MAX_UMIDITA_ARIA = 70;

    private static final float MIN_TEMPERATURA_ARIA = 10;
    private static final float MAX_TEMPERATURA_ARIA = 35;

    private static final double MIN_UMIDITA_TERRENO = 20;
    private static final double MAX_UMIDITA_TERRENO = 80;

    private static final double MIN_TEMPERATURA_TERRENO = 15;
    private static final double MAX_TEMPERATURA_TERRENO = 40;

    private static final int MAX_INTENSITA_LUCE = 1000;

    private static final double MAX_VELOCITA_VENTO = 20;

    // Costruttore privato per evitare l'istanza della classe
    private DataRandom() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static float getUmiditaAria() {
        return MIN_UMIDITA_ARIA + (MAX_UMIDITA_ARIA - MIN_UMIDITA_ARIA) * random.nextFloat();
    }

    public static float getTemperaturaAria() {
        return MIN_TEMPERATURA_ARIA + (MAX_TEMPERATURA_ARIA - MIN_TEMPERATURA_ARIA) * random.nextFloat();
    }

    public static double getUmiditaTerreno() {
        return MIN_UMIDITA_TERRENO + (MAX_UMIDITA_TERRENO - MIN_UMIDITA_TERRENO) * random.nextDouble();
    }

    public static double getTemperaturaTerreno() {
        return MIN_TEMPERATURA_TERRENO + (MAX_TEMPERATURA_TERRENO - MIN_TEMPERATURA_TERRENO) * random.nextDouble();
    }

    public static int getIntensitaLuce() {
        return random.nextInt(MAX_INTENSITA_LUCE);
    }

    public static double getVelocitaVento() {
        return random.nextDouble() * MAX_VELOCITA_VENTO;
    }
}
