package com.restaurant.simulador.business.models;

public class Mesa {
    private final int numero;
    private EstadoMesa estado;

    public Mesa(int numero) {
        this.numero = numero;
        this.estado = EstadoMesa.LIBRE;
    }

    public int getNumero() {
        return numero;
    }

    public synchronized EstadoMesa getEstado() {
        return estado;
    }

    public synchronized void asignarComensal() {
        this.estado = EstadoMesa.OCUPADA;
    }

    public synchronized void liberarMesa() {
        this.estado = EstadoMesa.LIBRE;
    }
}
