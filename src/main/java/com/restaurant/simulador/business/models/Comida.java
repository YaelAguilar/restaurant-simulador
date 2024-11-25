package com.restaurant.simulador.business.models;

public class Comida {
    private final int id;
    private final Orden orden;
    private final int tiempoPreparacion; // en milisegundos

    public Comida(int id, Orden orden, int tiempoPreparacion) {
        this.id = id;
        this.orden = orden;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public int getId() {
        return id;
    }

    public Orden getOrden() {
        return orden;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void preparar() {
        try {
            Thread.sleep(tiempoPreparacion);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
