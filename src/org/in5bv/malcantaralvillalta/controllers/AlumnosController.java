package org.in5bv.malcantaralvillalta.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bv.malcantaralvillalta.system.Principal;

import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bv.malcantaralvillalta.db.Conexion;

import org.in5bv.malcantaralvillalta.models.Alumnos;
import org.in5bv.malcantaralvillalta.reports.GenerarReporte;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 19/04/2022
 * @time 13:41:02
 *
 * Código Tecnico: IN5BV
 */
//En interfaces si se puede imnplementar mas interfaces
public class AlumnosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGE = "org/in5bv/malcantaralvillalta/resources/images/";

    private Principal escenarioPrincipal;

    private ObservableList<Alumnos> listaAlumnos;
    
    

    @FXML
    private TextField txtCarne;
    @FXML
    private TextField txtNombre1;
    @FXML
    private TextField txtNombre2;
    @FXML
    private TextField txtNombre3;
    @FXML
    private TextField txtApellido1;
    
    @FXML
    private TextField txtApellido2;
    
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
    private TableView tblAlumnos;
    //Table Column
    @FXML
    private TableColumn colCarne;
    @FXML
    private TableColumn colNombre1;
    @FXML
    private TableColumn colNombre2;
    @FXML
    private TableColumn colNombre3;
    @FXML
    private TableColumn colApellido1;
    @FXML
    private TableColumn colApellido2;

    //Image Regresar
    @FXML
    private ImageView imgRegresar;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    
    public static void CerrarPrograma(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void clicCerrar(ActionEvent event) {
        CerrarPrograma(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos(); // Cargar datos llama al getAlumnos
    }
    

    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try
        {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read()}");
            
            System.out.println("\n" +pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                Alumnos alumno = new Alumnos();
                //Colocar un salto de linea con los registros al comienzo con las entidades salones y carreras tecnicas
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));
                System.out.println(alumno.toString());

                lista.add(alumno);
                for (int i = 0; i <= lista.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);

            // De esta manera un arraylist a un ObservableList en el parentesis pongo lo que deseo transformar.
            listaAlumnos = FXCollections.observableArrayList(lista);

        } catch (SQLException e)
        {
            System.err.println("Se produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQlState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (pstmt != null)
                {
                    pstmt.close();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return listaAlumnos;
    }

    public void cargarDatos() {
        Alumnos alumno = new Alumnos();
        
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
        alumno.setContador(contador);
        txtRegistro.setText(Integer.toString(alumno.getContador()));
    }

    private boolean existeElementoSeleccionado() {
        return (tblAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElementos() {
        if (existeElementoSeleccionado())
        {
            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }
    }

    private boolean elimnarAlumno() {
        if (existeElementoSeleccionado())
        {
            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(alumno);
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_alumnos_delete(?)");
                pstmt.setString(1, alumno.getCarne());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e)
            {
                System.err.println("\nSe produjo un error al intentar eliminar el siguente Registro: " + alumno.toString());
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (pstmt != null)
                    {
                        pstmt.close();
                    }
                } catch (Exception e)
                {
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
        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
    }

    private void habilitarCampos() {
        txtCarne.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    private void limpiarCampos() {
        //txtCarne.setText("");
        txtCarne.clear();
        txtNombre1.clear();
        txtNombre3.clear();
        txtNombre2.clear();
        txtApellido1.clear();
        txtApellido2.clear();
    }

    @FXML
    private void clicNuevo() {
        switch (operacion)
        {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();         
                
                tblAlumnos.setDisable(true);

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
                if (agregarAlumno())
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
                    
                    tblAlumnos.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    private boolean agregarAlumno() {

        Alumnos alumno = new Alumnos();
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try
        {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_create(?,?,?,?,?,?)}");

            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaAlumnos.add(alumno);

            return true;
        } catch (SQLException e)
        {
            System.err.println("\nSe produjo un error al intentar insertar el siguente registro" + alumno.toString());
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (pstmt != null)
                {
                    pstmt.close();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }

    @FXML
    private void clicModificar() {
        switch (operacion)
        {
            case NINGUNO:
                if (existeElementoSeleccionado())
                {
                    habilitarCampos();                    

                    //para que no se pueda editar ese campo y que se desabilite
                    txtCarne.setDisable(true);
                    txtCarne.setEditable(false);

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
                tblAlumnos.setDisable(false);

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
                if (actualizarAlumno())
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

    private boolean actualizarAlumno() {

        if (existeElementoSeleccionado())
        {
            Alumnos alumno = new Alumnos();
            alumno.setCarne(txtCarne.getText());
            alumno.setNombre1(txtNombre1.getText());
            alumno.setNombre2(txtNombre2.getText());
            alumno.setNombre3(txtNombre3.getText());
            alumno.setApellido1(txtApellido1.getText());
            alumno.setApellido2(txtApellido2.getText());

            PreparedStatement pstmt = null;

            try
            {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_alumnos_update(?,?,?,?,?,?)");
                pstmt.setString(1, alumno.getCarne());
                pstmt.setString(2, alumno.getNombre1());
                pstmt.setString(3, alumno.getNombre2());
                pstmt.setString(4, alumno.getNombre3());
                pstmt.setString(5, alumno.getApellido1());
                pstmt.setString(6, alumno.getApellido2());

                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e)
            {
                System.err.println("\nSe produjo un error al intentar actualizar el siguente Registro: " + alumno.toString());
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (pstmt != null)
                    {
                        pstmt.close();
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @FXML
    private void clicEliminar() {
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
                tblAlumnos.getSelectionModel().clearSelection();

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
                        if (elimnarAlumno()) {
                            listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
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
                        tblAlumnos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
 
                } else {
                    //Para poner una alerta
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
    // Para este clic se hace en scenden Binder en on mause clicked
    public void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    @FXML
    private void clicReporte() {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("LOGO_HEADER", PAQUETE_IMAGE + "alumni.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGE + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteAlumnos1.jasper",parametros, "ReporteAlumnos");
        
    }

}
