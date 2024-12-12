package com.mycompany.petsociety.models;

import java.time.LocalDateTime;

public class PetHistory {
    private int id;
    private Animal pet;
    private double weight;
    private double height;
    private LocalDateTime registerDate;

    public PetHistory() {
    }
    

    public PetHistory(int id, Animal pet, double weight, double height, LocalDateTime registerDate) {
        this.id = id;
        this.pet = pet;
        this.weight = weight;
        this.height = height;
        this.registerDate = registerDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getPet() {
        return pet;
    }

    public void setPet(Animal pet) {
        this.pet = pet;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "PetHistory [id=" + id + ", pet=" + pet.getName() + ", weight=" + weight + ", height=" + height
                + ", registerDate=" + registerDate + "]";
    }

}
