package org.in5bv.malcantaralvillalta.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bv.malcantaralvillalta.db.Conexion;
import org.in5bv.malcantaralvillalta.models.Salones;
import org.in5bv.malcantaralvillalta.reports.GenerarReporte;
import org.in5bv.malcantaralvillalta.system.Principal;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 27/04/2022
 * @time 21:32:52
 * 
 * Código Tecnico: IN5BV
 */
public class SalonesController implements Initializable {
    
     private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    private final String PAQUETE_IMAGE = "org/in5bv/malcantaralvillalta/resources/images/";
    
    private Principal escenarioPrincipal;
    
    private ObservableList<Salones> listaSalones;
    
    
    @FXML
    private TextField txtCodigoSalon;    
    @FXML
    private TextField txtCapacidadMaxima;
    @FXML
    private TextField txtEdificio;
    @FXML
    private TextField txtNivel;
    @FXML
    private TextField txtDescripcion;
    
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
    private TableView tblSalones;
    
    //Table Column
    @FXML 
    private TableColumn colCodigo;
    @FXML 
    private TableColumn colCapacidadMax;
    @FXML
    private TableColumn colEdificio;
    @FXML
    private TableColumn colNivel;
    @FXML
    private TableColumn colDescripcion;
    
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
        cargarDatos();
    }
    
    public static void CerrarPrograma(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void clicCerrar(ActionEvent event) {
        CerrarPrograma(event);
    }
    
    public ObservableList getSalones() {
        
        ArrayList<Salones> lista = new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;      
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_read()");
            rs = pstmt.executeQuery();           
            while(rs.next()) {
                Salones salon = new Salones();
                //Colocar un salto de linea con los registros al comienzo con las entidades salones y carreras tecnicas
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));    
                System.out.println(salon.toString());
                
                lista.add(salon);
                for (int i = 0; i <= lista.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);
            
            // De esta manera un arraylist a un ObservableList en el parentesis pongo lo que deseo transformar.
            listaSalones = FXCollections.observableArrayList(lista);
            
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Alumnos");
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
        
        return listaSalones;
    }
    
    public void cargarDatos() {
        Salones salon = new Salones();
        tblSalones.setItems(getSalones());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Salones,String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones,String>("descripcion"));
        //Solo me acepta integer en numericos aqui no int
        colCapacidadMax.setCellValueFactory(new PropertyValueFactory<Salones,Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones,String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones,Integer>("nivel"));
        salon.setContador(contador);
        txtRegistro.setText(Integer.toString(salon.getContador()));
    }
    
    private boolean existeElementoSeleccionado() {
        return(tblSalones.getSelectionModel().getSelectedItem() != null);
    }
    
    @FXML
    public void seleccionarElementos() {
        if (existeElementoSeleccionado()) {
            
            txtCodigoSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            //(Capacidad Maxima) En Modificar devemos convertirlo a String de esta Manera del ValueOf en otras sera a int
            txtCapacidadMaxima.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
            //Este es Valor String no nesecita una convercion
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
        }
    }
    
    private boolean elimnarSalon() {
        if(existeElementoSeleccionado()) {
            
            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println("\n" + salon);
            PreparedStatement pstmt = null;
            
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_delete(?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                System.out.println(pstmt.toString());
                
                pstmt.execute();
                return true;
                
            } catch(SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguente registro: " + salon.toString());
                e.printStackTrace();
            } catch(Exception e) {
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
    
    private void deshabilitarCampos() {
        txtCodigoSalon.setEditable(false);
        txtCapacidadMaxima.setEditable(false);
        txtEdificio.setEditable(false);
        txtNivel.setEditable(false);
        txtDescripcion.setEditable(false);
        
        txtCodigoSalon.setDisable(true);
        txtCapacidadMaxima.setDisable(true);
        txtEdificio.setDisable(true);
        txtNivel.setDisable(true);
        txtDescripcion.setDisable(true);
    }
    
    private void habilitarCampos() {
        txtCodigoSalon.setEditable(true);
        txtCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);
        txtDescripcion.setEditable(true);
        
        txtCodigoSalon.setDisable(false);
        txtCapacidadMaxima.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);
        txtDescripcion.setDisable(false);
    }
    
    private void limpiarCampos() {
        //txtCarne.setText("");
        txtCodigoSalon.clear();
        txtCapacidadMaxima.clear();
        txtNivel.clear();
        txtEdificio.clear();
        txtDescripcion.clear();   
    }
    
    private boolean agregarSalon() {
        
        Salones salon = new Salones();
        salon.setCodigoSalon(txtCodigoSalon.getText());
        //Manera de pegar el String a un Dato numerico
        salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
        salon.setEdificio(txtEdificio.getText());
        //Manera de pegar el String a un Dato numerico
        salon.setNivel(Integer.parseInt(txtNivel.getText()));
        salon.setDescripcion(txtDescripcion.getText());
        
        PreparedStatement pstmt = null;
        
        try {
            
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_create(?,?,?,?,?)}");
            
            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setInt(2, salon.getCapacidadMaxima());
            pstmt.setString(3, salon.getEdificio());
            pstmt.setInt(4, salon.getNivel());
            pstmt.setString(5, salon.getDescripcion());
            
            System.out.println(pstmt.toString() + "\n");
            
            pstmt.execute();
            
            listaSalones.add(salon);
            
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insetar el siguente registro" + salon.toString());
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
    private void clicNuevo() {
        switch(operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblSalones.setDisable(true);
                
                btnNuevo.setText(" Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "guardar.png"));
                
                btnModificar.setText(" Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "cancelar3.png")); //Cambiar imagen ya
                
                btnEliminar.setDisable(true);
                
                btnReporte.setDisable(true);
                
                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (agregarSalon()) {
                    
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnReporte.setDisable(false);
                    
                    tblSalones.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
                
        }
    }
    
    @FXML
    private void clicModificar() {   
        switch (operacion) {
            case NINGUNO:
                
                if(existeElementoSeleccionado()) {
                    
                    habilitarCampos();
                    
                    txtCodigoSalon.setDisable(true);
                    txtCodigoSalon.setEditable(false);

                    btnModificar.setText(" Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "guardar.png"));

                    btnEliminar.setText(" Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar3.png"));

                    btnNuevo.setDisable(true);

                    btnReporte.setDisable(true);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecione un registro.");
                    alert.show();
                }               
                break;
            case GUARDAR: // CANCELAR INSERCION
                
                tblSalones.setDisable(false);
                
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
                if (actualizarSalon()) {
                    
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
    
    private boolean actualizarSalon() {
        if (existeElementoSeleccionado()) {
            
            Salones salon = new Salones();
            salon.setCodigoSalon(txtCodigoSalon.getText());
            salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel(Integer.parseInt(txtNivel.getText()));
            salon.setDescripcion(txtDescripcion.getText());
            
            PreparedStatement pstmt = null;
            
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_update(?,?,?,?,?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                pstmt.setInt(2, salon.getCapacidadMaxima());
                pstmt.setString(3, salon.getEdificio());
                pstmt.setInt(4, salon.getNivel());
                pstmt.setString(5, salon.getDescripcion());
                
                System.out.println(pstmt.toString());
                
                pstmt.execute();
                
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguente Registro: " + salon.toString());
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
    public void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: // Guardar Ninguno
                
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));
                
                btnNuevo.setDisable(false);

                btnReporte.setDisable(false);
                
                limpiarCampos();
                deshabilitarCampos();
                
                tblSalones.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
            break;
            case NINGUNO: //Eliminar un Registro
                if (existeElementoSeleccionado()) {
                    
                    //Alerta para confirmacion de eliminacion
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Esta seguro que desea eliminar el registro selecionado");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "eliminar.png"));
                    
                    Optional <ButtonType> result = alert.showAndWait();
                    
                    if(result.get().equals(ButtonType.OK)) {
                        if (elimnarSalon()) {
                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();
                            
                        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                        alertInformation.setTitle("Control Academico Kinal");
                        alertInformation.setHeaderText(null);
                        alertInformation.setContentText("Registro Eliminado Exitosamente");
                        alertInformation.show();
                            
                        }                       
                    } else {
                        alert.close();
                        tblSalones.getSelectionModel().clearSelection();
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
    public void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    @FXML
    private void clicReporte(ActionEvent event) {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "salon.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper",parametros, "ReporteSalones");
        
    }
    

}
