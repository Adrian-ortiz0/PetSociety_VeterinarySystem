package com.mycompany.petsociety.models;

public enum Sex {
    MALE(1, "Male"), 
    FEMALE(2, "Female");
    
    private int idSex;
    private String sex;
    
    Sex(int idSex, String sex){
        this.idSex = idSex;
        this.sex = sex;
    }

    public int getIdSex() {
        return idSex;
    }

    public void setIdSex(int idSex) {
        this.idSex = idSex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
