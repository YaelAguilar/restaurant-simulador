package com.restaurant.simulador.business.services;

public class CocinaService {
    public static int getTiempoPreparacion() {
        // Generar un tiempo de preparación aleatorio entre 2 y 5 segundos (2000 - 5000 ms)
        return (int) (Math.random() * 3000) + 2000;
    }

    // Métodos adicionales para gestionar comidas si es necesario
}
