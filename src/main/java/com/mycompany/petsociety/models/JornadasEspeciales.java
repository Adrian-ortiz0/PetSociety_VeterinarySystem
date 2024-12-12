
package com.mycompany.petsociety.models;

import java.time.LocalDate;

public class JornadasEspeciales {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private String descripcion;
    private TipoAnimalInsumo tipo;
    private int demandaEsperada;
    private int impactoJornada;

    public JornadasEspeciales(int id, String nombre, LocalDate fecha, String descripcion, TipoAnimalInsumo tipo, int demandaEsperada, int impactoJornada) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.demandaEsperada = demandaEsperada;
        this.impactoJornada = impactoJornada;
    }

    public TipoAnimalInsumo getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnimalInsumo tipo) {
        this.tipo = tipo;
    }

    

    public int getDemandaEsperada() {
        return demandaEsperada;
    }

    public void setDemandaEsperada(int demandaEsperada) {
        this.demandaEsperada = demandaEsperada;
    }

    public int getImpactoJornada() {
        return impactoJornada;
    }

    public void setImpactoJornada(int impactoJornada) {
        this.impactoJornada = impactoJornada;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
