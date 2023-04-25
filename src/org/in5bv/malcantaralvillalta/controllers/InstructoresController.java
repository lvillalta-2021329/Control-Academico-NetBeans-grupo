package org.in5bv.malcantaralvillalta.controllers;

import java.beans.Introspector;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bv.malcantaralvillalta.db.Conexion;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

//import java.util.Date;

import org.in5bv.malcantaralvillalta.system.Principal;

import org.in5bv.malcantaralvillalta.models.Instructores;
import org.in5bv.malcantaralvillalta.models.Alumnos;
import org.in5bv.malcantaralvillalta.reports.GenerarReporte;

/**
 *
 * @author Marcos Alcántara Hernández
 * @date 14/06/2022
 * @time 12:11:12
 */

public class InstructoresController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    private final String PAQUETE_IMAGE = "org/in5bv/malcantaralvillalta/resources/images/";
    
    private ObservableList<Instructores> listaObservableInstructores;
    
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
    private TableView<Instructores> tblInstructores;
    @FXML
    private TableColumn<Instructores, Integer> colId;
    @FXML
    private TableColumn<Instructores, String> colNombre1;
    @FXML
    private TableColumn<Instructores, String> colNombre2;
    @FXML
    private TableColumn<Instructores, String> colNombre3;
    @FXML
    private TableColumn<Instructores, String> colApellido1;
    @FXML
    private TableColumn<Instructores, String> colApellido2;
    @FXML
    private TableColumn<Instructores, String> colDireccion;
    @FXML
    private TableColumn<Instructores, String> colEmail;
    @FXML
    private TableColumn<Instructores, String> colTelefono;
    @FXML
    private TableColumn<Instructores, LocalDate> colFechaNacimiento;
    
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre1;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtNombre3;
    @FXML
    private TextField txtApellido2;
    @FXML
    private TextField txtNombre2;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtDirecion;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpkFechaNacimiento;
    @FXML
    private ImageView imgRegresar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    public static void CerrarPrograma(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void clicCerrar(ActionEvent event) {
        CerrarPrograma(event);
    }
    
    private void habilitarCampos() {
        txtId.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtTelefono.setEditable(true);
        txtDirecion.setEditable(true);
        txtEmail.setEditable(true);
        dpkFechaNacimiento.setEditable(true);
        
        txtId.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtTelefono.setDisable(false);
        txtDirecion.setDisable(false);
        txtEmail.setDisable(false);
        dpkFechaNacimiento.setDisable(false);
    }
    
    private void limpiarCampos() {
        txtId.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtTelefono.clear();
        txtDirecion.clear();
        txtEmail.clear();
        dpkFechaNacimiento.getEditor().clear();
    }
    
    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtTelefono.setEditable(false);
        txtDirecion.setEditable(false);
        txtEmail.setEditable(false);
        dpkFechaNacimiento.setEditable(false);
        
        txtId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtTelefono.setDisable(true);
        txtDirecion.setDisable(true);
        txtEmail.setDisable(true);
        dpkFechaNacimiento.setDisable(true);
    }
    
    private boolean existeElementoSeleccionado() {
        return (tblInstructores.getSelectionModel().getSelectedItem() != null);
    }

    private boolean agregarInstructor() {

        Instructores instructor = new Instructores();
        //instructor.setId(Integer.parseInt(txtId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDirecion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaNacimiento(dpkFechaNacimiento.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_create(?,?,?,?,?,?,?,?,?)}");
                    
            pstmt.setString(1, instructor.getNombre1());
            pstmt.setString(2, instructor.getNombre2());
            pstmt.setString(3, instructor.getNombre3());
            pstmt.setString(4, instructor.getApellido1());
            pstmt.setString(5, instructor.getApellido2());
            pstmt.setString(6, instructor.getDireccion());
            pstmt.setString(7, instructor.getEmail());
            pstmt.setString(8, instructor.getTelefono());
            pstmt.setDate(9,  Date.valueOf(instructor.getFechaNacimiento()));

            System.out.println(pstmt.toString());

            pstmt.execute();
            listaObservableInstructores.add(instructor);

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguente registro" + instructor.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean actualizarInstructor() {

        if (existeElementoSeleccionado()) {
            
            Instructores instructor = new Instructores();
            
            instructor.setId(Integer.parseInt(txtId.getText()));
            instructor.setNombre1(txtNombre1.getText());
            instructor.setNombre2(txtNombre2.getText());
            instructor.setNombre3(txtNombre3.getText());
            instructor.setApellido1(txtApellido1.getText());
            instructor.setApellido2(txtApellido2.getText());
            instructor.setDireccion(txtDirecion.getText());
            instructor.setEmail(txtEmail.getText());
            instructor.setTelefono(txtTelefono.getText());
            instructor.setFechaNacimiento(dpkFechaNacimiento.getValue());


            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_instructores_update(?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, instructor.getId());
                pstmt.setString(2, instructor.getNombre1());
                pstmt.setString(3, instructor.getNombre2());
                pstmt.setString(4, instructor.getNombre3());
                pstmt.setString(5, instructor.getApellido1());
                pstmt.setString(6, instructor.getApellido2());
                pstmt.setString(7, instructor.getDireccion());
                pstmt.setString(8, instructor.getEmail());
                pstmt.setString(9, instructor.getTelefono());
                pstmt.setDate(10, Date.valueOf(instructor.getFechaNacimiento()));

                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguente Registro: " + instructor.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e)  {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    private boolean elimnarInstructor() {
        if (existeElementoSeleccionado()) {
            Instructores instructor = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            
            System.out.println(instructor);
            PreparedStatement pstmt = null;
            
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_instructores_delete(?)");
                pstmt.setInt(1, instructor.getId());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguente Registro: " + instructor.toString());
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
        }
        return false;
    }
    
    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion)
        {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();         
                
                tblInstructores.setDisable(true);
                txtId.setDisable(true);

                btnNuevo.setText(" Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "guardar.png"));

                btnModificar.setText(" Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "cancelar3.png")); //Cambiar imagen ya

                btnEliminar.setDisable(true);

                btnReporte.setDisable(true);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (agregarInstructor()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnReporte.setDisable(false);
                    
                    tblInstructores.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();                    
                    
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
            case GUARDAR: //Cancelar insercion
                
                tblInstructores.setDisable(false);

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
                if (actualizarInstructor())
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
        switch (operacion) {
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
                tblInstructores.getSelectionModel().clearSelection();

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
                        if (elimnarInstructor()) {
                            listaObservableInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
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
                        tblInstructores.getSelectionModel().clearSelection();
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
        parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "instructor.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteInstructores.jasper",parametros, "ReporteInstructores");
        
       
    }

    @FXML
    private void seleccionarElementos() {
        if (existeElementoSeleccionado())
        {
            txtId.setText(String.valueOf(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId())); 
            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDirecion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            dpkFechaNacimiento.setValue(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFechaNacimiento());
        }
    }
    
    public void cargarDatos() {
        Instructores intructor = new Instructores();
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<Instructores, Integer>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Instructores, String>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Instructores, String>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Instructores, String>("telefono"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        
        intructor.setContador(contador);
        txtRegistro.setText(Integer.toString(intructor.getContador()));
    }
    
    private ObservableList getInstructores() {

        ArrayList<Instructores> arrayListInstructores = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_read()}");
            
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
                
                System.out.println(instructor.toString());

                arrayListInstructores.add(instructor);
                for (int i = 0; i <= arrayListInstructores.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);

            listaObservableInstructores = FXCollections.observableArrayList(arrayListInstructores);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Alumnos");
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

        return listaObservableInstructores;
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
