
package com.mycompany.petsociety.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    private int id;
    private Animal animal;  
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoCita estado;  


    public Cita(int id, Animal animal, LocalDate fecha, LocalTime hora, EstadoCita estado) {
        this.id = id;
        this.animal = animal;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", animal=" + animal +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", estado=" + estado +
                '}';
    }
}
