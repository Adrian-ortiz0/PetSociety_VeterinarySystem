package com.mycompany.petsociety.models;

public class DescuentosStrategyFactory {

    public static DescuentosStrategy obtenerEstrategiaPorPuntos(int puntos) {
        if (puntos < 100) {
            return new SinDescuento();
        } else if (puntos >= 100 && puntos < 250) {
            return new DescuentoBasico();
        } else if (puntos >= 250 && puntos < 500) {
            return new DescuentoIntermedio();
        } else {
            return new DescuentoPremium();
        }
    }
}