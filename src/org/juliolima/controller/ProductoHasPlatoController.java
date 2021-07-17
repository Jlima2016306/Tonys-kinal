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
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.juliolima.bean.Productos;
import org.juliolima.bean.Plato;
import org.juliolima.bean.ProductoHasPlato;
import org.juliolima.bean.ServicioHasPlato;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;

public class ProductoHasPlatoController implements Initializable{
    private enum operaciones{GUARDAR,NINGUNO,ELMINAR}
    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <ProductoHasPlato>  listaProductosHasPlato;
    private ObservableList <Productos > listaProductos;
    private ObservableList <Plato> listaPlato;
    @FXML private ComboBox cmbCodigoProductos;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private TableView tblProductosHasPlatos;
    @FXML private TableColumn colCodigoProductos;
    @FXML private TableColumn colCodigoPlato;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoProductos.setItems(getProductos());
        cmbCodigoPlato.setItems(getPlato());
    }
    
    public void cargarDatos(){
        tblProductosHasPlatos.setItems(getProductosHasPlato());
        colCodigoProductos.setCellValueFactory(new PropertyValueFactory<ProductoHasPlato, Integer> ("codigoProducto"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ProductoHasPlato, Integer>("codigoPlato" ));
    }

    public ObservableList<Productos> getProductos(){
        ArrayList<Productos> Lista= new ArrayList<Productos>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next())
                Lista.add(new Productos(resultado.getInt("codigoProducto" ),
                                      resultado.getString( "nombreProducto"),
                                      resultado.getInt("Cantidad")));
                
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return listaProductos= FXCollections.observableArrayList(Lista);
   
    }
    
    
    public Productos buscarProductos(int codigoProducto){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Productos(registro.getInt("codigoProducto"),
                              registro.getString("nombreProducto"),
                              registro.getInt("Cantidad"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
         return resultado;
    }
    
    
    
    
        public ObservableList<Plato> getPlato(){
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
    
    public Plato buscarPlato(int codigoPlato){
        Plato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlatos(?)}");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Plato(registro.getInt("codigoPlato"),
                                         registro.getInt("cantidad"),
                                         registro.getString("nombrePlato"),
                                         registro.getString("descripcionPlato"),
                                         registro.getDouble("precioPlato"),
                                         registro.getInt("codigoTipoPlato"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
         return resultado;
    }
    
    
    
    public ObservableList <ProductoHasPlato> getProductosHasPlato(){
        ArrayList<ProductoHasPlato> lista = new ArrayList<ProductoHasPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos_has_Platos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ProductoHasPlato(resultado.getInt("codigoProducto"),
                                                resultado.getInt("codigoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductosHasPlato = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if (tblProductosHasPlatos.getSelectionModel().getSelectedItem() != null){
        
        cmbCodigoProductos.getSelectionModel().select(buscarProductos(((ProductoHasPlato)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlato(((ProductoHasPlato)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
    }
    }
}



