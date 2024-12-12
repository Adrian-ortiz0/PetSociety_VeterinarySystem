package com.mycompany.petsociety.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Vacuna extends Insumo {
    private String tipoEspecie;
    private String lote;
    private LocalDate fechaIngreso;
    private LocalDate fechaVencimiento;
    private LocalDate fechaAplicacion;
    
    public Vacuna(){
        
    }

    public Vacuna(String tipoEspecie, String lote, LocalDate fechaIngreso, LocalDate fechaVencimiento, int id, String nombre, String fabricante, String unidadMedida, CategoriaInsumos categoria, int nivelMinimo, int cantidadStock, BigDecimal precioUnitario, Proveedor proveedor) {
        super(id, nombre, fabricante, unidadMedida, categoria, nivelMinimo, cantidadStock, precioUnitario, proveedor);
        this.tipoEspecie = tipoEspecie;
        this.lote = lote;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Vacuna(String tipoEspecie, String lote, LocalDate fechaIngreso, LocalDate fechaVencimiento, LocalDate fechaAplicacion, int id, String nombre, String fabricante, String unidadMedida, CategoriaInsumos categoria, int nivelMinimo, int cantidadStock, BigDecimal precioUnitario, Proveedor proveedor) {
        super(id, nombre, fabricante, unidadMedida, categoria, nivelMinimo, cantidadStock, precioUnitario, proveedor);
        this.tipoEspecie = tipoEspecie;
        this.lote = lote;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }
    
    

    public String getTipoEspecie() {
        return tipoEspecie;
    }

    public void setTipoEspecie(String tipoEspecie) {
        this.tipoEspecie = tipoEspecie;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String LocalDate) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String LocalDate) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return super.toString() + ", Vacuna{tipoEspecie='" + tipoEspecie + "', lote='" + lote + "', fechaIngreso='" + fechaIngreso + "', fechaVencimiento='" + fechaVencimiento + "'}";
    }
}