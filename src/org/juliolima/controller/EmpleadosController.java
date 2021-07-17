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
import javax.swing.JOptionPane;
import org.juliolima.bean.Empleados;
import org.juliolima.bean.TipoEmpleado;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;

/**
 *
 * @author julio
 */
public class EmpleadosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<TipoEmpleado> listaTipoEmpleado;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNumeroEmpleado;
    @FXML private TextField txtNombresEmpleado;
    @FXML private TextField txtApellidosEmpleado;
    @FXML private TextField txtDireccionEmpleado;
    @FXML private TextField txtTelefonoContacto;
    @FXML private TextField txtGradoCocinero;
    @FXML private ComboBox cmbCodigoTipoEmpleado;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNumeroEmpleado;
    @FXML private TableColumn colNombresEmpleado;
    @FXML private TableColumn colApellidosEmpleado;
    @FXML private TableColumn colDireccionEmpleado;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colGradoCocinero;
    @FXML private TableColumn colCodigoTipoEmpleado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
       cmbCodigoTipoEmpleado.setItems(getTipoEmpleado());
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("numeroEmpleado"));
        colApellidosEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colDireccionEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccionEmpleado"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Empleados, String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleados, String>("gradoCocinero"));
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoTipoEmpleado"));
    
    }
    
    public void seleccionarElemento(){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null){
               txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado())); 
               txtNumeroEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNumeroEmpleado())); 
               txtApellidosEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
               txtNombresEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
               txtDireccionEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado());
               txtTelefonoContacto.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto());
               txtGradoCocinero.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero());
               cmbCodigoTipoEmpleado.getSelectionModel().select(buscarTipoEmpleado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
 
        }  
        
        
        
        
    }
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(false);
        txtApellidosEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(false);
        txtDireccionEmpleado.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        txtGradoCocinero.setEditable(false);
        

    }
    
    public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtNombresEmpleado.setEditable(true);
        txtDireccionEmpleado.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        txtGradoCocinero.setEditable(true);
      
    }
    
    public void limpiarControles(){
        txtCodigoEmpleado.setText("");
        txtNumeroEmpleado.setText("");
        txtApellidosEmpleado.setText("");
        txtNombresEmpleado.setText("");
        txtDireccionEmpleado.setText("");
        txtTelefonoContacto.setText("");
        txtGradoCocinero.setText("");
        
        cmbCodigoTipoEmpleado.getSelectionModel().clearSelection();
    }
        
    
    
    public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados (resultado.getInt("codigoEmpleado"),
                                         resultado.getInt("numeroEmpleado"),
                                         resultado.getString("apellidosEmpleado"),
                                         resultado.getString("nombresEmpleado"),
                                         resultado.getString("direccionEmpleado"),
                                         resultado.getString("telefonoContacto"),
                                         resultado.getString("gradoCocinero"),
                                         resultado.getInt("codigoTipoEmpleado")
                
                
                
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
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
    
    
    public TipoEmpleado buscarTipoEmpleado(int codigoTipoEmpleado){
        TipoEmpleado resultado = null;
        try{
            PreparedStatement procedimiento= Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoEmpleado(?)}");
            procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
                while(registro.next()){
                resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"),
                                             registro.getString("descripcion"));
                }
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return resultado;
    }
    
    
        public void guardar(){
        Empleados registro = new Empleados();
        //registro.setCodigoEmpresa(Integer.parseInt(txtCodigoEmpresa.getText()));
        registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
        registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
        registro.setNombresEmpleado(txtNombresEmpleado.getText());
        registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
        registro.setGradoCocinero(txtGradoCocinero.getText());
        registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setString(3, registro.getNombresEmpleado());
            procedimiento.setString(4, registro.getDireccionEmpleado());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setString(6, registro.getGradoCocinero());
            procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
            

            procedimiento.execute();
            listaEmpleados.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
        
        public void Actualizar(){
        
        
   try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleados(?,?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            
            registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
            registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
            registro.setNombresEmpleado(txtNombresEmpleado.getText());
            registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
            registro.setTelefonoContacto(txtTelefonoContacto.getText());
            registro.setGradoCocinero(txtGradoCocinero.getText());
            registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
        
            
            
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setInt(2, registro.getNumeroEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setString(4, registro.getNombresEmpleado());
            procedimiento.setString(5, registro.getDireccionEmpleado());
            procedimiento.setString(6, registro.getTelefonoContacto());
            procedimiento.setString(7, registro.getGradoCocinero());
            procedimiento.setInt(8, registro.getCodigoTipoEmpleado());
            procedimiento.execute();
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
                if(txtNumeroEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Numero del Empleado ");   
                
            }else if(txtApellidosEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Apellido del Empleado");
            
            }else if(txtNombresEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre del Empleado");
             
            }else if(txtDireccionEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar la Direccion del Empleado");
                 
                
            }else if(txtTelefonoContacto.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Telefono Contacto ");
                
            }else if(txtGradoCocinero.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese el grado cocinero, si no tiene coloque -Ninguno- cómo grado");
                
            }else if(cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()== null){
                JOptionPane.showMessageDialog(null, "Debe elegir el tipo Empleado");
                
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
                if(txtNumeroEmpleado.getText() == ""){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Numero del Empleado ");   
                
            }else if(txtApellidosEmpleado.getText()== null){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Apellido del Empleado");
            
            }else if(txtNombresEmpleado.getText()== null){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre del Empleado");
             
            }else if(txtDireccionEmpleado.getText()== null){
                JOptionPane.showMessageDialog(null, "Debe ingresar la Direccion del Empleado");
                 
                
            }else if(txtTelefonoContacto.getText() == null){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Telefono Contacto ");
                
            }else if(txtGradoCocinero.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese el grado cocinero, si no tiene coloque -Ninguno- cómo grado");
                
            }else if(cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()== null){
                JOptionPane.showMessageDialog(null, "Debe elegir el tipo Empleado");
                
            } 
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿estás seguro?", "Eliminar",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null){
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
            if(txtNumeroEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Numero del Empleado ");   
                
            }else if(txtApellidosEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Apellido del Empleado");
            
            }else if(txtNombresEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre del Empleado");
             
            }else if(txtDireccionEmpleado.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar la Direccion del Empleado");
                 
                
            }else if(txtTelefonoContacto.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar el Telefono Contacto ");
                
            }else if(txtGradoCocinero.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese el grado cocinero, si no tiene coloque -Ninguno- cómo grado");
                
            }else if(cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()== null){
                JOptionPane.showMessageDialog(null, "Debe elegir el tipo Empleado");
                
            }               
            else{
                
                Actualizar();
                desactivarControles();
                limpiarControles();
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
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
             tblEmpleados.getSelectionModel().clearSelection();        
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
    
    
    
    
    
    
    
    
    
    
    
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaEmpleados(){
        escenarioPrincipal.ventanaEmpleados();
    }
            public void menuPrincipal(){
            escenarioPrincipal.menuPrincipal();
    }
    
    
    
    public void ventanaTipoEmpleado(){
        escenarioPrincipal.ventanaTipoEmpleado();
}
    
}
