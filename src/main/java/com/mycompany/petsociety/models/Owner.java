package com.mycompany.petsociety.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Owner extends User{
    private String address;
    private String emergencyContact;
    private LocalDateTime registerDate;
    ArrayList<Animal> pets = new ArrayList();
    private ClubMember clubMembership;

    public Owner(int id, String name, String cedula, String phone, String email, String adress, String emergencyContact, LocalDateTime registerDate) {
        super(id, name, cedula, phone, email);
        this.address = adress;
        this.emergencyContact = emergencyContact;
        this.registerDate = registerDate;
    }

    public Owner(int id, String name, String cedula, String phone, String email, String address, String emergencyContact, LocalDateTime registerDate, ClubMember clubMembership) {
        super(id, name, cedula, phone, email);
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.registerDate = registerDate;
        this.clubMembership = clubMembership;
    }

    public ArrayList<Animal> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Animal> pets) {
        this.pets = pets;
    }

    public ClubMember getClubMembership() {
        return clubMembership;
    }

    public void setClubMembership(ClubMember clubMembership) {
        this.clubMembership = clubMembership;
    }

    
    public Owner(String name, String cedula, String phone, String email, String address, String emergenyContact, LocalDateTime registerDate){
        super(name, cedula, phone, email);
        this.address = address;
        this.emergencyContact = emergenyContact;
        this.registerDate = registerDate;
    }
    
    public Owner(String name, String cedula,  String address, String phone, String email, String emergencyContact){
        super(name, cedula, phone, email);
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "Owner{" + "address=" + address + ", emergencyContact=" + emergencyContact + ", registerDate=" + registerDate + '}';
    }
    
}


