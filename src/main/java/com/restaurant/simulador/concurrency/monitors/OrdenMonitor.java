package com.restaurant.simulador.concurrency.monitors;

import com.restaurant.simulador.business.models.Orden;

import java.util.LinkedList;
import java.util.Queue;

public class OrdenMonitor {
    private final Queue<Orden> bufferOrdenes;

    public OrdenMonitor() {
        bufferOrdenes = new LinkedList<>();
    }

    public synchronized void addOrden(Orden orden) {
        bufferOrdenes.add(orden);
        System.out.println("Orden " + orden.getId() + " añadida al buffer de órdenes.");
        notifyAll(); // Notificar a los cocineros que hay una nueva orden
    }

    public synchronized Orden getOrden() throws InterruptedException {
        while (bufferOrdenes.isEmpty()) {
            wait(); // Esperar hasta que haya una orden disponible
        }
        Orden orden = bufferOrdenes.poll();
        System.out.println("Orden " + orden.getId() + " retirada del buffer de órdenes.");
        return orden;
    }
}
