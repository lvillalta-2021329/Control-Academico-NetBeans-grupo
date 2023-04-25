package org.in5bv.malcantaralvillalta.db;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

/**
 *
 * @author Marcos Alcántara Hernández
 * @author Lionar Gerardy Villalta Barrientos
 * @date 3/05/2022
 * @time 16:59:03
 * 
 * Código Técnico: IN5BV
 */
public class Conexion {
    
    private final String IP_SERVER = "localhost";
    private final String PORT = "3306";
    private final String DB = "db_control_academico_in5bv";
    
    private final String USER = "kinal"; //kinal
    private final String PASSWORD = "admin"; //admin
    
    private final String URL;
    private Connection conexion;
    
    //Conexion con mayuscula es diferente a la que esta minusculas
    private static Conexion instancia;
    
    public static Conexion getInstance() {
        
        if (instancia == null) {
            instancia = new Conexion();
        }       
        return instancia;
    }
    
    private Conexion() {
        // De esta manera es mejor ya que si se cambia algo, solo cambiaria lo afectado
        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB + "?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
        
        try {                            
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Class forName no se Modifca.
            
            //Creamos el objeto de conexion a la base de datos
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion Exitosa"); 
            
            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Diver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion());
             
        } catch (ClassNotFoundException ex) {
            System.err.println("No se encuentra definicion para la clase");
            ex.printStackTrace();
        } catch (CommunicationsException ex) {
            System.err.println("EROR: No se puede establecer conexion con el servidor de MYSQL"
                    + "\nRecomendaciones:"
                    + "\nVerifique que el nombre del HOST  o la IP_SERVER sea correcta"
                    + "\nVerifique el numero de puerto sea correcto. "
                    + "\nVerifique que el servicio de MySQL este en Ejecucion");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("Se produjo un error de tipo SQLException");
            System.err.println("Menssage: " + ex.getMessage());
            System.err.println("Error Code: " + ex.getErrorCode());
            System.err.println("SQLStage: " + ex.getSQLState());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Se produjo un error al intentar establecer conexion con la base de Datos.");
            ex.printStackTrace();
        }
    }
    
    public Connection getConexion() {
        return conexion;
    }
    
}
