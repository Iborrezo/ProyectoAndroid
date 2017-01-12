package com.example.adminportatil.cebanctortillas;



public class Producto {
    private double precio;
    private int cantidad;
    private String descripcion;

    Producto (double precio, int cantidad, String descripcion){
        this.precio= precio;
        this.cantidad=cantidad;
        this.descripcion= descripcion;
    }
    public String getDetails(){
        return precio+cantidad+descripcion;
    }
}
