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
import org.juliolima.bean.TipoEmpleado;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;

/**
 *
 * @author julio
 */
public class TipoEmpleadoController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO,GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoEmpleado> listaTipoEmpleado;
    
    @FXML private TextField txtCodigoTipoEmpleado;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTipoEmpleados;
    @FXML private TableColumn colCodigoTipoEmpleado;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }

        public void cargarDatos(){
                   tblTipoEmpleados.setItems(getTipoEmpleado());
                   colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, Integer>("codigoTipoEmpleado"));
                   colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, String>("Descripcion"));

        
        }
        
            public void seleccionarElemento(){
                
               if (tblTipoEmpleados.getSelectionModel().getSelectedItem() != null){
        
                

        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
        
        
        txtCodigoTipoEmpleado.setText(String.valueOf(((TipoEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
        txtDescripcion.setText(((TipoEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem()).getDescripcion());
               }
    }
        
            public ObservableList<TipoEmpleado> getTipoEmpleado(){
        ArrayList<TipoEmpleado> lista = new ArrayList<TipoEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoEmpleado}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoEmpleado(resultado.getInt("codigoTipoEmpleado"),
                                      resultado.getString("descripcion")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
      
        return listaTipoEmpleado = FXCollections.observableArrayList(lista);
    }
    
            
            
    
    public Principal getEcenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEcenarioPrincipal(Principal ecenarioPrincipal) {
        this.escenarioPrincipal = ecenarioPrincipal;
    }
    
        public void desactivarControles(){
        txtCodigoTipoEmpleado.setEditable(false);
        txtDescripcion.setEditable(false);

        
    }
    
    public void activarControles(){
        txtCodigoTipoEmpleado.setEditable(false);
        txtDescripcion.setEditable(true);


    }
    
    public void limpiarControles(){
        txtCodigoTipoEmpleado.setText("");
        txtDescripcion.setText("");

        
    }
    
    
        public void guardar(){
        TipoEmpleado registro = new TipoEmpleado();
        //registro.setCodigoEmpresa(Integer.parseInt(txtCodigoEmpresa.getText()));
        registro.setDescripcion(txtDescripcion.getText());

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoEmpleado(?)}");
            procedimiento.setString(1, registro.getDescripcion());

            procedimiento.execute();
            listaTipoEmpleado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
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
                        if(txtDescripcion.getText().equals("") ){
                JOptionPane.showMessageDialog(null, "Debe ingresar la Descripcion");   
                
            }else{    
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
    }
    
        public void eliminar(){
        
        
        
        
        switch (tipoDeOperacion){
            case GUARDAR:
                tblTipoEmpleados.getSelectionModel().clearSelection();        
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
                if (tblTipoEmpleados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿estás seguro?", "Eliminar",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
                            procedimiento.setInt(1, ((TipoEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
                            procedimiento.execute();
                            listaTipoEmpleado.remove(tblTipoEmpleados.getSelectionModel().getSelectedIndex());
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
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoEmpleado(?,?)}");
            
            TipoEmpleado registro = (TipoEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem();
            
            registro.setDescripcion(txtDescripcion.getText());

            
            procedimiento.setInt(1, registro.getCodigoTipoEmpleado());
            procedimiento.setString(2, registro.getDescripcion());

            procedimiento.execute();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }    
    
        public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblTipoEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
            if(txtDescripcion.getText().equals("") ){
                JOptionPane.showMessageDialog(null, "Debe ingresar la Descripcion");   
                
            }else{                
                actualizar();
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
    }
        
        public void Reporte() {
    switch(tipoDeOperacion){
        case ACTUALIZAR:
             tblTipoEmpleados.getSelectionModel().clearSelection();        
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
    }
        }       
    
        public void menuPrincipal(){
            escenarioPrincipal.menuPrincipal();
    }
    
        public void ventanaEmpleados(){
        escenarioPrincipal.ventanaEmpleados();
    }
    
    
}
