

package org.in5bv.malcantaralvillalta.reports;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign; 
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.in5bv.malcantaralvillalta.db.Conexion;
import net.sf.jasperreports.view.JasperViewer;
 
        

/**
 *
 * @author Lionar Gerardy Villalta Barrientos.
 * @date 26 jun. 2022
 * @time 18:22:38
 * Codigo: IN5BV
 */

public class GenerarReporte {
    private static GenerarReporte instance;
   

    private JasperViewer jasperViewer; 
    
    private GenerarReporte(){
         
    }
    public static GenerarReporte getInstance(){
        if (instance == null) {
            instance = new GenerarReporte();
        }return instance;
    }
    
    public void mostrarReporte(String nombreReporte, Map<String, Object> parametros, String titulo){
        
        try {
            URL urlFile = new URL(getClass().getResource(nombreReporte).toString());
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(urlFile);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, Conexion.getInstance().getConexion());
            
            jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle(titulo);
            jasperViewer.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
   
}


