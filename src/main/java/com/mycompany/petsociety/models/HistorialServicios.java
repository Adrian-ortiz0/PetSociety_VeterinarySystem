package com.mycompany.petsociety.models;

import java.time.LocalDate;

public class HistorialServicios {
    private int id;
    private Animal animal;
    private Servicio servicio;
    private Veterinarian veterinario;
    private LocalDate fechaAplicacion;
    private LocalDate proximaDosis;

    public HistorialServicios() {
    }

    public HistorialServicios(int id, Animal animal, Servicio servicio, Veterinarian veterinario, LocalDate fechaAplicacion, LocalDate proximaDosis) {
        this.id = id;
        this.animal = animal;
        this.servicio = servicio;
        this.veterinario = veterinario;
        this.fechaAplicacion = fechaAplicacion;
        this.proximaDosis = proximaDosis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Veterinarian getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinarian veterinario) {
        this.veterinario = veterinario;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDate getProximaDosis() {
        return proximaDosis;
    }

    public void setProximaDosis(LocalDate proximaDosis) {
        this.proximaDosis = proximaDosis;
    }
    
    
}
