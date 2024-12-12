package com.mycompany.petsociety.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Factura {
    private int id;
    private LocalDate fecha;
    private String veterinariaNombre = "Clínica Veterinaria PetSociety";
    private String veterinariaNit = "NIT: 900.025.1818-7";
    private String veterinariaDireccion = "Cra 20 # 110-69, Bucaramanga";
    private String veterinariaTelefono = "Tel: (+57) 317-310-9599";
    private String cufe = "636363636363636-J";
    private String email = "Dxniel7328@gmail.com";
    private BigDecimal total;
    private Owner owner;

    public Factura(int id, LocalDate fecha, BigDecimal total, Owner owner) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.owner = owner;
    }

    public Factura(LocalDate fecha, BigDecimal total, Owner owner) {
        this.fecha = fecha;
        this.total = total;
        this.owner = owner;
    }
    
    

    public String getVeterinariaNombre() {
        return veterinariaNombre;
    }

    public void setVeterinariaNombre(String veterinariaNombre) {
        this.veterinariaNombre = veterinariaNombre;
    }

    public String getVeterinariaNit() {
        return veterinariaNit;
    }

    public void setVeterinariaNit(String veterinariaNit) {
        this.veterinariaNit = veterinariaNit;
    }

    public String getVeterinariaDireccion() {
        return veterinariaDireccion;
    }

    public void setVeterinariaDireccion(String veterinariaDireccion) {
        this.veterinariaDireccion = veterinariaDireccion;
    }

    public String getVeterinariaTelefono() {
        return veterinariaTelefono;
    }

    public void setVeterinariaTelefono(String veterinariaTelefono) {
        this.veterinariaTelefono = veterinariaTelefono;
    }

    public String getCufe() {
        return cufe;
    }

    public void setCufe(String cufe) {
        this.cufe = cufe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    
    
    
}
