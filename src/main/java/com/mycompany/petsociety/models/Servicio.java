package com.mycompany.petsociety.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Servicio {
    private int id;
    private String nombre;
    private TipoServicio tipoServicio;
    private BigDecimal costoBase;

    public Servicio() {
    }

    
    public Servicio(int id, TipoServicio tipoServicio, BigDecimal costoBase, String nombre) {
        this.id = id;
        this.tipoServicio = tipoServicio;
        this.costoBase = costoBase;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    

    public BigDecimal getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(BigDecimal costoBase) {
        this.costoBase = costoBase;
    }
    
}
