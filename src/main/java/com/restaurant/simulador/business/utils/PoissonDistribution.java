package com.restaurant.simulador.business.utils;

public class PoissonDistribution {
    public static double generarTiempoLlegada(double lambda) {
        // Generar tiempo entre llegadas basado en la distribución exponencial
        // Aproximación de Poisson para el tiempo entre eventos
        double u = Math.random();
        return -Math.log(1 - u) / lambda;
    }
}
