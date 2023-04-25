package org.in5bv.malcantaralvillalta.controllers;

import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.DepthTest;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import org.in5bv.malcantaralvillalta.system.Principal;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bv.malcantaralvillalta.db.Conexion;

import org.in5bv.malcantaralvillalta.models.CarrerasTecnicas;
import org.in5bv.malcantaralvillalta.reports.GenerarReporte;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 29/04/2022
 * @time 09:35:14
 *
 * Código Tecnico: IN5BV
 *
 * Código Tecnico: IN5BV
 */
public class CarrerasTecnicasController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;
    private final String PAQUETE_IMAGE = "org/in5bv/malcantaralvillalta/resources/images/";
    private Principal escenarioPrincipal;
    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;

    @FXML
    private TextField txtCodigoTecnico;
    @FXML
    private TextField txtCarrera;
    @FXML
    private TextField txtGrado;
    @FXML
    private TextField txtSeccion;
    @FXML
    private TextField txtJornada;
    
    @FXML
    private Button btnCerrar;
    @FXML
    private TextField txtRegistro;    
    private int contador = 0;

    //Botones
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;

    //Table
    @FXML
    private TableView tblCarrerasTecnicas;

    //Table Column
    @FXML
    private TableColumn colCodigoTecnico;
    @FXML
    private TableColumn colCarrera;
    @FXML
    private TableColumn colGrado;
    @FXML
    private TableColumn colSeccion;
    @FXML
    private TableColumn colJornada;

    //Image Regresar
    @FXML
    private ImageView imgRegresar;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();// Cargar datos llama al getCarreras tecnicas
    }
    
    public static void CerrarPrograma(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void clicCerrar(ActionEvent event) {
        CerrarPrograma(event);
    }
    

    public ObservableList getCarrerasTecnicas() {
        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //String sql = "SELECT cane, nombre1, nombre2, nombre3, apellido1, apellido2 FROM Alumnos;";          
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_read()}");
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

                lista.add(carrerasTecnicas);
                for (int i = 0; i <= lista.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);
            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);

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
        return listaCarrerasTecnicas;
    }

    public void cargarDatos() {
        CarrerasTecnicas carrera = new CarrerasTecnicas();
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
        
        carrera.setContador(contador);
        txtRegistro.setText(Integer.toString(carrera.getContador()));

    }

    private boolean existeElementoSeleccionado() {
        return (tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElementos() {

        if (existeElementoSeleccionado()) {
            txtCodigoTecnico.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtSeccion.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion());
            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());

        }
    }

    private boolean elimnarAlumno() {
        if (existeElementoSeleccionado()) {
            CarrerasTecnicas carrerasTecnica = (CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            System.out.println(carrerasTecnica);
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_delete (?)");
                pstmt.setString(1, carrerasTecnica.getCodigoTecnico());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguente Registro: " + carrerasTecnica.toString());
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

    private void deshabilitarCampos() {
        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);

    }

    private void habilitarCampos() {
        txtCodigoTecnico.setEditable(true);
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);

        txtCodigoTecnico.setDisable(false);
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
    }

    private void limpiarCampos() {
        //txtCarne.setText("");
        txtCodigoTecnico.clear();
        txtCarrera.clear();
        txtSeccion.clear();
        txtGrado.clear();
        txtJornada.clear();
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblCarrerasTecnicas.setDisable(true);

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
                if (agregarCarrerasTecnica()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnReporte.setDisable(false);
                    
                    tblCarrerasTecnicas.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;

        }
    }

    private boolean agregarCarrerasTecnica() {

        CarrerasTecnicas carrerasTecnica = new CarrerasTecnicas();
        carrerasTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
        carrerasTecnica.setCarrera(txtCarrera.getText());
        carrerasTecnica.setGrado(txtGrado.getText());
        carrerasTecnica.setSeccion(txtSeccion.getText());
        carrerasTecnica.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_create (?,?,?,?,?)");

            pstmt.setString(1, carrerasTecnica.getCodigoTecnico());
            pstmt.setString(2, carrerasTecnica.getCarrera());
            pstmt.setString(3, carrerasTecnica.getGrado());
            pstmt.setString(4, carrerasTecnica.getSeccion());
            pstmt.setString(5, carrerasTecnica.getJornada());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaCarrerasTecnicas.add(carrerasTecnica);

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguente registro" + carrerasTecnica.toString());
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

    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    //para que no se pueda editar ese campo y que se desabilite
                    txtCodigoTecnico.setDisable(true);
                    txtCodigoTecnico.setEditable(false);

                    btnModificar.setText(" Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "guardar.png"));

                    btnEliminar.setText(" Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar3.png"));

                    btnNuevo.setDisable(true);

                    btnReporte.setDisable(true);

                    operacion = Operacion.ACTUALIZAR;//GUARDAR
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecione un registro.");
                    alert.show();
                }
                break;
            case GUARDAR: //CANSELAR

                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                btnEliminar.setDisable(false);
                btnReporte.setDisable(false);

                //Implementarlo en los botones de cancelar.
                deshabilitarCampos();
                limpiarCampos();
                tblCarrerasTecnicas.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (actualizarCarrerasTecnica()) {
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

    private boolean actualizarCarrerasTecnica() {

        if (existeElementoSeleccionado()) {
            CarrerasTecnicas carrerasTecnica = new CarrerasTecnicas();
            carrerasTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
            carrerasTecnica.setCarrera(txtCarrera.getText());
            carrerasTecnica.setGrado(txtGrado.getText());
            carrerasTecnica.setSeccion(txtSeccion.getText());
            carrerasTecnica.setJornada(txtJornada.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_update(?,?,?,?,?)");
                pstmt.setString(1, carrerasTecnica.getCodigoTecnico());
                pstmt.setString(2, carrerasTecnica.getCarrera());
                pstmt.setString(3, carrerasTecnica.getGrado());
                pstmt.setString(4, carrerasTecnica.getSeccion());
                pstmt.setString(5, carrerasTecnica.getJornada());

                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguente Registro: " + carrerasTecnica.toString());
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
    private void clicEliminar() {
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
                
                //Sentencia que al cancelar la opcion de modificar ya no esten canceladas las casillas
                tblCarrerasTecnicas.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: //Eliminar un Registro 
                if (existeElementoSeleccionado()) {
                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Esta seguro que desea eliminar el registro seleccionado?");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "eliminar.png"));
                    
                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (result.get().equals(ButtonType.OK)) {
                        if (elimnarAlumno()) {
                        listaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
                        limpiarCampos();
                        cargarDatos();
                        
                        Alert alertInformation = new Alert(Alert.AlertType.CONFIRMATION);
                        alertInformation.setTitle("Control Académico Kinal");
                        alertInformation.setHeaderText(null);
                        alertInformation.setContentText("Registro eliminado Exitosamente");
                        alertInformation.show();
                       }
                        
                    } else {
                        alert.close();
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }

                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }
                break;
        }
    }

   

    @FXML
    private void clicReporte(ActionEvent event) {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "carrera.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteCarreras13.jasper",parametros, "ReporteCarreras");

    }

}