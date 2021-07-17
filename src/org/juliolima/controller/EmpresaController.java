
package org.juliolima.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.juliolima.bean.Empresa;
import org.juliolima.db.Conexion;
import org.juliolima.report.GenerarReporte;
import org.juliolima.system.Principal;

public class EmpresaController implements Initializable {
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR,EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private enum tipoOpcion {CERO, UNO, DOS};
    private tipoOpcion  DatosCorrectos =  tipoOpcion.CERO;
    private ObservableList<Empresa> listaEmpresa;
    
    @FXML private TextField txtCodigoEmpresa;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccionEmpresa;
    @FXML private TextField txtTelefono;
    @FXML private TableView tblEmpresas;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colNombreEmpresa;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         cargarDatos();
         btnReporte.setDisable(false);

    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
    
    public void ventanaPresupuestos(){
        escenarioPrincipal.ventanaPresupuestos();
    }
    
    public void cargarDatos(){
       tblEmpresas.setItems(getEmpresa());
       colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("codigoEmpresa"));
       colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombreEmpresa"));
       colDireccion.setCellValueFactory(new PropertyValueFactory<Empresa, String>("direccion"));
       colTelefono.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono"));
    }
    
    public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),
                                      resultado.getString("nombreEmpresa"),
                                      resultado.getString("direccion"),
                                      resultado.getString("telefono")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
      
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
    
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
    public void desactivarControles(){
        txtCodigoEmpresa.setEditable(false);
        txtNombreEmpresa.setEditable(false);
        txtDireccionEmpresa.setEditable(false);
        txtTelefono.setEditable(false);
        
    }
    
    public void activarControles(){
        txtCodigoEmpresa.setEditable(false);
        txtNombreEmpresa.setEditable(true);
        txtDireccionEmpresa.setEditable(true);
        txtTelefono.setEditable(true);
    

    }
    
    public void limpiarControles(){
        txtCodigoEmpresa.setText("");
        txtNombreEmpresa.setText("");
        txtDireccionEmpresa.setText("");
        txtTelefono.setText("");
        
    }
    
    public void guardar(){
        Empresa registro = new Empresa();
         DatosCorrectos =tipoOpcion.CERO;
        //registro.setCodigoEmpresa(Integer.parseInt(txtCodigoEmpresa.getText()));
        registro.setNombreEmpresa(txtNombreEmpresa.getText());
        registro.setDireccion(txtDireccionEmpresa.getText());
        registro.setTelefono(txtTelefono.getText());
        
        
        if ((txtNombreEmpresa.getText().length() > 0 )&&(txtDireccionEmpresa.getText().length() > 0)&&(txtTelefono.getText().length() > 0) ){

            DatosCorrectos =tipoOpcion.UNO;
        }
        
        if((registro.getTelefono().matches("[0-9]+") == false)   ){ 
           DatosCorrectos =tipoOpcion.DOS;
        }
        
    switch(DatosCorrectos){
        case CERO:
            JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios");
                        
            break;
        case DOS:
            
            JOptionPane.showMessageDialog(null, "Solo pueden haber numeros en telefono");
                        
           
            break; 
            
            
        case UNO:        
        
          
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpresas(?,?,?)}");
            procedimiento.setString(1, registro.getNombreEmpresa());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getTelefono());
            procedimiento.execute();
            listaEmpresa.add(registro);
            
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
        
    }
    
    public void seleccionarElemento(){
        
                btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        if (tblEmpresas.getSelectionModel().getSelectedItem() != null){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
        
        
        txtCodigoEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        txtNombreEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa());
        txtDireccionEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccion());
        txtTelefono.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefono());
        }
    }
    
    public Empresa buscarEmpresa(int codigoEmpresa){
        Empresa resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call_spBucarEmpresas(?)}");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Empresa(
                                registro.getInt("codigoEmpresa"),
                                registro.getString("nombreEmpresa"),
                                registro.getString("direccion"),
                                registro.getString("telefono")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    

    
    public void eliminar(){
        
        
        
        
        switch (tipoDeOperacion){
            case GUARDAR:
                tblEmpresas.getSelectionModel().clearSelection();        
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("NUEVO");
                btnNuevo.setDisable(false);
                btnEliminar.setText("ELIMINAR");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblEmpresas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿estás seguro?", "Eliminar",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpresas(?)}");
                            procedimiento.setInt(1, ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
                            procedimiento.execute();
                            listaEmpresa.remove(tblEmpresas.getSelectionModel().getSelectedIndex());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                
        }else{
            JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento");
        }
        
    }
    
    }

        public void actualizar(){
            Empresa registro = (Empresa)tblEmpresas.getSelectionModel().getSelectedItem();
            DatosCorrectos =tipoOpcion.CERO;
            registro.setNombreEmpresa(txtNombreEmpresa.getText());
            registro.setDireccion(txtDireccionEmpresa.getText());
            registro.setTelefono(txtTelefono.getText());
            
            
            if ((txtNombreEmpresa.getText().length() > 0 )&&(txtDireccionEmpresa.getText().length() > 0)&&(txtTelefono.getText().length() > 0) ){

                DatosCorrectos =tipoOpcion.UNO;
            }
        
            if((registro.getTelefono().matches("[0-9]+") == false)  ){ 
                DatosCorrectos =tipoOpcion.DOS;
            }
        
        switch(DatosCorrectos){
        case CERO:
            JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios, exptuando el código Empresa que es automatico");
                        
            break;
        case DOS:
            
            JOptionPane.showMessageDialog(null, "Solo pueden haber numeros en telefono");
                        
           
            break; 
            
            
        case UNO:            
            
            
            
            
            
            
            
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpresas(?,?,?,?)}");
            

    
            
            
            procedimiento.setInt(1, registro.getCodigoEmpresa());
            procedimiento.setString(2, registro.getNombreEmpresa());
            procedimiento.setString(3, registro.getDireccion());
            procedimiento.setString(4, registro.getTelefono());
            procedimiento.execute();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }

    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblEmpresas.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("ACTUALIZAR");
                    btnReporte.setText("CANCELAR");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditar.setDisable(false);
                btnEditar.setText("EDITAR");
                btnReporte.setText("REPORTE");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                
                break;
                
        }
    }
    

    public void nuevo (){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setDisable(false);
                btnEliminar.setText("CANCELAR");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                                break;

            case GUARDAR:
     
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("NUEVO");
                btnEliminar.setText("ELIMINAR");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                
                break;
            
                
        }
    }
    
    
    public void Rerporte() {
    switch(tipoDeOperacion){
        case ACTUALIZAR:
             tblEmpresas.getSelectionModel().clearSelection();        
             desactivarControles();
             limpiarControles();
             btnEditar.setText("EDITAR");
             btnEditar.setDisable(false);
             btnReporte.setText("REPORTE");
             btnEliminar.setDisable(true);
             btnReporte.setDisable(true);
             btnEditar.setDisable(true);
             btnNuevo.setDisable(false);
             
             tipoDeOperacion = operaciones.NINGUNO;
             break;        
        
        
        case NINGUNO:
            imprimirReporte();
            
            break;
    }
}
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoEmpresa", null);
        GenerarReporte.mostrarReporte("ReporteEmpresa.jasper", "Reporte de Empresa"
                + "", parametros);
    }
    
    
}
