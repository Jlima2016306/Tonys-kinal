
package org.juliolima.bean;

import java.util.Date;


public class Servicios {
    private int codigoServicio;
    private Date fechaServivio;
    private String tipoServicio;
    private String horaServicio ;
    private String lugarServicio;
    private String telefonoContacto ;
    private int codigoEmpresa;

    public Servicios() {
    }
    
    
    public Servicios(int codigoServicio, Date fechaServivio, String tipoServicio, String horaServicio, String lugarServicio, String telefonoContacto, int codigoEmpresa) {
        this.codigoServicio = codigoServicio;
        this.fechaServivio = fechaServivio;
        this.tipoServicio = tipoServicio;
        this.horaServicio = horaServicio;
        this.lugarServicio = lugarServicio;
        this.telefonoContacto = telefonoContacto;
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Date getFechaServivio() {
        return fechaServivio;
    }

    public void setFechaServivio(Date fechaServivio) {
        this.fechaServivio = fechaServivio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(String horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getLugarServicio() {
        return lugarServicio;
    }

    public void setLugarServicio(String lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

                public String toString(){
        return getCodigoServicio()+")"+getTipoServicio();
    }
    
}
