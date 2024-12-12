package com.mycompany.petsociety.models;

public class DescuentoIntermedio implements DescuentosStrategy{

    @Override
    public double calcularDescuento(int puntos) {
        if(puntos < 250){
            return 0.10;
        }
        return 0.20;
    }
    
}
