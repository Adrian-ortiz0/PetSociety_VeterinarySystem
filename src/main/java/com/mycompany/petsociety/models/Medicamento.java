
package com.mycompany.petsociety.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Medicamento extends Insumo {
    private String tipo;
    private String tipoEspecie;
    private LocalDate fechaVencimiento;

    public Medicamento(String tipo, String tipoEspecie, LocalDate fechaVencimiento, int id, String nombre, String fabricante, String unidadMedida, CategoriaInsumos categoria, int nivelMinimo, int cantidadStock, BigDecimal precioUnitario, Proveedor proveedor) {
        super(id, nombre, fabricante, unidadMedida, categoria, nivelMinimo, cantidadStock, precioUnitario, proveedor);
        this.tipo = tipo;
        this.tipoEspecie = tipoEspecie;
        this.fechaVencimiento = fechaVencimiento;
    }
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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

    @Override
    public String toString() {
        return "Medicamento{" + "tipo=" + tipo + ", tipoEspecie=" + tipoEspecie + ", fechaVencimiento=" + fechaVencimiento + '}';
    }

    
}
