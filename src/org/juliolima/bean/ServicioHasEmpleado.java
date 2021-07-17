/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juliolima.bean;

import java.util.Date;


public class ServicioHasEmpleado {
  
    private int Servicios_codigoServicios;
    private int codigoServicio ;
    private int codigoEmpleado;
    private Date fechaEvento ;
    private String horaEvento;
    private String lugarEvento;

    public ServicioHasEmpleado() {
    }

    public ServicioHasEmpleado(int Servicios_codigoServicios, int codigoServicio, int codigoEmpleado, Date fechaEvento, String horaEvento, String lugarEvento) {
        this.Servicios_codigoServicios = Servicios_codigoServicios;
        this.codigoServicio = codigoServicio;
        this.codigoEmpleado = codigoEmpleado;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
    }

    public int getServicios_codigoServicios() {
        return Servicios_codigoServicios;
    }

    public void setServicios_codigoServicios(int Servicios_codigoServicios) {
        this.Servicios_codigoServicios = Servicios_codigoServicios;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

  

    
   
}
