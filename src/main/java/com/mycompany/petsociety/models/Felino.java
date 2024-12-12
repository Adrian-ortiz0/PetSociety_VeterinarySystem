package com.mycompany.petsociety.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Felino extends Animal{

    public Felino() {
    }

    public Felino(String name, String breed, LocalDate birthDate, Sex sex, String microChip, String photo, PetStatus petStatus, Owner owner) {
        super(name, breed, birthDate, sex, microChip, photo,  petStatus, owner);
        this.setSpecie("Felino");
    }

    public Felino(int id, String name, String breed, LocalDate birthDate, Sex sex, String microChip, String photo, LocalDateTime registerDate, PetStatus petStatus, Owner owner) {
        super(id, name, breed, birthDate, sex, microChip, photo, registerDate, petStatus, owner);
        this.setSpecie("Felino");
    }

    
}
