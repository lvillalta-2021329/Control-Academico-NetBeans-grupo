package org.in5bv.malcantaralvillalta.controllers;


import org.in5bv.malcantaralvillalta.models.AsignacionesAlumnos;
import org.in5bv.malcantaralvillalta.models.Alumnos;
import org.in5bv.malcantaralvillalta.models.Cursos;


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

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bv.malcantaralvillalta.db.Conexion;


import org.in5bv.malcantaralvillalta.system.Principal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.in5bv.malcantaralvillalta.reports.GenerarReporte;

/**
 *
 * @author Jorge Luis Pérez Canto
 * @date 2/06/2022
 * @time 10:53:37
 *
 */
public class AsignacionesAlumnosController implements Initializable {

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
    private Button btnCerrar;
    
    @FXML
    private TextField txtRegistro;
    
    private int contador = 0;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgReporte;
    @FXML
    private TextField txtId;
    @FXML
    private ImageView imgRegresar;
    @FXML
    private TableView<AsignacionesAlumnos> tblAsignacionAlumnos;
    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colId;
    @FXML
    private TableColumn<AsignacionesAlumnos, String> colCarne;
    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colCursoId;
    @FXML
    private TableColumn<AsignacionesAlumnos, LocalDateTime> colFechaAsignacion;

    @FXML
    private ComboBox<Alumnos> cmbAlumno;
    @FXML
    private ComboBox<Cursos> cmbCurso;

    private Principal escenarioPrincipal;
    @FXML
    private DatePicker dpkFechaAsignacion;
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGES = "org/in5bv/malcantaralvillalta/resources/images/";
    

    private ObservableList<Alumnos> listaObservableAlumnos;
    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<AsignacionesAlumnos> listaObservableAsignaciones;

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
    
    private void deshabilitarCampos() {
        txtId.setEditable(false);
        cmbCurso.setEditable(false);
        cmbAlumno.setEditable(false);
        dpkFechaAsignacion.setEditable(false);

        txtId.setDisable(true);
        cmbCurso.setDisable(true);
        cmbAlumno.setDisable(true);
        dpkFechaAsignacion.setDisable(true);
    }

    private void habilitarCampos() {
        txtId.setEditable(true);        
        dpkFechaAsignacion.setEditable(true);

        txtId.setDisable(false);
        cmbCurso.setDisable(false);
        cmbAlumno.setDisable(false);
        dpkFechaAsignacion.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.clear();
        cmbCurso.valueProperty().set(null);
        cmbAlumno.valueProperty().set(null);
        dpkFechaAsignacion.getEditor().clear();
    }

    public ObservableList getAlumnos() {
        ArrayList<Alumnos> arrayListAlumnos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read()}");
            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // Opción 1:
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                /*
                // Opción 2:
                Alumnos alumno = new Alumnos(
                        rs.getString("carne"),
                        rs.getString("nombre1"),
                        rs.getString("nombre2"),
                        rs.getString("nombre3"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2")
                );
                 */
                System.out.println(alumno.toString());
                arrayListAlumnos.add(alumno);
                for (int i = 0; i <= arrayListAlumnos.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);
            listaObservableAlumnos = FXCollections.observableArrayList(arrayListAlumnos);

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
        return listaObservableAlumnos;
    }

    public Alumnos buscarAlumno(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Alumnos alumno = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                alumno = new Alumnos(
                        rs.getString("carne"),
                        rs.getString("nombre1"),
                        rs.getString("nombre2"),
                        rs.getString("nombre3"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2")
                );

                System.out.println(alumno.toString());
            }

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
        return alumno;
    }

    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt("id"));
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
            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);

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

        return listaObservableCursos;
    }

    private Cursos buscarCurso(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setInstructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());

            }

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

        return curso;
    }

    // read -> Lista todos los registros
    public ObservableList getAsignacionAlumnos() {
        ArrayList<AsignacionesAlumnos> arrayListAsignaciones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
                asignacion.setId(rs.getInt("id"));
                asignacion.setAlumnoId(rs.getString("alumno_id"));
                asignacion.setCursoId(rs.getInt("curso_id"));
                asignacion.setFechaAsignacion(rs.getTimestamp("fecha_asignacion").toLocalDateTime());

                System.out.println(asignacion);

                arrayListAsignaciones.add(asignacion);
                for (int i = 0; i <= arrayListAsignaciones.size() ; i++) {                  
                    contador = 0 + i;
                }
            }
            System.out.println(contador);
            listaObservableAsignaciones = FXCollections.observableArrayList(arrayListAsignaciones);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Asignacion alumnos");
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

        return listaObservableAsignaciones;
    }

    public void cargarDatos() {
        AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
        tblAsignacionAlumnos.setItems(getAsignacionAlumnos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCarne.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        colCursoId.setCellValueFactory(new PropertyValueFactory<>("cursoId"));
        colFechaAsignacion.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        cmbAlumno.setItems(getAlumnos());
        cmbCurso.setItems(getCursos());
        
        asignacion.setContador(contador);
        txtRegistro.setText(Integer.toString(asignacion.getContador()));
    }

    private boolean existeElementoSeleccionado() {
        return (tblAsignacionAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElementos() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getId()));
            dpkFechaAsignacion.setValue(((AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getFechaAsignacion().toLocalDate());

            cmbCurso.getSelectionModel().select(
                    buscarCurso(
                            ((AsignacionesAlumnos) tblAsignacionAlumnos
                                    .getSelectionModel().getSelectedItem()).getCursoId()
                    )
            );

            cmbAlumno.getSelectionModel().select(buscarAlumno(((AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getAlumnoId()));

        }
    }

    private boolean agregarAsignacion() {
        
        AsignacionesAlumnos asignacion = new AsignacionesAlumnos(
               
                cmbAlumno.getValue().getCarne(), 
                cmbCurso.getValue().getId(), 
                dpkFechaAsignacion.getValue().atStartOfDay()
        );

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_create(?, ?, ?)}");

            pstmt.setString(1, asignacion.getAlumnoId());
            pstmt.setInt(2, asignacion.getCursoId());
            pstmt.setTimestamp(3, Timestamp.valueOf(asignacion.getFechaAsignacion()));

            System.out.println(pstmt.toString());

            pstmt.execute();
            
            listaObservableAsignaciones.add(asignacion);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar "
                    + "el siguiente registro: " + asignacion.toString());
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
    
    private boolean actualizarAsignacion() {
        
        AsignacionesAlumnos asignacion = new AsignacionesAlumnos(
                Integer.parseInt(txtId.getText()),
                cmbAlumno.getValue().getCarne(), 
                cmbCurso.getValue().getId(), 
                dpkFechaAsignacion.getValue().atStartOfDay()
        );

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_update(?, ?, ?, ?)}");

            pstmt.setInt(1, asignacion.getId());
            pstmt.setString(2, asignacion.getAlumnoId());
            pstmt.setInt(3, asignacion.getCursoId());
            pstmt.setTimestamp(4, Timestamp.valueOf(asignacion.getFechaAsignacion()));

            System.out.println(pstmt.toString());

            pstmt.execute();
            
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar "
                    + "el siguiente registro: " + asignacion.toString());
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

    private boolean eliminarAsignacion() {

        AsignacionesAlumnos asignacion = (AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem();

        System.out.println(asignacion.toString());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_delete(?)}");

            pstmt.setInt(1, asignacion.getId());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaObservableAsignaciones.remove(tblAsignacionAlumnos.getSelectionModel().getFocusedIndex());

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + asignacion.toString());
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
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                limpiarCampos();
                habilitarCampos();
                tblAsignacionAlumnos.setDisable(true);

                // Clave primaria para autoincrementables.
                txtId.setEditable(false);
                txtId.setDisable(true);

                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "guardar.png"));

                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancelar3.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);

                btnReporte.setDisable(true);
                btnReporte.setVisible(false);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (agregarAsignacion()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    tblAsignacionAlumnos.setDisable(false);
                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "agregar.png"));
                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));
                    btnEliminar.setDisable(false);
                    //btnEliminar.setVisible(true);
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);
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
                    txtId.setEditable(false);
                    txtId.setDisable(true);
                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "guardar.png"));
                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar3.png"));
                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }
                break;
            case GUARDAR: // Cancelar inserción
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "agregar.png"));
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));
                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);
                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();
                tblAsignacionAlumnos.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarAsignacion()) {
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();
                        tblAsignacionAlumnos.setDisable(false);
                        tblAsignacionAlumnos.getSelectionModel().clearSelection();

                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGES + "agregar.png"));
                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));
                        btnEliminar.setDisable(false);
                        btnEliminar.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);
                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        switch (operacion) {
            case ACTUALIZAR: // Cancelar una modificación
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblAsignacionAlumnos.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: // Eliminar un registro
                if (existeElementoSeleccionado()) {
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirm.setTitle("Control Académico Kinal");
                    alertConfirm.setHeaderText(null);
                    alertConfirm.setContentText("¿Está seguro que desea eliminar el registro seleccionado?");

                    Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "eliminar.png"));

                    Optional<ButtonType> result = alertConfirm.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarAsignacion()) {
                            listaObservableAsignaciones.remove(tblAsignacionAlumnos.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                            alertInformation.setTitle("Control Académico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro eliminado exitosamente");
                            alertInformation.show();
                        }
                    } else if (result.get().equals(ButtonType.CANCEL)) {
                        alertConfirm.close();
                        tblAsignacionAlumnos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {
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
       parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "asignacion.png");
       parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "Alumno.png");
       
       GenerarReporte.getInstance().mostrarReporte("ReporteCarrerasTecnicas.jasper",parametros, "ReporteCarreras");

    }

}

