package com.mycompany.petsociety.models;

public class Desparacitacion {
    private int id;
    private Servicio servicio;
    private Desparacitario desparacitario;

    public Desparacitacion(int id, Servicio servicio, Desparacitario desparacitario) {
        this.id = id;
        this.servicio = servicio;
        this.desparacitario = desparacitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Desparacitario getDesparacitario() {
        return desparacitario;
    }

    public void setDesparacitario(Desparacitario desparacitario) {
        this.desparacitario = desparacitario;
    }
    
    
}
