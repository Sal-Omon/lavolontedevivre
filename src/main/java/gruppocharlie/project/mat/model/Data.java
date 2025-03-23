package gruppocharlie.project.mat.model;

public class Data {
    private double umiditaAria;
    private double temperaturaAria;
    private double umiditaTerreno;
    private double temperaturaTerreno;
    private int intensitaLuce;
    private double velocitaVento;

    public Data(double umiditaAria, double temperaturaAria, double umiditaTerreno, double temperaturaTerreno, int intensitaLuce, double velocitaVento) {
        this.umiditaAria = umiditaAria;
        this.temperaturaAria = temperaturaAria;
        this.umiditaTerreno = umiditaTerreno;
        this.temperaturaTerreno = temperaturaTerreno;
        this.intensitaLuce = intensitaLuce;
        this.velocitaVento = velocitaVento;
    }

    public double getUmiditaAria() {
        return umiditaAria;
    }

    public void setUmiditaAria(double umiditaAria) {
        this.umiditaAria = umiditaAria;
    }

    public double getTemperaturaAria() {
        return temperaturaAria;
    }

    public void setTemperaturaAria(double temperaturaAria) {
        this.temperaturaAria = temperaturaAria;
    }

    public double getUmiditaTerreno() {
        return umiditaTerreno;
    }

    public void setUmiditaTerreno(double umiditaTerreno) {
        this.umiditaTerreno = umiditaTerreno;
    }

    public double getTemperaturaTerreno() {
        return temperaturaTerreno;
    }

    public void setTemperaturaTerreno(double temperaturaTerreno) {
        this.temperaturaTerreno = temperaturaTerreno;
    }

    public int getIntensitaLuce() {
        return intensitaLuce;
    }

    public void setIntensitaLuce(int intensitaLuce) {
        this.intensitaLuce = intensitaLuce;
    }

    public double getVelocitaVento() {
        return velocitaVento;
    }

    public void setVelocitaVento(double velocitaVento) {
        this.velocitaVento = velocitaVento;
    }
}