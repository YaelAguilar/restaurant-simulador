package com.restaurant.simulador.concurrency.threads;

import com.restaurant.simulador.business.actors.Cocinero;
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
    private final RestauranteService service;

    public CocineroThread(Cocinero cocinero, OrdenMonitor ordenMonitor, ComidaMonitor comidaMonitor, RestauranteView view, RestauranteService service) {
        this.cocinero = cocinero;
        this.ordenMonitor = ordenMonitor;
        this.comidaMonitor = comidaMonitor;
        this.view = view;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Retirar orden del buffer
                Orden orden = ordenMonitor.retirarOrden();
                System.out.println("Orden " + orden.getId() + " retirada del buffer de órdenes.");
                System.out.println("Cocinero " + cocinero.getId() + " está preparando la Orden " + orden.getId());

                // Simular tiempo de preparación
                Thread.sleep(3000); // Simula el tiempo de preparación

                // Añadir comida al buffer de comidas
                comidaMonitor.agregarComida(orden);
                System.out.println("Cocinero " + cocinero.getId() + " ha terminado de preparar la Orden " + orden.getId());
                System.out.println("Comida " + orden.getId() + " añadida al buffer de comidas.");

                // Actualizar estadísticas
                service.incrementarOrdenesProcesadas();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
