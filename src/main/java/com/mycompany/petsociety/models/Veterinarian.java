package com.mycompany.petsociety.models;

public class Veterinarian {
    private int id;
    private String cedula;
    private String nombre;
    private String licencia;
    private String especialidad;
    private String telefono;
    private String email;
    private boolean contratado;

    public Veterinarian() {
    }
    
    public Veterinarian(int id, String cedula, String nombre, String licencia, String especialidad, String telefono, String email) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.licencia = licencia;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.contratado = true;
    }

    public Veterinarian(int id, String cedula, String nombre, String licencia, String especialidad, String telefono, String email, boolean contratado) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.licencia = licencia;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.contratado = contratado;
    }
    
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isContratado() {
        return contratado;
    }

    public void setContratado(boolean contratado) {
        this.contratado = contratado;
    }

    @Override
    public String toString() {
        return "Veterinarian{" + "nombre=" + nombre + ", licencia=" + licencia + ", especialidad=" + especialidad + ", telefono=" + telefono + ", email=" + email + ", contratado=" + contratado + '}';
    }
    
    
}
