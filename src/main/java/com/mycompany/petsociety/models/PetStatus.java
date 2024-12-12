package com.mycompany.petsociety.models;

public enum PetStatus {
    ACTIVA(1, "Activa"),
    ADOPTADA(2, "Adoptada"),
    FALLECIDA(3, "Facllecida"),
    ESPERANDO_ADOPCION(4, "Esperando_Adopcion");
    
    private int idStatus;
    private String status;
    
    PetStatus(int idStatus, String status){
        this.idStatus = idStatus;
        this.status = status;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
