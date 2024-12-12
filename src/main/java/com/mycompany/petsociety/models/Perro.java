
package com.mycompany.petsociety.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Perro extends Caninos{

    public Perro() {
    }

    public Perro(String name, String breed, LocalDate birthDate, Sex sex, String microChip, String photo,  PetStatus petStatus, Owner owner) {
        super(name, breed, birthDate, sex, microChip, photo, petStatus, owner);
    }

    public Perro(int id, String name, String breed, LocalDate birthDate, Sex sex, String microChip, String photo, LocalDateTime registerDate, PetStatus petStatus, Owner owner) {
        super(id, name, breed, birthDate, sex, microChip, photo, registerDate, petStatus, owner);
    }
    
}