package com.mycompany.petsociety.models;

public class DescuentoPremium implements DescuentosStrategy{

    @Override
    public double calcularDescuento(int puntos) {
        if(puntos < 500){
            return 0.25;
        }
        return 0.30;
    }
    
}
