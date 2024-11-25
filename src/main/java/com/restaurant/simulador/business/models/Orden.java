package com.restaurant.simulador.business.models;

import com.restaurant.simulador.business.actors.Comensal;

public class Orden {
    private final int id;
    private final Comensal comensal;
    private EstadoOrden estado;

    public Orden(int id, Comensal comensal) {
        this.id = id;
        this.comensal = comensal;
        this.estado = EstadoOrden.EN_PROCESO;
    }

    public int getId() {
        return id;
    }

    public Comensal getComensal() {
        return comensal;
    }

    public synchronized EstadoOrden getEstado() {
        return estado;
    }

    public synchronized void cambiarEstado(EstadoOrden nuevoEstado) {
        this.estado = nuevoEstado;
    }
}
