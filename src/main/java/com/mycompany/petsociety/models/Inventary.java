package com.mycompany.petsociety.models;

import java.util.ArrayList;

public class Inventary {
    private int id;
    private Insumo insumo;
    private double cantidad;
    private String lote;
    private String fechaIngreso;
    private String fechaVencimiento;

    public Inventary(int id, Insumo insumo, double cantidad, String lote, String fechaIngreso, String fechaVencimiento) {
        this.id = id;
        this.insumo = insumo;
        this.cantidad = cantidad;
        this.lote = lote;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Inventario{id=" + id + ", insumo=" + insumo + ", cantidad=" + cantidad + ", lote='" + lote + "', fechaIngreso='" + fechaIngreso + "', fechaVencimiento='" + fechaVencimiento + "'}";
    }
}
