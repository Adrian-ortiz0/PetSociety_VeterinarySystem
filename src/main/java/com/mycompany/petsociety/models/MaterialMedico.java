package com.mycompany.petsociety.models;

import java.math.BigDecimal;

public class MaterialMedico extends Insumo {
    private String tipo;
    private int cantidad;

    public MaterialMedico(String tipo, int cantidad, int id, String nombre, String fabricante, String unidadMedida, CategoriaInsumos categoria, int nivelMinimo, int cantidadStock, BigDecimal precioUnitario, Proveedor proveedor) {
        super(id, nombre, fabricante, unidadMedida, categoria, nivelMinimo, cantidadStock, precioUnitario, proveedor);
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return super.toString() + ", MaterialMedico{tipo='" + tipo + "', cantidad=" + cantidad + "}";
    }

}
