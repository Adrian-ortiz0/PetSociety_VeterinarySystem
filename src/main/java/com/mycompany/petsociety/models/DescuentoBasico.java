
package com.mycompany.petsociety.models;

public class DescuentoBasico implements DescuentosStrategy{

    @Override
    public double calcularDescuento(int puntos) {
       if(puntos < 100){
           return 0.0;
       }
       return 0.15;
    }
    
}
