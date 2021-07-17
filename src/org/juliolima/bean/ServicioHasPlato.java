/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juliolima.bean;

public class ServicioHasPlato {
    private int codigoServicio;
    private int CodigoPlato ;

    public ServicioHasPlato() {
    }

    public ServicioHasPlato(int codigoServicio, int CodigoPlato) {
        this.codigoServicio = codigoServicio;
        this.CodigoPlato = CodigoPlato;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public int getCodigoPlato() {
        return CodigoPlato;
    }

    public void setCodigoPlato(int CodigoPlato) {
        this.CodigoPlato = CodigoPlato;
    }

    
    
}
