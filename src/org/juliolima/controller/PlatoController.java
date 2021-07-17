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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.juliolima.bean.Plato;
import org.juliolima.bean.TipoPlato;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;


public class PlatoController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO,GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoPlato> listaTipoPlato;
    private ObservableList<Plato> listaPlato;
    
    @FXML private TextField txtCodigoPlato;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtNombrePlato;
    @FXML private TextField txtDescripcionPlato;
    @FXML private TextField txtPrecioPlato;

    @FXML private ComboBox cmbCodigoTipoPlato;
    
    @FXML private TableView tblPlatos;
    
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colNombrePlato;
    @FXML private TableColumn colDescripcionPlato;
    @FXML private TableColumn colPrecioPlato;
    @FXML private TableColumn colCodigoTipoPlatos;

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
            cmbCodigoTipoPlato.setItems(getTipoPlato());
    }
    
    
    public void cargarDatos(){
        tblPlatos.setItems(getPlatos());
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoPlato"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("cantidad"));
        colNombrePlato.setCellValueFactory(new PropertyValueFactory<Plato, String>("nombrePlato"));
        colDescripcionPlato.setCellValueFactory(new PropertyValueFactory<Plato, String>("descripcionPlato"));  
        colPrecioPlato.setCellValueFactory(new PropertyValueFactory<Plato, Double>("precioPlato"));
        colCodigoTipoPlatos.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoTipoPlato"));

    
    
    }
    
    public void seleccionarElemento(){
        
                if (tblPlatos.getSelectionModel().getSelectedItem() != null){

        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        
        txtCodigoPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        txtCantidad.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad()));
        txtNombrePlato.setText(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato());
        txtDescripcionPlato.setText(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato());
        txtPrecioPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato()));
        cmbCodigoTipoPlato.getSelectionModel().select(buscarTipoPlato(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
    }
    }    
    
    
    public ObservableList<TipoPlato> getTipoPlato(){
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoPlato}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoPlato(
                                resultado.getInt("codigoTipoPlato"),
                                resultado.getString("DescripcionTipo")));
                
            }    
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoPlato = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    
    public ObservableList<Plato> getPlatos(){
        ArrayList<Plato> lista= new ArrayList<Plato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlatos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Plato ( resultado.getInt("codigoPlato"),
                                            resultado.getInt("cantidad"),
                                            resultado.getString("nombrePlato"),
                                            resultado.getString("descripcionPlato"),
                                            resultado.getDouble("precioPlato"),
                                            resultado.getInt("codigoTipoPlato")
                                            ));
            }
                
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
            
        
        
        
        return listaPlato= FXCollections.observableArrayList(lista);
    }
    
    
    
        public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }

    
    
    public void desactivarControles(){
        txtCodigoPlato.setEditable(false);
        txtCantidad.setEditable(false);
        txtNombrePlato.setEditable(false);
        txtDescripcionPlato.setEditable(false);
        txtPrecioPlato.setEditable(false);
        cmbCodigoTipoPlato.setDisable(false);
    }
    
    public void activarControles(){
        txtCodigoPlato.setEditable(false);
        txtCantidad.setEditable(true);
        txtNombrePlato.setEditable(true);
        txtDescripcionPlato.setEditable(true);
        txtPrecioPlato.setEditable(true);
        cmbCodigoTipoPlato.setDisable(false);    
    }
    
    public void limpiarControles(){
        txtCodigoPlato.setText("");
        txtCantidad.setText("");
        txtNombrePlato.setText("");
        txtDescripcionPlato.setText("");
        txtPrecioPlato.setText("");
        cmbCodigoTipoPlato.getSelectionModel().clearSelection();  
    }
    
 public TipoPlato buscarTipoPlato(int codigoTipoPlato){
     TipoPlato resultado = null;
     try{
     PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarTipoPlato(?)");
     procedimiento.setInt(1, codigoTipoPlato);
     ResultSet registro = procedimiento.executeQuery();
     while(registro.next()){
         resultado = new TipoPlato(registro.getInt("codigoTipoPlato"),
                                registro.getString("DescripcionTipo"));
     }
         
     }catch(Exception e){
         e.printStackTrace();
     }
     
     
     return resultado;
 }
    
 private int DatosCorrectos =0;
 
    public void guardar(){
        
        
        
        
        
        
        Plato registro = new Plato();
        
        if((txtCantidad.getText()== null)&&(txtNombrePlato.getText()== null)&&(txtDescripcionPlato.getText()==null)&&(txtPrecioPlato.getText()==null)){
        DatosCorrectos=1;
        }
        
        switch(DatosCorrectos){
            
        case 1:
                        JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios, exptuando el código Platos que es automatico");

        case 2:
            
        case 0:
        
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNombrePlato(txtNombrePlato.getText());
        registro.setDescripcionPlato(txtDescripcionPlato.getText());
        registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));
        registro.setCodigoTipoPlato(((TipoPlato)cmbCodigoTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPlatos(?,?,?,?,?)}");
            procedimiento.setInt(1,registro.getCantidad());
            procedimiento.setString(2, registro.getNombrePlato());
            procedimiento.setString(3, registro.getDescripcionPlato());
            procedimiento.setDouble(4, registro.getPrecioPlato());
            procedimiento.setInt(5, registro.getCodigoTipoPlato());

            procedimiento.executeQuery();
            DatosCorrectos =0;

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    }   
 
        public void Actualizar(){
        Plato registro = new Plato();
        
        
                if((txtCantidad.getText()== null)&&(txtNombrePlato.getText()== null)&&(txtDescripcionPlato.getText()==null)&&(txtPrecioPlato.getText()==null)){
        DatosCorrectos=1;
        }
        
        switch(DatosCorrectos){
            
        case 1:
                        JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios, exptuando el código Platos que es automatico");
            break;
        case 2:
            break;
        case 0:
        registro.setCodigoPlato(Integer.parseInt(txtCodigoPlato.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNombrePlato(txtNombrePlato.getText());
        registro.setDescripcionPlato(txtNombrePlato.getText());
        registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));
        registro.setCodigoTipoPlato(((TipoPlato)cmbCodigoTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPlatos(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoPlato());
            procedimiento.setInt(2,registro.getCantidad());
            procedimiento.setString(3, registro.getNombrePlato());
            procedimiento.setString(4, registro.getDescripcionPlato());
            procedimiento.setDouble(5, registro.getPrecioPlato());
            procedimiento.setInt(6, registro.getCodigoTipoPlato());

            procedimiento.executeQuery();
            DatosCorrectos = 0; 
                break;
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }   
    
    
 
 
     public void nuevo (){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setDisable(false);
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                                break;

            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
            
                
        }
    }
    
     
         public void eliminar(){
        
        
        
        
        switch (tipoDeOperacion){
            case GUARDAR:
                tblPlatos.getSelectionModel().clearSelection();        
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblPlatos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿estás seguro?", "Eliminar",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPlatos(?)}");
                            procedimiento.setInt(1, ((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
                            procedimiento.execute();
                            listaPlato.remove(tblPlatos.getSelectionModel().getSelectedIndex());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                
        }else{
            JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento");
        }
        
    }
    
    }
         
         
        public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblPlatos.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnReporte.setDisable(false);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                Actualizar();
                desactivarControles();
                limpiarControles();
                btnEditar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                
                break;
                
        }
    }
        
        public void Rerporte() {
    switch(tipoDeOperacion){
        case ACTUALIZAR:
             tblPlatos.getSelectionModel().clearSelection();        
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
    
        public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
    }
    
    

    
}


    
