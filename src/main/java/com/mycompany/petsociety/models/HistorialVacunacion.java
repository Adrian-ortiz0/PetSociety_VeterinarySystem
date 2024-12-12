package com.mycompany.petsociety.models;

import java.time.LocalDateTime;

public class HistorialVacunacion {
    private int id;
    private Animal animal;
    private Vacuna vacuna;
    private Veterinarian veterinarian;
    private LocalDateTime fechaAplicacion;
    private LocalDateTime proximaDosis;

    public HistorialVacunacion(int id, Animal animal, Vacuna vacuna, Veterinarian veterinarian, LocalDateTime fechaAplicacion, LocalDateTime proximaDosis) {
        this.id = id;
        this.animal = animal;
        this.vacuna = vacuna;
        this.veterinarian = veterinarian;
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

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }

    public LocalDateTime getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDateTime fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDateTime getProximaDosis() {
        return proximaDosis;
    }

    public void setProximaDosis(LocalDateTime proximaDosis) {
        this.proximaDosis = proximaDosis;
    }

    // Método para imprimir los detalles del historial de vacunación
    public void imprimirHistorial() {
        System.out.println("Historial de Vacunación:");
        System.out.println("ID: " + id);
        System.out.println("Animal: " + animal.getName());
        System.out.println("Vacuna: " + vacuna.getNombre());
        System.out.println("Fecha de Aplicación: " + fechaAplicacion);
        System.out.println("Próxima Dosis: " + proximaDosis);
    }
}
