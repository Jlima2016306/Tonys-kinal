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
import org.juliolima.bean.Productos;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;

/**
 *
 * @author julio
 */
public class ProductosController implements Initializable{



    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,EDITAR, ACTUALIZAR,NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Productos> listaProductos;
    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colNombreProducto;
    @FXML private TableColumn colCantidad;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        btnReporte.setDisable(false);
    }
    
    public void cargarDatos(){
        tblProductos.setItems(getProductos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("CodigoProducto") );
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("NombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidad"));
    }
        private int DatosCorrectos = 1;

    public ObservableList<Productos> getProductos(){
        ArrayList<Productos> Lista= new ArrayList<Productos>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next())
                Lista.add(new Productos(resultado.getInt("codigoProducto"),
                                      resultado.getString("nombreProducto"),
                                      resultado.getInt("Cantidad")));
                
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return listaProductos= FXCollections.observableArrayList(Lista);
   
    }
    
    
    public void desactivarControles(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(false);
        txtCantidad.setEditable(false);
        
    }
    
    public void activarControles(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(true);
        txtCantidad.setEditable(true);
        
    

    }
    
    public void limpiarControles(){
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCantidad.setText("");
   
        
    }
    
    public void guardar(){
        Productos registro = new Productos();
         DatosCorrectos =0;
        //registro.setCodigoEmpresa(Integer.parseInt(txtCodigoEmpresa.getText()));
        registro.setNombreProducto(txtNombreProducto.getText());
        
        
        
        if ((txtNombreProducto.getText().length() > 0 )&&(txtCantidad.getText().length() > 0) ){

            DatosCorrectos =1;
            
            
        }
         if((txtCantidad.getText().matches("[0-9]+") == false)  ){ 
                DatosCorrectos =2;
            }
        
           
        
    switch(DatosCorrectos){
        case 0:
            JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios, exceptuando el Código Producto que se crea automaticamente");
                        
            break;
        case 2:
            
            JOptionPane.showMessageDialog(null, "Solo pueden haber numeros en Cantidad");
                        
           
            break; 
            
            
        case 1:        
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
          
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos(?,?)}");
            procedimiento.setString(1, registro.getNombreProducto());
            procedimiento.setInt(2, registro.getCantidad());
    
            procedimiento.execute();
            listaProductos.add(registro);
            DatosCorrectos =0;
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
        
    }
    
    public void seleccionarElemento(){
        if (tblProductos.getSelectionModel().getSelectedItem() != null){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
        
        
        txtCodigoProducto.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtNombreProducto.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto());
        txtCantidad.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCantidad()));
      
        }
    }
    
    public Productos buscarProductos(int codigoProductos){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call_spBucarProductos(?)}");
            procedimiento.setInt(1, codigoProductos);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Productos(
                                registro.getInt("codigoProducto"),
                                registro.getString("nombreProducto"),
                                registro.getInt("cantidad")
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
                tblProductos.getSelectionModel().clearSelection();        
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿estás seguro?", "Eliminar",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProductos(?)}");
                            procedimiento.setInt(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedIndex());
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
            
            
            Productos registro = (Productos)tblProductos.getSelectionModel().getSelectedItem();
            DatosCorrectos = 0;
            registro.setNombreProducto(txtNombreProducto.getText());
            
            
            
            if ((txtNombreProducto.getText() == null )&&(txtCantidad.getText()== null)){

                DatosCorrectos =1;
            }
        
            if( txtCantidad.getText().matches("[0-9]+") == false) { 
                DatosCorrectos =2;
            }
        
        switch(DatosCorrectos){
        case 1:
            JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios, exceptuando el código Productos que es automatico");
                        
            break;
        case 2:
            
            JOptionPane.showMessageDialog(null, "Solo pueden haber numeros en la Cantidad");
                        
           
            break; 
            
            
        case 0:            
            
            
            
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            
            
            
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProductos(?,?,?)}");
            

    
            procedimiento.setInt(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getNombreProducto());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.execute();
            
            DatosCorrectos = 0;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }

    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null){
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
             tblProductos.getSelectionModel().clearSelection();        
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
            
            
            break;
    }
}
    
    
      public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }  
    
    
    
}
