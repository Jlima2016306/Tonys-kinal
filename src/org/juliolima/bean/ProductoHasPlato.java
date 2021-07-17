/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juliolima.bean;

public class ProductoHasPlato {
    private int CodigoProducto ;
    private int CodigoPlato ;

    public ProductoHasPlato() {
    }

    public ProductoHasPlato(int CodigoProducto, int CodigoPlato) {
        this.CodigoProducto = CodigoProducto;
        this.CodigoPlato = CodigoPlato;
    }

    public int getCodigoProducto() {
        return  CodigoProducto;
        
        
    }

    public void setCodigoProducto(int CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
        
        
    }

    public int getCodigoPlato() {
        return CodigoPlato;
    }

    public void setCodigoPlato(int CodigoPlato) {
        this.CodigoPlato = CodigoPlato;
    }

    
    
    
}
