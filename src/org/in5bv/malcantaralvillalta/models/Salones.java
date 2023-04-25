
package org.in5bv.malcantaralvillalta.models;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 29/04/2022
 * @time 11:59:39
 * 
 * Código Tecnico: IN5BV
 */
public class Salones {
    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;
    private int contador;
    
    public Salones() {
    
    }
    
    public Salones(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }
    
    public Salones(String codigoString, String descripcion, int capacidadMaxima) {
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
    }
    
    public Salones(String descripcion, int capacidadMaxima, String edificio, int nivel) {
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }
    
    public Salones(String codigoSalon, String descripcion, int capacidadMaxima, String edificio, int nivel) {
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    public String getCodigoSalon() {
        return codigoSalon;
    }
    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getEdificio() {
        return edificio;
    }
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public int getContador() {
        return contador;
    }
    
    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return codigoSalon + " | " + descripcion;
    }

    
    
    
    
    

}
