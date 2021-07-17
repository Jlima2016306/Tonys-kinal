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
import org.juliolima.bean.Plato;
import org.juliolima.bean.ServicioHasPlato;
import org.juliolima.bean.Servicios;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;

/**
 *
 * @author julio
 */
public class ServicioHasPlatoController implements Initializable{
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
    private ObservableList <ServicioHasPlato>  listaServicioHasPlato;
    private ObservableList <Servicios> listaServicio;
    private ObservableList <Plato> listaPlato;
    @FXML private ComboBox cmbCodigoServicios;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private TableView tblServiciosHasPlatos;
    @FXML private TableColumn colCodigoServicios;
    @FXML private TableColumn colCodigoPlato;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoServicios.setItems(getServicio());
        cmbCodigoPlato.setItems(getPlato());
    }
    
    public void cargarDatos(){
        tblServiciosHasPlatos.setItems(getServicioHasPlato());
        colCodigoServicios.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer> ("codigoServicio"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer>("codigoPlato"));
    }

    public ObservableList<Servicios> getServicio(){
        ArrayList<Servicios> lista = new ArrayList<Servicios>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios(resultado.getInt("codigoServicio"),
                                       resultado.getDate("fechaServivio"),
                                       resultado.getString("tipoServicio"),
                                       resultado.getString("horaServicio"),
                                       resultado.getString("lugarServicio"),
                                       resultado.getString("telefonoContacto"),
                                       resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicio = FXCollections.observableArrayList(lista);
    }
        
    
    
    public Servicios buscarServicio(int codigoServicio){
        Servicios resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicios(?)}");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Servicios(registro.getInt("codigoServicio"),
                              registro.getDate("fechaServivio"),
                              registro.getString("tipoServicio"),
                              registro.getString("horaServicio"),
                              registro.getString("lugarServicio"),
                              registro.getString("telefonoContacto"),
                              registro.getInt("codigoEmpresa"));
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
    
    
    
    public ObservableList <ServicioHasPlato> getServicioHasPlato(){
        ArrayList<ServicioHasPlato> lista = new ArrayList<ServicioHasPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios_has_Platos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ServicioHasPlato(resultado.getInt("codigoServicio"),
                                                resultado.getInt("codigoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicioHasPlato = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
                if (tblServiciosHasPlatos.getSelectionModel().getSelectedItem() != null){
        cmbCodigoServicios.getSelectionModel().select(buscarServicio(((ServicioHasPlato)tblServiciosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlato(((ServicioHasPlato)tblServiciosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
    }
    }
}
