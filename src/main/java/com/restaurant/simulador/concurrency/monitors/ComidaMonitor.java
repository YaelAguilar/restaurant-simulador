package com.restaurant.simulador.concurrency.monitors;

import com.restaurant.simulador.business.models.Comida;

import java.util.LinkedList;
import java.util.Queue;

public class ComidaMonitor {
    private final Queue<Comida> bufferComidas;

    public ComidaMonitor() {
        bufferComidas = new LinkedList<>();
    }

    public synchronized void addComida(Comida comida) {
        bufferComidas.add(comida);
        System.out.println("Comida " + comida.getId() + " añadida al buffer de comidas.");
        notifyAll(); // Notificar a los meseros que hay una comida lista
    }

    public synchronized Comida getComida() throws InterruptedException {
        while (bufferComidas.isEmpty()) {
            wait(); // Esperar hasta que haya una comida disponible
        }
        Comida comida = bufferComidas.poll();
        System.out.println("Comida " + comida.getId() + " retirada del buffer de comidas.");
        return comida;
    }
}
