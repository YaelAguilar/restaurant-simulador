package com.restaurant.simulador.concurrency.monitors;

import com.restaurant.simulador.business.actors.Comensal;
import com.restaurant.simulador.business.models.Mesa;
import com.restaurant.simulador.business.models.EstadoMesa;

import java.util.ArrayList;
import java.util.List;

public class MesaMonitor {
    private final List<Mesa> mesas;

    public MesaMonitor(int capacidad) {
        mesas = new ArrayList<>();
        for (int i = 1; i <= capacidad; i++) {
            mesas.add(new Mesa(i));
        }
    }

    public synchronized Mesa asignarMesa(Comensal comensal) throws InterruptedException {
        while (true) {
            for (Mesa mesa : mesas) {
                if (mesa.getEstado() == EstadoMesa.LIBRE) {
                    mesa.asignarComensal();
                    System.out.println("Mesa " + mesa.getNumero() + " asignada al Comensal " + comensal.getId());
                    return mesa;
                }
            }
            wait(); // Esperar hasta que una mesa esté disponible
        }
    }

    public synchronized void liberarMesa(Mesa mesa) {
        mesa.liberarMesa();
        System.out.println("Mesa " + mesa.getNumero() + " liberada.");
        notifyAll(); // Notificar a los comensales que una mesa está disponible
    }

    public int getCapacidad() {
        return mesas.size();
    }

    public synchronized int mesasDisponibles() {
        return (int) mesas.stream().filter(mesa -> mesa.getEstado() == EstadoMesa.LIBRE).count();
    }
}
