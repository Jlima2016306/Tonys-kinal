/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juliolima.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.juliolima.system.Principal;


public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
  
    

    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    public void ventanaEmpresa(){
        escenarioPrincipal.ventanaEmpresa();
    }
    public void ventanaPresupuestos(){
        escenarioPrincipal.ventanaPresupuestos();
    }
    public void ventanaTipoEmpleado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    
    public void ventanaEmpleados(){
        escenarioPrincipal.ventanaEmpleados();
    }
    
    public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
    
    public void ventanaProductos(){
        escenarioPrincipal.ventanaProductos();
    }
    
    public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
    }
    
    public void ventanaPlato(){
        escenarioPrincipal.VentanaPlato();
    }
    
    public void ventanaServicioHasEmpleadoController(){
        escenarioPrincipal.ventanaServicioHasEmpleadoController();
    }
    
    public void ventanaServicioHasPlato(){
        escenarioPrincipal.ventanaServicioHasPlato();
    }
    
    public void ventnaProductoHasPlato(){
        escenarioPrincipal.ventnaProductoHasPlato();
    }
    public void exit(){
        System.exit(0);
    }
}

    