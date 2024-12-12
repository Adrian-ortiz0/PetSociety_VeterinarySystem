package com.mycompany.petsociety.models;

public class Admin extends User{
    
    private System system;
    
    public Admin(String name, String cedula, String phone, String email) {
        super(name, cedula, phone, email);
    }
    
}
