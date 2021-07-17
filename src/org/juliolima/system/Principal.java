/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juliolima.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.juliolima.controller.DatosPersonalesController;
import org.juliolima.controller.EmpleadosController;
import org.juliolima.controller.EmpresaController;
import org.juliolima.controller.MenuPrincipalController;
import org.juliolima.controller.PlatoController;
import org.juliolima.controller.PresupuestoController;
import org.juliolima.controller.ProductoHasPlatoController;
import org.juliolima.controller.ProductosController;
import org.juliolima.controller.ServicioController;
import org.juliolima.controller.ServicioHasEmpleadoController;
import org.juliolima.controller.ServicioHasPlatoController;
import org.juliolima.controller.TipoEmpleadoController;
import org.juliolima.controller.TipoPlatoController;

/**
 *
 * @author julio
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/juliolima/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    
    @Override 
    public void start(Stage escenarioPrincipal) throws Exception{
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tonys Kinal app");
        escenarioPrincipal.getIcons().add(new  Image("/org/juliolima/images/icono.PNG"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/juliolima/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();
    }

    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",513,399);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
}    
   

    
    public void ventanaProgramador(){
        try{
            DatosPersonalesController datosPersonales = (DatosPersonalesController)cambiarEscena("DatosPersonalesView.fxml",496,396);
            datosPersonales.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ventanaEmpresa(){
        try{
            EmpresaController Datosempresa = (EmpresaController)cambiarEscena("EmpresaView.fxml",779,484);
            Datosempresa.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPresupuestos(){
        try{
            PresupuestoController DatosPresupuesto = (PresupuestoController)cambiarEscena("PresupuestoView.fxml",779,484);
            DatosPresupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaTipoEmpleado(){
        try{
        TipoEmpleadoController DatostP = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",763,472);
        DatostP.setEcenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleados(){
        try{
        EmpleadosController Emplea = (EmpleadosController)cambiarEscena("EmpleadosView.fxml",949,587);
        Emplea.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicios(){
        try{
        ServicioController servicio = (ServicioController)cambiarEscena("ServiciosView.fxml",720,498);
        servicio.setEscenarioPrinciapal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductos(){
        try{
        ProductosController producto =  (ProductosController)cambiarEscena("ProductosView.fxml",779,484);
        producto.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoPlato(){
        try{
        TipoPlatoController tipoplato = (TipoPlatoController)cambiarEscena("TipoPlatoView.fxml",779,484);
        tipoplato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VentanaPlato(){
        try{
        PlatoController plato = (PlatoController)cambiarEscena("PlatoView.fxml",949,587);
        plato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
    }
    }
    
    public void ventanaServicioHasEmpleadoController(){
        try{
        ServicioHasEmpleadoController servicio = (ServicioHasEmpleadoController)cambiarEscena("ServiciosHasEmpleadosView.fxml",779,484);
        servicio.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
    public void ventnaProductoHasPlato(){
        try{
        ProductoHasPlatoController prod = (ProductoHasPlatoController)cambiarEscena("ProductosHasPlatosView.fxml",779,484);
        prod.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    public void ventanaServicioHasPlato(){
        try{
        ServicioHasPlatoController servi= (ServicioHasPlatoController)cambiarEscena("ServiciosHasPlatosView.fxml",779,484);
        servi.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        launch(args);
    }
    
    public Initializable cambiarEscena (String fxml, int ancho, int alto) throws Exception{
       Initializable resultado = null;
       FXMLLoader cargadorFXML = new FXMLLoader();
       InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
       cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
       cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
       escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
       escenarioPrincipal.setScene(escena);
       escenarioPrincipal.sizeToScene();
       resultado = (Initializable)cargadorFXML.getController();
       
       
       return resultado;
    }
    
    
}
