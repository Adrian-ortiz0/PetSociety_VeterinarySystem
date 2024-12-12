
package com.mycompany.petsociety.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Desparacitario extends Insumo{

    private TipoAntiParasitario tipo;
    private String tipoEspecie;
    private LocalDate fechaVencimiento;
    
    public Desparacitario(int id, String nombre, String fabricante, String unidadMedida, CategoriaInsumos categoria, int nivelMinimo, int cantidadStock, BigDecimal precioUnitario, Proveedor proveedor, TipoAntiParasitario tipoAntiParasitario, String tipoEspecie, LocalDate fechaVencimiento) {
        super(id, nombre, fabricante, unidadMedida, categoria, nivelMinimo, cantidadStock, precioUnitario, proveedor);
        this.tipo = tipoAntiParasitario;
        this.tipoEspecie = tipoEspecie;
        this.fechaVencimiento = fechaVencimiento;
    }

    public TipoAntiParasitario getTipo() {
        return tipo;
    }

    public void setTipo(TipoAntiParasitario tipo) {
        this.tipo = tipo;
    }

    public String getTipoEspecie() {
        return tipoEspecie;
    }

    public void setTipoEspecie(String tipoEspecie) {
        this.tipoEspecie = tipoEspecie;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
}
