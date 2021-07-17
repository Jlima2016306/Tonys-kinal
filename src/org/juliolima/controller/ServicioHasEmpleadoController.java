/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juliolima.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import org.juliolima.bean.Empleados;
import org.juliolima.bean.ServicioHasEmpleado;
import org.juliolima.bean.Servicios;
import org.juliolima.db.Conexion;
import org.juliolima.system.Principal;

/**
 *
 * @author julio
 */
public class ServicioHasEmpleadoController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                cargarDatos();
        cmbCodigoServicio.setItems(getServicio());
        cmbCodigoEmpleados.setItems(getEmpleado());
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        fecha.getStylesheets().add("/org/juliolima/resourse/DatePicker.css");
        fecha.selectedDateProperty().set(null);
        grpFechaEvento.add(fecha, 0, 0);
    }
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<ServicioHasEmpleado> listaServicioHasEmpleado;
    private ObservableList<Servicios> listaServicio;
    private ObservableList<Empleados> listaEmpleado;
    private DatePicker fecha;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private ComboBox cmbCodigoEmpleados;
    @FXML private GridPane grpFechaEvento;
    @FXML private TextField txtHoraEvento;
    @FXML private TextField txtLugarEvento;
    
    @FXML private TableView tblServiciosHasEmpleados;
    
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colFechaEvento;
    @FXML private TableColumn colHoraEvento;
    @FXML private TableColumn colLugarEvento;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    

    
    public void cargarDatos(){
        tblServiciosHasEmpleados.setItems(getServicioHasEmpleado());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("codigoEmpleado"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, String>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, String>("lugarEvento"));
    }
    
    
     

    public ObservableList<ServicioHasEmpleado> getServicioHasEmpleado(){
        ArrayList<ServicioHasEmpleado> lista = new ArrayList<ServicioHasEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios_has_Empleados}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ServicioHasEmpleado(resultado.getInt("Servicios_codigoServicios"),
                                                  resultado.getInt("codigoServicio"),
                                                  resultado.getInt("codigoEmpleado"),
                                                  resultado.getDate("fechaEvento"),
                                                  resultado.getString("horaEvento"),
                                                  resultado.getString("lugarEvento")));               
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaServicioHasEmpleado = FXCollections.observableArrayList(lista);
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
    
  
    public ObservableList <Empleados> getEmpleado(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                       resultado.getInt("numeroEmpleado"),
                                       resultado.getString("apellidosEmpleado"),
                                       resultado.getString("nombresEmpleado"),
                                       resultado.getString("direccionEmpleado"),
                                       resultado.getString("telefonoContacto"),
                                       resultado.getString("gradoCocinero"),
                                       resultado.getInt("codigoTipoEmpleado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public Empleados buscarEmpleado(int codigoEmpleado){
        Empleados resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                                         registro.getInt("numeroEmpleado"),
                                         registro.getString("apellidosEmpleado"),
                                         registro.getString("nombresEmpleado"),
                                         registro.getString("direccionEmpleado"),
                                         registro.getString("telefonoContacto"),
                                         registro.getString("gradoCocinero"),
                                         registro.getInt("codigoTipoEmpleado"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
         return resultado;
    }
    
    public void seleccionarElemento(){
       if (tblServiciosHasEmpleados.getSelectionModel().getSelectedItem() != null){


        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
        
        
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServicioHasEmpleado)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoEmpleados.getSelectionModel().select(buscarEmpleado(((ServicioHasEmpleado)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        fecha.selectedDateProperty().set(((ServicioHasEmpleado)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getFechaEvento());
        txtHoraEvento.setText(String.valueOf(((ServicioHasEmpleado)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getHoraEvento()));
        txtLugarEvento.setText(String.valueOf(((ServicioHasEmpleado)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getLugarEvento()));      
    }
    
    }
        
    
    public void desactivarControles(){
        cmbCodigoServicio.setEditable(false);
        cmbCodigoEmpleados.setEditable(false);
        grpFechaEvento.setDisable(false);
        txtHoraEvento.setEditable(false);
        txtLugarEvento.setEditable(false);
    }

    public void activarControles(){
        cmbCodigoServicio.setEditable(false);
        cmbCodigoEmpleados.setEditable(false);
        grpFechaEvento.setDisable(false);
        txtHoraEvento.setEditable(true);
        txtLugarEvento.setEditable(true);
    }
    
    public void limpiarControles(){
        cmbCodigoServicio.getSelectionModel().clearSelection();
        cmbCodigoEmpleados.getSelectionModel().clearSelection();
        txtHoraEvento.setText("");
        txtLugarEvento.setText("");
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void nuevo(){
        switch (tipoDeOperacion){
         case NINGUNO:
             limpiarControles();
             activarControles();
             btnNuevo.setText("Guardar");
             btnEliminar.setDisable(false);
             btnEliminar.setText("Cancelar");
             btnEditar.setDisable(true);
             btnReporte.setDisable(true);
             tipoDeOperacion = operaciones.GUARDAR;
            break;
         case GUARDAR:
             if(cmbCodigoServicio.getSelectionModel().getSelectedItem() == null){
                JOptionPane.showMessageDialog(null, "Debe ingresar el codigo Servicio ");   
            }else if(cmbCodigoEmpleados.getSelectionModel().getSelectedItem() == null){
                JOptionPane.showMessageDialog(null, "Debe ingresar Codigo de Empleado");
            }else if(fecha.getSelectedDate()== null){
                JOptionPane.showMessageDialog(null, "Debe ingresar Fecha con formado -Día/mes/año- ");
            }else if(txtHoraEvento.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese la Hora con formato -Hora:minuto:segundo-");
            }else if(txtLugarEvento.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar un lugar un Lugar");
            }               
            else{
               try{
                    guardar();
                    desactivarControles();
                    limpiarControles();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setDisable(false);
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                    break;  
                }catch(Exception e){
                  e.printStackTrace();
                }
             }   
        }
    }
    
    public void guardar(){
        ServicioHasEmpleado registro = new ServicioHasEmpleado();
        registro.setCodigoServicio(((Servicios)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        registro.setCodigoEmpleado(((Empleados)cmbCodigoEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setFechaEvento(fecha.getSelectedDate());
        registro.setHoraEvento(txtHoraEvento.getText());
        registro.setLugarEvento(txtLugarEvento.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicios_has_Empleados(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoServicio());
            procedimiento.setInt(2, registro.getCodigoEmpleado());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(4, registro.getHoraEvento());
            procedimiento.setString(5, registro.getLugarEvento());
            procedimiento.execute();
            listaServicioHasEmpleado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
             desactivarControles();
             limpiarControles();
             btnNuevo.setText("Nuevo");
             btnNuevo.setDisable(false);
             btnEliminar.setText("Eliminar");
             btnEliminar.setDisable(false);
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             tipoDeOperacion = operaciones.NINGUNO;
            break;
            default:
            if(tblServiciosHasEmpleados.getSelectionModel().getSelectedItem() != null){
                int confirmacion = JOptionPane.showConfirmDialog(null,"Esta Seguro de eliminar","Eliminar Servicio Has Empleados",JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(confirmacion == JOptionPane.YES_OPTION){
                    try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicios_has_Empleados(?)}");
                        procedimiento.setInt(1, ((ServicioHasEmpleado)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicios());
                        procedimiento.execute();
                        listaServicioHasEmpleado.remove(tblServiciosHasEmpleados.getSelectionModel().getSelectedIndex());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione un Dato");
            }        
            break;
            
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblServiciosHasEmpleados.getSelectionModel().getSelectedItem() != null){   
                    btnEditar.setText("Actualizar");      
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();            
                    tipoDeOperacion = operaciones.ACTUALIZAR; 
                }else{
                   JOptionPane.showMessageDialog(null, "Debe seleccionar un dato");
                }
                break;
                case ACTUALIZAR:
                            if(cmbCodigoServicio.getSelectionModel().getSelectedItem() == null){
                JOptionPane.showMessageDialog(null, "Debe ingresar el codigo Servicio ");   
                
            }else if(cmbCodigoEmpleados.getSelectionModel().getSelectedItem() == null){
                JOptionPane.showMessageDialog(null, "Debe ingresar Codigo de Empleado");
                
            }else if(fecha.getSelectedDate()== null){
                JOptionPane.showMessageDialog(null, "Debe ingresar Fecha con formado -Día/mes/año- ");
                
            }else if(txtHoraEvento.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese la Hora con formato -Hora:minuto:segundo-");
                
            }else if(txtLugarEvento.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar un lugar un Lugar");
                
            }               
            else{
                    actualizar();
                    desactivarControles();
                    limpiarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false); 
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                }   
            break;
        }
    } 
    
    public void actualizar(){
        try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicios_has_Empleados(?,?,?,?,?,?)}");
             ServicioHasEmpleado registro =(ServicioHasEmpleado)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem();
             registro.setCodigoServicio(((Servicios)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
             registro.setCodigoEmpleado(((Empleados)cmbCodigoEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
             registro.setFechaEvento((fecha.getSelectedDate()));
             registro.setHoraEvento((txtHoraEvento.getText()));
             registro.setLugarEvento((txtLugarEvento.getText()));
             procedimiento.setInt(1, registro.getServicios_codigoServicios());
             procedimiento.setInt(2, registro.getCodigoServicio());
             procedimiento.setInt(3, registro.getCodigoEmpleado());
             procedimiento.setDate(4, new java.sql.Date(registro.getFechaEvento().getTime()));
             procedimiento.setString(5, registro.getHoraEvento());
             procedimiento.setString(6, registro.getLugarEvento());
             procedimiento.execute();
        }catch(Exception e){
             e.printStackTrace();
        }

    }
    

    public void reporte(){
        switch(tipoDeOperacion){
         case ACTUALIZAR:
             desactivarControles();
             limpiarControles();
             btnEditar.setText("Editar");
             btnEditar.setDisable(false);
             btnReporte.setText("Reporte");
             btnReporte.setDisable(false);
             btnNuevo.setDisable(false);
             btnEliminar.setDisable(false);
             tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    

}
