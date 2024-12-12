
package com.mycompany.petsociety.models;

public abstract class User {
    
    private int id;
    private String name;
    private String cedula;
    private String phone;
    private String email;

    public User(int id, String name, String cedula, String phone, String email) {
        this.id = id;
        this.name = name;
        this.cedula = cedula;
        this.phone = phone;
        this.email = email;
    }
    public User(String name, String cedula, String phone, String email) {
        this.name = name;
        this.cedula = cedula;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", cedula=" + cedula + ", phone=" + phone + ", email=" + email + '}';
    }
}
