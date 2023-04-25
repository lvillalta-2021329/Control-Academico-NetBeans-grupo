package org.in5bv.malcantaralvillalta.models;

import java.time.LocalTime;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 26/04/2022
 * @time 13:45:11
 * 
 * Código Tecnico: IN5BV
 */

public class Horarios {

    private int id;
    private LocalTime horarioInicio;
    private LocalTime horarioFinal;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private int contador;


    public Horarios() {
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(LocalTime horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    

    public Horarios(int id, LocalTime horarioInicio, LocalTime horarioFinal, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
      
    }
    
     public int getContador() {
        return contador;
    }
    
    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return  id + "|" + horarioInicio + "|" + horarioFinal ;
    }

   
     
    
    
    
}

