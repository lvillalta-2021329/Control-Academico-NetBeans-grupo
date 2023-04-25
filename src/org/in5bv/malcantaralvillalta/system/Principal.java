package org.in5bv.malcantaralvillalta.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bv.malcantaralvillalta.controllers.AlumnosController;
import org.in5bv.malcantaralvillalta.controllers.CarrerasTecnicasController;
import org.in5bv.malcantaralvillalta.controllers.MenuPrincipalController;
import org.in5bv.malcantaralvillalta.controllers.SalonesController;
import org.in5bv.malcantaralvillalta.controllers.CursosController;
import org.in5bv.malcantaralvillalta.controllers.InstructoresController;
import org.in5bv.malcantaralvillalta.controllers.HorariosController;
import org.in5bv.malcantaralvillalta.controllers.AsignacionesAlumnosController;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 5/04/2022
 * @time 16:38:10
 * 
 * Código Tecnico: IN5BV
 */
public class Principal extends Application { 
    
    private Stage esenarioPrincipal;
    //Palabra final para que sea una constante  
    private final String PAQUETE_IMAGE = "org/in5bv/malcantaralvillalta/resources/images/";
    //Se pone ../view/ porque no aranca con la direcion completa
    private final String PAQUETE_VIEW = "../views/";
    
    public static void main(String[] args) {
        launch(args);
    }   

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.esenarioPrincipal = primaryStage;
        this.esenarioPrincipal.setTitle("Control Academico KINAL");
        this.esenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
        this.esenarioPrincipal.setResizable(false);
        this.esenarioPrincipal.centerOnScreen();
                       
        //mostrarEscenaPrincipal();
        mostrarEscenaAlumnos();
        //mostrarEscenaAsignacionesAlumnos();
    }
    
  
    
    public void mostrarEscenaAlumnos() {
        try {
            AlumnosController alumnosController = (AlumnosController) cambiarEsena("AlumnosView.FXML",870,465);
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            //err es de error me lo mostrara en color rojo
            System.err.println("\nSe produjo un error al intentar mostrar la vista de alumnos");
        }    
    }
    
    public void mostrarEscenaHorarios() {
        try {
            HorariosController horariosController = (HorariosController) cambiarEsena("HorariosView.FXML",870,465);
            horariosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            //err es de error me lo mostrara en color rojo
            System.err.println("\nSe produjo un error al intentar mostrar la vista de horarios");
            ex.printStackTrace();
        }    
    }
        
    public void mostrarEscenaAsignacionesAlumnos() {
        try {
            AsignacionesAlumnosController asignacionesAlumnosController = (AsignacionesAlumnosController) cambiarEsena("AsignacionesAlumnosView.FXML",870,465);
            asignacionesAlumnosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            //err es de error me lo mostrara en color rojo
            System.err.println("\nSe produjo un error al intentar mostrar la vista de Asignaciones alumnos");
            ex.printStackTrace();
        }    
    }
    public void mostrarEscenaSalones() {
        try {
            SalonesController salonesController = (SalonesController) cambiarEsena("SalonesView.FXML",870,465);
            salonesController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            //err es de error me lo mostrara en color rojo
            System.err.println("\nSe produjo un error al intentar mostrar la vista de alumnos");
        }    
    }
    
    public void mostrarEscenaCursos() {
        try {
            CursosController cursosController = (CursosController) cambiarEsena("CursosView.FXML",870,465);
            cursosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al intentar mostrar la vista de Cursos");
        }    
    }
    
    public void mostrarEscenaInstructores() {
        try {
            InstructoresController instructoresController = (InstructoresController) cambiarEsena("InstructoresView.FXML",875,465);
            instructoresController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al intentar mostrar la vista de Instructores");
        }    
    }
    
    public void mostrarEscenaCarrerasTecnicas() {
        try {
            CarrerasTecnicasController carrerasTecnicasController = (CarrerasTecnicasController) cambiarEsena("CarrerasTecnicasView.FXML",870,465);
            carrerasTecnicasController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            //err es de error me lo mostrara en color rojo
            System.err.println("\nSe produjo un error al intentar mostrar la vista de alumnos");
        }    
    }
    public void mostrarEscenaPrincipal() {
        //El try cath maneja la exepcion y me dice el error que fue lo que pusimos en las dos anteriores
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEsena("MenuPrincipalView.FXML",850,465);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al intentar mostrar la vista de Menu Principla");
        }
    }
    
    /*
    public Initializable cambiarEsena(String vistaFxml, int ancho, int alto) throws IOException {
        //opcional este sout era para ver la ruta del archivo
        System.out.println(PAQUETE_VIEW + vistaFxml);
        
        Initializable resultado = null;
        
        //Cargador FXML
        FXMLLoader cargadorFXML = new FXMLLoader();
        
        //cargador de fabrica para cargar el archivo FXML
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VIEW + vistaFxml));
        
        //Asignacion de la logica del archivo
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VIEW + vistaFxml);
        
        //Creacion del nodo raiz
        AnchorPane root = cargadorFXML.load(archivo);
        
        //Creacion de la esena
        Scene nuevaEscena = new Scene(root, ancho, alto);
        
        //Cargar la esena en el esenario principal
        this.esenarioPrincipal.setScene(nuevaEscena);
        
        //obtener el controlador del archivo FXML
        resultado = (Initializable)cargadorFXML.getController();
        
        //Devolver el controlador
        return resultado;
    }
    */
    
    public Initializable cambiarEsena(String vistaFxml, int ancho, int alto) throws IOException {
        System.out.println(PAQUETE_VIEW + vistaFxml);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
        this.esenarioPrincipal.setScene(scene);
        this.esenarioPrincipal.sizeToScene();
        this.esenarioPrincipal.show();
        
        return (Initializable) cargadorFXML.getController();
    }
}

