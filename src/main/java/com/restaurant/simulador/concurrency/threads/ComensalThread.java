package com.restaurant.simulador.concurrency.threads;

import com.restaurant.simulador.business.actors.Comensal;
import com.restaurant.simulador.business.models.Mesa;
import com.restaurant.simulador.concurrency.monitors.MesaMonitor;
import com.restaurant.simulador.concurrency.monitors.ComensalMonitor;
import com.restaurant.simulador.concurrency.monitors.OrdenMonitor;
import com.restaurant.simulador.presentation.views.RestauranteView;
import com.restaurant.simulador.business.actors.Recepcionista;

public class ComensalThread extends Thread {
    private final Comensal comensal;
    private final Recepcionista recepcionista;
    private final MesaMonitor mesaMonitor;
    private final ComensalMonitor comensalMonitor;
    private final RestauranteView view;
    private final OrdenMonitor ordenMonitor;

    public ComensalThread(Comensal comensal, Recepcionista recepcionista, MesaMonitor mesaMonitor, ComensalMonitor comensalMonitor, RestauranteView view, OrdenMonitor ordenMonitor) {
        this.comensal = comensal;
        this.recepcionista = recepcionista;
        this.mesaMonitor = mesaMonitor;
        this.comensalMonitor = comensalMonitor;
        this.view = view;
        this.ordenMonitor = ordenMonitor;
    }

    @Override
    public void run() {
        try {
            // Registrar al comensal y asignar mesa
            Mesa mesa = recepcionista.registrarComensal(comensal, mesaMonitor);
            view.actualizarEstadoMesa(mesa.getNumero(), mesa.getEstado());

            // Añadir comensal a la cola de espera para ser atendido
            comensalMonitor.addComensal(comensal);
            view.actualizarComensalEstado(comensal.getId(), "Esperando ser atendido");

            // Esperar a que el mesero atienda y sirva la orden
            synchronized (comensal) {
                comensal.wait(); // Esperar a que el mesero tome la orden y la sirva
            }

            // Simular tiempo comiendo
            view.actualizarComensalEstado(comensal.getId(), "Comiendo");
            comensal.comer();

            // Liberar mesa
            mesaMonitor.liberarMesa(mesa);
            view.actualizarEstadoMesa(mesa.getNumero(), mesa.getEstado());

            // Notificar a la interfaz que el comensal se ha ido
            view.actualizarComensalEstado(comensal.getId(), "Se ha ido");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Comensal getComensal() {
        return comensal;
    }
}
