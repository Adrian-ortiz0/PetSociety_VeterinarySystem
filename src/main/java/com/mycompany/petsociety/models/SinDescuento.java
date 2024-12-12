
package com.mycompany.petsociety.models;

public class SinDescuento implements DescuentosStrategy {
    @Override
    public double calcularDescuento(int puntos) {
        return 0.0; 
    }
}
