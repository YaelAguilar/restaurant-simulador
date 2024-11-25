package com.restaurant.simulador.concurrency.threads;

import com.restaurant.simulador.business.actors.Mesero;
import com.restaurant.simulador.business.models.Comida;
import com.restaurant.simulador.business.models.Orden;
import com.restaurant.simulador.concurrency.monitors.ComidaMonitor;
import com.restaurant.simulador.concurrency.monitors.ComensalMonitor;
import com.restaurant.simulador.concurrency.monitors.OrdenMonitor;
import com.restaurant.simulador.presentation.views.RestauranteView;
import com.restaurant.simulador.business.services.RestauranteService;

public class MeseroThread extends Thread {
    private final Mesero mesero;
    private final OrdenMonitor ordenMonitor;
    private final ComidaMonitor comidaMonitor;
    private final ComensalMonitor comensalMonitor;
    private final RestauranteView view;
    private final RestauranteService restauranteService;

    public MeseroThread(Mesero mesero, OrdenMonitor ordenMonitor, ComidaMonitor comidaMonitor, ComensalMonitor comensalMonitor, RestauranteView view, RestauranteService restauranteService) {
        this.mesero = mesero;
        this.ordenMonitor = ordenMonitor;
        this.comidaMonitor = comidaMonitor;
        this.comensalMonitor = comensalMonitor;
        this.view = view;
        this.restauranteService = restauranteService;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // Atender a un comensal
                Orden orden = mesero.atenderComensal(comensalMonitor);
                if (orden != null) {
                    ordenMonitor.addOrden(orden);
                    view.actualizarComensalEstado(orden.getComensal().getId(), "Orden tomada");
                }

                // Dormir un tiempo antes de servir la siguiente comida
                Thread.sleep(500); // Ajusta según necesidad

                // Servir comidas listas
                Comida comida = comidaMonitor.getComida();
                if (comida != null) {
                    mesero.servirComida(comida.getOrden().getId());
                    view.actualizarComidaServida(comida.getOrden().getId());

                    // Notificar al comensal que su comida está lista
                    synchronized (comida.getOrden().getComensal()) {
                        comida.getOrden().getComensal().notify();
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
