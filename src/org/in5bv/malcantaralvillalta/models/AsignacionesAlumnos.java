package org.in5bv.malcantaralvillalta.models;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 19/04/2022
 * @time 13:41:02
 *
 * Código Tecnico: IN5BV
 */
public class AsignacionesAlumnos {

    private IntegerProperty id;
    private StringProperty alumnoId; // Carné
    private IntegerProperty cursoId;
    private ObjectProperty<LocalDateTime> fechaAsignacion;
    private int contador;

    public AsignacionesAlumnos() {
        this.id = new SimpleIntegerProperty();
        this.alumnoId = new SimpleStringProperty();
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty<>();
    }

    
    public AsignacionesAlumnos(String alumnoId, int cursoId, LocalDateTime fechaAsignacion) {
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }
    
    public AsignacionesAlumnos(int id, String alumnoId, int cursoId, LocalDateTime fechaAsignacion) {
        this.id = new SimpleIntegerProperty(id);
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }

    // Getter's and Setter's
    public IntegerProperty id() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty alumnoId() {
        return alumnoId;
    }

    public String getAlumnoId() {
        return alumnoId.get();
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId.set(alumnoId);
    }

    public IntegerProperty cursoId() {
        return cursoId;
    }

    public int getCursoId() {
        return cursoId.get();
    }

    public void setCursoId(int cursoId) {
        this.cursoId.set(cursoId);
    }

    public ObjectProperty<LocalDateTime> fechaAsignacion() {
        return fechaAsignacion;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion.get();
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion.set(fechaAsignacion);
    }
    
     public int getContador() {
        return contador;
    }
    
    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return id + " | " + fechaAsignacion;
    }

}
