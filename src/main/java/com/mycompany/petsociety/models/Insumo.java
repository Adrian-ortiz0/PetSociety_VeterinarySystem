package com.mycompany.petsociety.models;


import com.mycompany.petsociety.models.CategoriaInsumos;
import java.math.BigDecimal;

public class Insumo {
    private int id;
    private String nombre;
    private String fabricante;
    private String unidadMedida;
    private CategoriaInsumos categoria; 
    private int nivelMinimo;
    private int cantidadStock;
    private BigDecimal precioUnitario;
    private Proveedor proveedor;
    
    public Insumo(){
        
    }

    public Insumo(int id, String nombre, String fabricante, String unidadMedida, CategoriaInsumos categoria, int nivelMinimo, int cantidadStock, BigDecimal precioUnitario, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.nivelMinimo = nivelMinimo;
        this.cantidadStock = cantidadStock;
        this.precioUnitario = precioUnitario;
        this.proveedor = proveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public int getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(int nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public CategoriaInsumos getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaInsumos categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Insumo{id=" + id + ", nombre='" + nombre + "', fabricante='" + fabricante + "', unidadMedida='" + unidadMedida + "', categoria=" + categoria + "}";
    }
}