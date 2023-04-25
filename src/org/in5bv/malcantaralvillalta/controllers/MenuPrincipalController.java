package org.in5bv.malcantaralvillalta.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextInputDialog;
import org.in5bv.malcantaralvillalta.reports.GenerarReporte;
import org.in5bv.malcantaralvillalta.system.Principal;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 28/04/2022
 * @time 16:41:53
 * 
 * Código Técnico: IN5BV
 */
public class MenuPrincipalController implements Initializable{
    
    private Principal escenarioPrincipal;
    private final String PAQUETE_IMAGE = "org/in5bv/malcantaralvillalta/resources/images/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicAlumnos() {
        escenarioPrincipal.mostrarEscenaAlumnos();   
    }
    
    @FXML
    public void clicSalones() {
        escenarioPrincipal.mostrarEscenaSalones();
    }
    
    @FXML
    public void clicCarrerasTecnicas() {
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }
    
    @FXML
    public void clicCursos() {
        escenarioPrincipal.mostrarEscenaCursos();
    }

    @FXML
    public void clicInstructores() {
        escenarioPrincipal.mostrarEscenaInstructores();
    }
    
    @FXML
    public void clicAsignacionesAlumnos() {
        escenarioPrincipal.mostrarEscenaAsignacionesAlumnos();
    }
    
    @FXML
    public void clicHorarios() {
        escenarioPrincipal.mostrarEscenaHorarios();
    }
    
   
    
    @FXML
    public void clicReporteAsignaciones() {  
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "asignacion.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteCarrerasTecnicas.jasper",parametros, "ReporteCarreras");

    }
    
    @FXML
    public void clicReporteAlumnos() {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "alumni.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteAlumnos1.jasper",parametros, "ReporteAlumnos");
        
    } 
    
        @FXML
    public void clicReporteAsignacionesPorId() {
        Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "asignacion.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       TextInputDialog dialog = new TextInputDialog();
       dialog.setHeaderText("Ingresa el Id");
       dialog.setContentText("Id asignación");
       Optional<String> result = dialog.showAndWait();
       System.out.println(result + "numero que pongo");
       if(result.isPresent()) {
           System.out.println("Numero: " + result.get());
           parametros.put("idAsignacion", Integer.parseInt(result.get()));
       }
      
       GenerarReporte.getInstance().mostrarReporte("ReporteAsignacionPorId.jasper",parametros, "ReporteCarreras");
    }
    
    
    
    @FXML
    public void clicReporteCursosPorId() {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "cursos.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       
       TextInputDialog dialog = new TextInputDialog();
       dialog.setHeaderText("Ingresa el Id");
       dialog.setContentText("Id asignación");
       Optional<String> result = dialog.showAndWait();
       System.out.println(result + "numero que pongo");
       if(result.isPresent()) {
           System.out.println("Numero: " + result.get());
           parametros.put("idCurso", Integer.parseInt(result.get()));          
       }
       
       GenerarReporte.getInstance().mostrarReporte("ReporteCursosPorId.jasper",parametros, "ReporteCursos");
 
     
    }
    
    @FXML
    public void clicReporteCursos() {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "cursos.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteCursos12.jasper",parametros, "ReporteCursos");
 
    }
    
    
    @FXML
    public void clicReporteHorarios() {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "horario.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteHorarios.jasper",parametros, "ReporteHorarios");

    }
    
    
    
    @FXML
    public void clicReporteCarreras() {
        Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "carrera.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteCarreras13.jasper",parametros, "ReporteCarreras");

    }
    
    
    
    
    
    
    @FXML
    public void clicReporteSalones() {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "salon.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper",parametros, "ReporteSalones");
        
     
    }
    
    @FXML
    public void clicReporteInstructores() {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "instructor.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteInstructores.jasper",parametros, "ReporteInstructores");
        
     
    }
    
    @FXML
    public void clicReporteDatos() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "nuevo.jpeg");
        
        GenerarReporte.getInstance().mostrarReporte("ReporteDatos.jasper",parametros, "ReporteDatos");
        
       
    }

    

    
    
  

}
