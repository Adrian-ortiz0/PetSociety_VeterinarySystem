package com.mycompany.petsociety.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Animal {
    private int id;
    private String name;
    private String breed;
    private LocalDate birthDate;
    private Sex sex;
    private String microChip;
    private String photo;
    private LocalDateTime registerDate;
    private PetStatus petStatus;
    private String specie;
    private Owner owner;
    private double peso;
    private double altura;

    public Animal() {
    }

    public Animal(String name, String breed, LocalDate birthDate, Sex sex, String microChip, String photo, PetStatus petStatus,  Owner owner) {
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.sex = sex;
        this.microChip = microChip;
        this.photo = photo;
        this.petStatus = petStatus;
        this.owner = owner;
    }
    

    public Animal(int id, String name, String breed, LocalDate birthDate, Sex sex, String microChip, String photo, LocalDateTime registerDate, PetStatus petStatus, Owner owner) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.sex = sex;
        this.microChip = microChip;
        this.photo = photo;
        this.registerDate = registerDate;
        this.petStatus = petStatus;
        this.owner = owner;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
    

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getMicroChip() {
        return microChip;
    }

    public void setMicroChip(String microChip) {
        this.microChip = microChip;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public PetStatus getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(PetStatus petStatus) {
        this.petStatus = petStatus;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}