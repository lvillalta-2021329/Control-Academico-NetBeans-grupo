/* En esta entidad de models se aplica ya todas las maneras donde seutiliza
   la nueva manera de hacer los modelos usando los integer property entre
   otros.
*/

package org.in5bv.malcantaralvillalta.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 29/04/2022
 * @time 11:36:38
 * 
 * Código Tecnico: IN5BV
 */

public class Cursos {
    private IntegerProperty id;
    private StringProperty nombreCurso;
    private IntegerProperty ciclo;
    private IntegerProperty cupoMaximo;
    private IntegerProperty cupoMinimo;
    private StringProperty carreraTecnicaId;
    private IntegerProperty horarioId;
    private IntegerProperty instructorId;
    private StringProperty salonId;
    private int contador;

    public Cursos() {
        this.id = new SimpleIntegerProperty();
        this.nombreCurso = new SimpleStringProperty();
        this.ciclo = new SimpleIntegerProperty();
        this.cupoMaximo = new SimpleIntegerProperty();
        this.cupoMinimo = new SimpleIntegerProperty();
        this.carreraTecnicaId = new SimpleStringProperty();
        this.horarioId = new SimpleIntegerProperty();
        this.instructorId = new SimpleIntegerProperty();
        this.salonId = new SimpleStringProperty();
    }
    
    //Constructor con Parametros
    public Cursos(int id, String nombreCurso,int ciclo, int cupoMaximo, int cupoMinimo, String carreraTecnicaId, int horarioId, int instructorId, String salonId) {
        this.id = new SimpleIntegerProperty(id);
        this.nombreCurso = new SimpleStringProperty(nombreCurso);
        this.ciclo = new SimpleIntegerProperty(ciclo);
        this.cupoMaximo = new SimpleIntegerProperty(cupoMaximo);
        this.cupoMinimo = new SimpleIntegerProperty(cupoMinimo);
        this.carreraTecnicaId = new SimpleStringProperty(carreraTecnicaId);
        this.horarioId = new SimpleIntegerProperty(horarioId);
        this.instructorId = new SimpleIntegerProperty(instructorId);
        this.salonId = new SimpleStringProperty(salonId);
    }

    // Getter´s and Setter´s
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }   
    public IntegerProperty id() {
        return id;
    }
    
    public String getNombreCurso() {
        return nombreCurso.get();
    }
    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso.set(nombreCurso);
    }   
    public StringProperty nombreCurso () {
        return nombreCurso;
    }
    
    public int getCiclo() {
        return ciclo.get();
    }
    public void setCiclo(int ciclo) {
        this.ciclo.set(ciclo);
    }
    public IntegerProperty ciclo() {
        return ciclo;
    }
    
    public int getCupoMaximo() {
        return cupoMaximo.get();
    }
    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo.set(cupoMaximo);
    }
    public IntegerProperty cupoMaximo() {
        return cupoMaximo;
    }
    
    public int getCupoMinimo() {
        return cupoMinimo.get();
    }
    public void setCupoMinimo(int cupoMinimo) {
        this.cupoMinimo.set(cupoMinimo);
    }
    public IntegerProperty cupoMinimo() {
        return cupoMinimo;
    }
    
    public String getCarreraTecnicaId() {
        return carreraTecnicaId.get();
    }
    public void setCarreraTecnicaId(String carreraTecnicaId) {
        this.carreraTecnicaId.set(carreraTecnicaId);
    }
    public StringProperty carreraTecnicaId() {
        return carreraTecnicaId;
    }
    
    public int getHorarioId() {
        return horarioId.get();
    }
    public void setHorarioId(int horarioId) {
        this.horarioId.set(horarioId);
    }
    public IntegerProperty horarioId() {
        return horarioId;
    }
    
    public int getInstructorId() {
        return instructorId.get();
    }
    public void setInstructorId(int instructorId) {
        this.instructorId.set(instructorId);
    }
    public IntegerProperty instructorId() {
        return instructorId;
    }
    
    public String getSalonId() {
        return salonId.get();
    }
    public void setSalonId(String salonId) {
        this.salonId.set(salonId);
    }
    public StringProperty salonId() {
        return salonId();
    }
    
    public int getContador() {
        return contador;
    }
    
    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return  id + "|" + nombreCurso + "|" + horarioId;
    }
    
    
    
    
    
    
}