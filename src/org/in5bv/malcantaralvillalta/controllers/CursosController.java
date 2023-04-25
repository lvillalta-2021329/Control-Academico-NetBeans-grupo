package org.in5bv.malcantaralvillalta.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Spinner;

import javafx.scene.control.SpinnerValueFactory;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.in5bv.malcantaralvillalta.system.Principal;

import org.in5bv.malcantaralvillalta.models.CarrerasTecnicas;
import org.in5bv.malcantaralvillalta.models.Cursos;
import org.in5bv.malcantaralvillalta.models.Instructores;
import org.in5bv.malcantaralvillalta.models.Salones;
import org.in5bv.malcantaralvillalta.models.Horarios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import org.in5bv.malcantaralvillalta.db.Conexion;
import org.in5bv.malcantaralvillalta.models.Alumnos;
import org.in5bv.malcantaralvillalta.reports.GenerarReporte;

/**
 *
 * @author informatica
 */
public class CursosController implements Initializable {
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;
    
    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<Instructores> listaObservableInstructores;    
    private ObservableList<Salones> listaObservableSalones;    
    private ObservableList<CarrerasTecnicas> listaObservableCarrerasTecnicas;
    private ObservableList<Horarios> listaObservableHorarios;
    
    private Principal escenarioPrincipal;
    
    private final String PAQUETE_IMAGE = "org/in5bv/malcantaralvillalta/resources/images/";
    
    @FXML
    private Button btnNuevo;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private ImageView imgModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReporte;
    
    @FXML
    private Button btnCerrar; 
    @FXML
    private TextField txtRegistro; 
    private int contador = 0;
    
    @FXML
    private TableView<Cursos> tblCursos;
    
    // En esta insercion primero va mi modelo Cursos y luego el dato int = integer.
    @FXML 
    private TableColumn<Cursos, Integer> colId;
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    @FXML
    private TableColumn<Cursos, Integer> colCiclo;
    @FXML
    private TableColumn<Cursos, Integer> colCupoMaximo;
    @FXML
    private TableColumn<Cursos, Integer> colCupoMinimo;
    @FXML
    private TableColumn<Cursos, String> colCodigoTecnico;
    @FXML
    private TableColumn<Cursos, Integer> colHorario;
    @FXML
    private TableColumn<Cursos, Integer> colInstructor;
    @FXML
    private TableColumn<Cursos, String> colSalon;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombreCurso;
    
    // Adentro del operador diamante<> de Spinner va el tipo de dato que manejara
    @FXML
    private Spinner<Integer> spnCiclo;
    
    // Este no lleva el FXML porque visualmente no existe en la vista
    private SpinnerValueFactory<Integer> valueFactoryCiclo;
    
    @FXML
    private Spinner<Integer> spnCupoMaximo;
    
    private SpinnerValueFactory<Integer> valuesFactoryCupoMaximo;
    
    @FXML
    private Spinner<Integer> spnCupoMinimo;
    
    private SpinnerValueFactory<Integer> valuesFactoryCupoMinimo;
    
    //Adentro del operador diamante<> de ComboBox va su madelo de datos
    @FXML
    private ComboBox<CarrerasTecnicas> cmbCarreraTecnica;
    @FXML
    private ComboBox<Horarios> cmbHorario; //Agregar su modelo de datos
    @FXML
    private ComboBox<Instructores> cmbInstructor; //Agregar su modelo de datos
    @FXML
    private ComboBox<Salones> cmbSalon;
    @FXML
    private ImageView imgRegresar;

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2050, 2022); //Minimo, Maximo, Actual
        spnCiclo.setValueFactory(valueFactoryCiclo);
        
        valuesFactoryCupoMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20); //Minimo, Maximo, Actual
        spnCupoMaximo.setValueFactory(valuesFactoryCupoMaximo);
        
        valuesFactoryCupoMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 5); //Minimo, Maximo, Intermedio
        spnCupoMinimo.setValueFactory(valuesFactoryCupoMinimo);
        
        cargarDatos();
    }   
    
    public static void CerrarPrograma(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void clicCerrar(ActionEvent event) {
        CerrarPrograma(event);
    }
    
    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombreCurso.setEditable(false);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorario.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);
        
        txtId.setDisable(true);
        txtNombreCurso.setDisable(true);
        spnCiclo.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        cmbCarreraTecnica.setDisable(true);
        cmbHorario.setDisable(true);
        cmbInstructor.setDisable(true);
        cmbSalon.setDisable(true);
    }

    private void habilitarCampos() {
        txtId.setEditable(true);
        txtNombreCurso.setEditable(true);
        spnCiclo.setEditable(true);
        spnCupoMaximo.setEditable(true);
        spnCupoMinimo.setEditable(true);
        
        //Los comboBox nunca deven ir editables dara excepcion de los contrario.
        //cmbCarreraTecnica.setEditable(true);
        //cmbHorario.setEditable(true);
        //cmbInstructor.setEditable(true);
        //cmbSalon.setEditable(true);
        
        txtId.setDisable(false);
        txtNombreCurso.setDisable(false);
        spnCiclo.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        cmbCarreraTecnica.setDisable(false);
        cmbHorario.setDisable(false);
        cmbInstructor.setDisable(false);
        cmbSalon.setDisable(false);
    }

    //spn no tiene un metodo clear asi que se le da un valor por defecto.
    private void limpiarCampos() {
        txtId.clear();
        txtNombreCurso.clear();
        spnCiclo.getValueFactory().setValue(2022);
        spnCupoMaximo.getValueFactory().setValue(0);
        spnCupoMinimo.getValueFactory().setValue(0);
        cmbCarreraTecnica.valueProperty().set(null);
        cmbHorario.valueProperty().set(null);
        cmbInstructor.valueProperty().set(null);
        cmbSalon.valueProperty().set(null);
    }
    
    //READ = Liistar todos los registros
    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_read()}");
            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) { // Solo en esta llamada de datos se coloca el guin bajo
                Cursos curso = new Cursos();
                curso.setId(rs.getInt("id"));  //Se puede indicar que el lo que quiero mostar con el nombre, deve ser el de la base da datos
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setInstructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));
                
                System.out.println(curso.toString());
                
                arrayListCursos.add(curso);
                for (int i = 0; i <= arrayListCursos.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);
            //Trasformacion del arraylist al observable list
            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);
            
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Cursos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return listaObservableCursos;
    }
    
    private ObservableList getCarrerasTecnicas() {
        // Este get se tomo de CarrerasTecnicasController.....
        ArrayList<CarrerasTecnicas> arrayListCarreras = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {         
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_read()}");
            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CarrerasTecnicas carrerasTecnicas = new CarrerasTecnicas();
                carrerasTecnicas.setCodigoTecnico(rs.getString(1));
                carrerasTecnicas.setCarrera(rs.getString(2));
                carrerasTecnicas.setGrado(rs.getString(3));
                carrerasTecnicas.setSeccion(rs.getString(4));
                carrerasTecnicas.setJornada(rs.getString(5));
                
                System.out.println(carrerasTecnicas.toString());

                arrayListCarreras.add(carrerasTecnicas);
                
                for (int i = 0; i <= arrayListCarreras.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador );
            listaObservableCarrerasTecnicas = FXCollections.observableArrayList(arrayListCarreras);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Carreras Tecnicas");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaObservableCarrerasTecnicas;
    }
    
    private ObservableList getInstructores() {
        ArrayList<Instructores> arrayListInstructores = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());

                System.out.println(instructor);

                arrayListInstructores.add(instructor);
                for (int i = 0; i <= arrayListInstructores.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);
            listaObservableInstructores = FXCollections.observableArrayList(arrayListInstructores);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaObservableInstructores;
    }
    
    private ObservableList getHorarios() {
        ArrayList<Horarios> arrayListHorarios = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2).toLocalTime());
                horario.setHorarioFinal(rs.getTime(3).toLocalTime());
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                System.out.println(horario.toString());
                arrayListHorarios.add(horario);
                
                for (int i = 0; i <= arrayListHorarios.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);
            listaObservableHorarios = FXCollections.observableArrayList(arrayListHorarios);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Horarios");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaObservableHorarios;
    }
    
    private ObservableList getSalones() {
        ArrayList<Salones> arrayListSalones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null; 
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_read()");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));    
                System.out.println(salon.toString());
                
                arrayListSalones.add(salon);
                for (int i = 0; i <= arrayListSalones.size() ; i++) {                  
                    contador = 0 + i;
                }
            }  
            System.out.println(contador);
            listaObservableSalones = FXCollections.observableArrayList(arrayListSalones);
            
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Salones");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {           
            try{
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }              
        }       
        return listaObservableSalones;
    }
    
    public void cargarDatos() {
        Cursos curso = new Cursos();
        tblCursos.setItems(getCursos()); // Los datos del parentesis son de el modelo de datos
        colId.setCellValueFactory(new PropertyValueFactory<>("id")); 
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colCupoMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colCupoMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colSalon.setCellValueFactory(new PropertyValueFactory<>("salonId"));
        
        cmbCarreraTecnica.setItems(getCarrerasTecnicas());
        cmbHorario.setItems(getHorarios());
        cmbInstructor.setItems(getInstructores());
        cmbSalon.setItems(getSalones());
        
        curso.setContador(contador);
        txtRegistro.setText(Integer.toString(curso.getContador()));
    }
    
    
    private boolean agregarCursos() {

        Cursos curso = new Cursos();
        //curso.setId(Integer.parseInt(txtId.getText()));
        curso.setNombreCurso(txtNombreCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());
        
        //Por ser un comboBox no se podia tener solo una informacion, asi que se castea
        curso.setCarreraTecnicaId(((CarrerasTecnicas)cmbCarreraTecnica 
                .getSelectionModel().getSelectedItem()).getCodigoTecnico());
        
        curso.setHorarioId(((Horarios) cmbHorario.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setInstructorId(((Instructores) cmbInstructor.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setSalonId(((Salones) cmbSalon.getSelectionModel()
                .getSelectedItem()).getCodigoSalon());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_create(?,?,?,?,?,?,?,?)}");

            pstmt.setString(1, curso.getNombreCurso());
            pstmt.setInt(2, curso.getCiclo());
            pstmt.setInt(3, curso.getCupoMaximo());
            pstmt.setInt(4, curso.getCupoMinimo());
            pstmt.setString(5, curso.getCarreraTecnicaId());
            pstmt.setInt(6, curso.getHorarioId());
            pstmt.setInt(7, curso.getInstructorId());
            pstmt.setString(8, curso.getSalonId());

            System.out.println(pstmt.toString());

            pstmt.execute();
            listaObservableCursos.add(curso);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguente registro" + curso.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean actualizarCursos () {

        Cursos curso = new Cursos();
        curso.setId(Integer.parseInt(txtId.getText()));
        curso.setNombreCurso(txtNombreCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());
        
        curso.setCarreraTecnicaId(((CarrerasTecnicas)cmbCarreraTecnica 
                .getSelectionModel().getSelectedItem()).getCodigoTecnico());
        
        curso.setHorarioId(((Horarios) cmbHorario.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setInstructorId(((Instructores) cmbInstructor.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setSalonId(((Salones) cmbSalon.getSelectionModel()
                .getSelectedItem()).getCodigoSalon());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_update(?,?,?,?,?,?,?,?,?)}");
            
            pstmt.setInt(1, curso.getId());
            pstmt.setString(2, curso.getNombreCurso());
            pstmt.setInt(3, curso.getCiclo());
            pstmt.setInt(4, curso.getCupoMaximo());
            pstmt.setInt(5, curso.getCupoMinimo());
            pstmt.setString(6, curso.getCarreraTecnicaId());
            pstmt.setInt(7, curso.getHorarioId());
            pstmt.setInt(8, curso.getInstructorId());
            pstmt.setString(9, curso.getSalonId());

            System.out.println(pstmt.toString());

            pstmt.execute();
            listaObservableCursos.add(curso);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar el siguente registro" + curso.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public boolean elimnarCursos() {
        
        Cursos curso = (Cursos) tblCursos.getSelectionModel().getSelectedItem();
        System.out.println(curso.toString());
        PreparedStatement pstmt = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_cursos_delete(?)");
            pstmt.setInt(1, curso.getId());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
            
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar eliminar el siguente Registro: " + curso.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean existeElementoSeleccionado() {
        return (tblCursos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();         
                
                tblCursos.setDisable(true);
                txtId.setDisable(true);

                btnNuevo.setText(" Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "guardar.png"));

                btnModificar.setText(" Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "cancelar3.png")); //Cambiar imagen ya

                btnEliminar.setDisable(true);
                //btnEliminar.setVisible(false); para que no se visible

                btnReporte.setDisable(true);
                // btnReporte.setVisible(false); para que no sea visible

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (agregarCursos())
                {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnReporte.setDisable(false);
                    
                    tblCursos.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        switch (operacion)
        {
            case NINGUNO:
                if (existeElementoSeleccionado())
                {
                    habilitarCampos();                    

                    //para que no se pueda editar ese campo y que se desabilite
                    txtId.setDisable(true);
                    txtId.setEditable(false);

                    btnModificar.setText(" Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "guardar.png"));

                    btnEliminar.setText(" Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar3.png"));

                    btnNuevo.setDisable(true);

                    btnReporte.setDisable(true);

                    operacion = Operacion.ACTUALIZAR;//guardar
                } else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecione un registro.");
                    alert.show();
                }
                break;
            case GUARDAR: //guardar //Cancelar insercion
                
                //Esto es para habilitar el table view de la accion del boton nuevo case ninguno
                tblCursos.setDisable(false);

                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                btnEliminar.setDisable(false);
                btnReporte.setDisable(false);

                //Implementarlo en los botones de cancelar.
                deshabilitarCampos();
                limpiarCampos();

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (actualizarCursos())
                {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setDisable(false);

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));

                    btnReporte.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

   @FXML
    private void clicEliminar(ActionEvent event) {
        switch (operacion)
        {
            case ACTUALIZAR: // Guardar ninguno

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));

                btnNuevo.setDisable(false);

                btnReporte.setDisable(false);

                limpiarCampos();
                deshabilitarCampos();

                //Sentencia que al cancelar la opcion de modificar ya no esten selecionadas las casillas
                tblCursos.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: //Eliminar un Registro  
                if (existeElementoSeleccionado()) {
                    
                    //Alerta para confirmacion de eliminar
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Esta seguro que desea eliminar el registro selecionado");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "eliminar.png"));
                    
                    Optional <ButtonType> result = alert.showAndWait();
                    
                    if (result.get().equals(ButtonType.OK)) {
                        if (elimnarCursos()) {
                            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                            alertInformation.setTitle("Control Academico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro Eliminado Exitosamente");
                            alertInformation.show();
                        
                        }
                    } else { // Cancion de cancelar no deseo eliminar el campo
                        alert.close();
                        tblCursos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
 
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un Registtro");
                    alert.show();                   
                }
                break;
        }
    }

    @FXML
    private void clicReporte(ActionEvent event) {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "cursos.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteCursos12.jasper",parametros, "ReporteCursos");

     
    }
    
    private Cursos buscarCurso(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_read_by_id(?)}");           
            pstmt.setInt(1,id);
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                curso = new Cursos(
                        rs.getInt("id"),
                        rs.getString("nombre_curso"), 
                        rs.getInt("ciclo"), 
                        rs.getInt("cupo_maximo"), 
                        rs.getInt("cupo_minimo"), 
                        rs.getString("carrera_tecnica_id"), 
                        rs.getInt("horario_id"), 
                        rs.getInt("instructor_id"), 
                        rs.getString("salon_id")
                );
                System.out.println(curso.toString());
            }
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Cursos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return curso;
    }
    
    private CarrerasTecnicas buscarCarrerasTecnicas(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CarrerasTecnicas carrera = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_read_by_id(?)}");
            pstmt.setString(1,id);            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();           
            while(rs.next()) {
                carrera = new CarrerasTecnicas(
                        rs.getString("codigo_tecnico"),
                        rs.getString("carrera"), 
                        rs.getString("grado"), 
                        rs.getString("seccion"), 
                        rs.getString("jornada")                      
                );
                System.out.println(carrera.toString());
            }
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Carrera");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return carrera;
    }
    
    private Horarios buscarHorarios (int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Horarios horario = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_read_by_id(?)}");
            pstmt.setInt(1,id);            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();           
            while(rs.next()) {
                
                horario = new Horarios(
                        rs.getInt("id"),
                        rs.getTime("horario_inicio").toLocalTime(),
                        //Estos deven de estar escritos como en la base de datos: horario_inicio
                        rs.getTime("horario_final").toLocalTime(),
                        //Estos deven de estar escritos como en la base de datos: horario_final
                        rs.getBoolean("lunes"),
                        rs.getBoolean("martes"),
                        rs.getBoolean("miercoles"),
                        rs.getBoolean("jueves"),
                        rs.getBoolean("viernes")                             
                );
                
                System.out.println(horario.toString());
            }
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Horarios");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return horario;
    }
    
    private Instructores buscarInstructor (int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Instructores instructor = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read_by_id(?)}");
            pstmt.setInt(1,id);            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();           
            while(rs.next()) {
                
                instructor = new Instructores(
                        rs.getInt("id"),
                        rs.getString("nombre1"),
                        rs.getString("nombre2"),
                        rs.getString("nombre3"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("direccion"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_nacimiento").toLocalDate() //Uso del localDate en el read_by
                );
                
                System.out.println(instructor.toString());
            }
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Instructores");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instructor;
    }
    
    private Salones buscarSalon (String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Salones salon = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read_by_id(?)}");
            pstmt.setString(1,id);            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();           
            while(rs.next()) {
                
                salon = new Salones(
                        rs.getString("codigo_salon"),
                        rs.getString("descripcion"),
                        rs.getInt("capacidad_maxima"),
                        rs.getString("edificio"),
                        rs.getInt("nivel")
                );               
                System.out.println(salon.toString());
            }
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Salones");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return salon;
    }
    
    @FXML
    private void seleccionarElemento() {
        if(existeElementoSeleccionado()) {
            //Porque el id es numerico y el txt es texto se hace la convercion
            txtId.setText(String.valueOf(((Cursos) tblCursos
                    .getSelectionModel().getSelectedItem()).getId())); 
            txtNombreCurso.setText(((Cursos) tblCursos
                    .getSelectionModel().getSelectedItem()).getNombreCurso());  
            
            cmbCarreraTecnica.getSelectionModel().select(
                    buscarCarrerasTecnicas( //Multilinea para que se visualize.
                            ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                    .getCarreraTecnicaId()));
            
            cmbHorario.getSelectionModel().select(
                    buscarHorarios(
                           ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                   .getHorarioId()));
            
            cmbInstructor.getSelectionModel().select(
                    buscarInstructor(
                            ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                    .getInstructorId()));
            
            cmbSalon.getSelectionModel().select(
                    buscarSalon(
                            ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                    .getSalonId()));
        }
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
}


