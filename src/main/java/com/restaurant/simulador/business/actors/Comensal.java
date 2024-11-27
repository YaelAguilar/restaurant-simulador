package com.restaurant.simulador.business.actors;

import com.restaurant.simulador.business.models.Mesa;

public class Comensal {
    private final int id;
    private Mesa mesaAsignada;

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
}
