
package com.mycompany.petsociety.models;

public class ServicioInsumo {
    private Servicio servicio;
    private Insumo insumo;
    private int cantidad;

    public ServicioInsumo(Servicio servicio, Insumo insumo, int cantidad) {
        this.servicio = servicio;
        this.insumo = insumo;
        this.cantidad = cantidad;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
            
}
