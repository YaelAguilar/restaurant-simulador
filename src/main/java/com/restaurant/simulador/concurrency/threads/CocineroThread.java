package com.restaurant.simulador.concurrency.threads;

import com.restaurant.simulador.business.actors.Cocinero;
import com.restaurant.simulador.business.models.Comida;
import com.restaurant.simulador.business.models.Orden;
import com.restaurant.simulador.concurrency.monitors.ComidaMonitor;
import com.restaurant.simulador.concurrency.monitors.OrdenMonitor;
import com.restaurant.simulador.presentation.views.RestauranteView;
import com.restaurant.simulador.business.services.RestauranteService;

public class CocineroThread extends Thread {
    private final Cocinero cocinero;
    private final OrdenMonitor ordenMonitor;
    private final ComidaMonitor comidaMonitor;
    private final RestauranteView view;
    private final RestauranteService restauranteService;

    public CocineroThread(Cocinero cocinero, OrdenMonitor ordenMonitor, ComidaMonitor comidaMonitor, RestauranteView view, RestauranteService restauranteService) {
        this.cocinero = cocinero;
        this.ordenMonitor = ordenMonitor;
        this.comidaMonitor = comidaMonitor;
        this.view = view;
        this.restauranteService = restauranteService;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // Tomar una orden del buffer
                Orden orden = ordenMonitor.getOrden();

                // Preparar la comida
                Comida comida = cocinero.prepararComida(orden);
                comidaMonitor.addComida(comida);

                // Notificar a la interfaz que la comida está lista
                view.actualizarComidaLista(comida.getOrden().getId());

                // Incrementar contador de órdenes procesadas
                restauranteService.incrementarOrdenesProcesadas();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
