package modelo;

public class Destino {
    private int id;
    private String nombre;
    private String lugar;
    private double costo;
    private int dias;  // nuevo campo

    // Constructor vacío
    public Destino() {
    }

    // Constructor con parámetros
    public Destino(int id, String nombre, String lugar, double costo, int dias) {
        this.id = id;
        this.nombre = nombre;
        this.lugar = lugar;
        this.costo = costo;
        this.dias = dias;
    }

    // Getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public double getCosto() {
        return costo;
    }
    public void setCosto(double costo) {
        this.costo = costo;
    }
    public int getDias() {
        return dias;
    }
    public void setDias(int dias) {
        this.dias = dias;
    }
}
