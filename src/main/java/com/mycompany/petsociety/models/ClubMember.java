package com.mycompany.petsociety.models;

public class ClubMember {
    private int id;
    private Owner owner;
    private String level;
    private int accumulatedPoints;
    private DescuentosStrategy discountStrategy;

    public ClubMember(int id, Owner owner, String level, int accumulatedPoints, DescuentosStrategy discountStrategy) {
        this.id = id;
        this.owner = owner;
        this.level = level;
        this.accumulatedPoints = accumulatedPoints;
        this.discountStrategy = discountStrategy;
    }

    public ClubMember(int id, Owner owner, String level, int accumulatedPoints) {
        this.id = id;
        this.owner = owner;
        this.level = level;
        this.accumulatedPoints = accumulatedPoints;
    }

    public DescuentosStrategy getDiscountStrategy() {
        return discountStrategy;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(int accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

    public double getDiscount() {
        return discountStrategy.calcularDescuento(accumulatedPoints);
    }

    public void setDiscountStrategy(DescuentosStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
}

