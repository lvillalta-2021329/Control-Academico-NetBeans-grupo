
package org.in5bv.malcantaralvillalta.models;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 26/04/2022
 * @time 13:45:11
 * 
 * Código Tecnico: IN5BV
 */
public class Alumnos {
    
    private String carne;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    private int contador;
    
    public Alumnos() {
    
    }
    
    public Alumnos(String carne) {
        this.carne = carne;
    }
    
    public Alumnos(String carne, String nombre1, String apellido1) {
        this.carne = carne;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
    }
    
    public Alumnos(String nombre1, String nombre2, String nombre3, String apellido1, String apellido2) {        
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }
    
    public Alumnos(String carne, String nombre1, String nombre2, String nombre3, String apellido1, String apellido2) {
        this.carne = carne;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }
    
    public String getCarne() {
        return this.carne;
    }   
    //void porque los set no devuelven nada
    public void setCarne(String carne) {
        this.carne = carne;
    }

    public String getNombre1() {
        return nombre1;
    }
    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }
    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre3() {
        return nombre3;
    }
    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    public String getApellido1() {
        return apellido1;
    }
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    public int getContador() {
        return contador;
    }
    
    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return   carne + "|" + nombre1 + "|" + apellido1 ;
    }
  
}
