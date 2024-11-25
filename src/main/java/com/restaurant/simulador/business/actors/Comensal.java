package com.restaurant.simulador.business.actors;

import com.restaurant.simulador.business.models.Mesa;
import com.restaurant.simulador.business.models.Orden;

public class Comensal {
    private final int id;
    private Mesa mesaAsignada;
    private Orden orden;

    public Comensal(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Mesa getMesaAsignada() {
        return mesaAsignada;
    }

    public void setMesaAsignada(Mesa mesaAsignada) {
        this.mesaAsignada = mesaAsignada;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public void comer() {
        // Simular tiempo comiendo
        try {
            Thread.sleep(5000); // Comer durante 5 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Comensal " + id + " ha terminado de comer y se va.");
    }
}
