/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juliolima.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import org.juliolima.bean.TipoPlato;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;

public class TipoPlatoController implements Initializable {
    private Principal escenarioPrincipal;
        private enum operaciones {NUEVO,GUARDAR,ELIMINAR,EDITAR, ACTUALIZAR,NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoPlato> listaTipoPlato;
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML TextField txtCodigoTipoPlato;
    @FXML TextField txtDescripcionTipo;
    @FXML TableView tblTipoPlatos;
    @FXML TableColumn colCodigoTipoPlato;
    @FXML TableColumn colDescripcionTipo;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

          public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }  

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
       tblTipoPlatos.setItems(getEmpresa());
       colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato, Integer>("codigoTipoPlato"));
       colDescripcionTipo.setCellValueFactory(new PropertyValueFactory<TipoPlato, String>("DescripcionTipo"));
    }
    
    public ObservableList<TipoPlato> getEmpresa(){
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoPlato}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoPlato(resultado.getInt("codigoTipoPlato"),
                                      resultado.getString("descripcionTipo")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
      
        return listaTipoPlato= FXCollections.observableArrayList(lista);
    }
    
    
    

  
    
    public void desactivarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipo.setEditable(false);
    
        
    }
    
    public void activarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipo.setEditable(true);
    

    }
    
    public void limpiarControles(){
        txtCodigoTipoPlato.setText("");
        txtDescripcionTipo.setText("");
    
        
    }
    private int DatosCorrectos=0;
    public void guardar(){
        TipoPlato registro = new TipoPlato();
         DatosCorrectos =0;
        //registro.setCodigoEmpresa(Integer.parseInt(txtCodigoEmpresa.getText()));
        registro.setDescripcionTipo(txtDescripcionTipo.getText());
        
    
        
        
        if ((txtDescripcionTipo.getText().length() > 0 ) ){

            DatosCorrectos =1;
        }
        
       
        
    switch(DatosCorrectos){
        case 0:
            JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios");
                        
            break;
                        
           
            
            
            
        case 1:        
        
          
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoPlato(?)}");
            procedimiento.setString(1, registro.getDescripcionTipo());
         
            procedimiento.execute();
            listaTipoPlato.add(registro);
            DatosCorrectos=0;

           
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
        
    }
    
    public void seleccionarElemento(){
        if (tblTipoPlatos.getSelectionModel().getSelectedItem() != null){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
        
        
        txtCodigoTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
        txtDescripcionTipo.setText(((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getDescripcionTipo());
        
        }
    }
    
    public TipoPlato buscarTipoPlato(int codigoTipoPlato){
        TipoPlato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoPlato(?)}");
            procedimiento.setInt(1, codigoTipoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new TipoPlato(
                                registro.getInt("codigoTipoPlato"),
                                registro.getString("DescripcionTipo")
                               
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
                tblTipoPlatos.getSelectionModel().clearSelection();        
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
                if (tblTipoPlatos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿estás seguro?", "Eliminar",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoPlato(?)}");
                            procedimiento.setInt(1, ((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
                            procedimiento.execute();
                            listaTipoPlato.remove(tblTipoPlatos.getSelectionModel().getSelectedIndex());
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
            TipoPlato registro = (TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem();
            DatosCorrectos =0;
            registro.setDescripcionTipo(txtDescripcionTipo.getText());
            
            
            
            if ((txtDescripcionTipo.getText().length() > 0 ) ){

                DatosCorrectos =1;
            }
        
      
        switch(DatosCorrectos){
        case 0:
            JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios, exptuando el código TipoPlato que es automatico");
                        
            break;
        
        case 1:            
            
            
            
            
            
            
            
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoPlato(?,?)}");
            

    
            
            
            procedimiento.setInt(1, registro.getCodigoTipoPlato());
            procedimiento.setString(2, registro.getDescripcionTipo());
  
            procedimiento.execute();
                            DatosCorrectos =0;

        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }

    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblTipoPlatos.getSelectionModel().getSelectedItem() != null){
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
             tblTipoPlatos.getSelectionModel().clearSelection();        
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
            //imprimirReporte();
            
            break;
    }
}
    
         public void ventanaPlato(){
        escenarioPrincipal.VentanaPlato();
    }   
    
    

    
}
