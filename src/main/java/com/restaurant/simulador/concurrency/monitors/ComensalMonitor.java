package com.restaurant.simulador.concurrency.monitors;

import com.restaurant.simulador.business.actors.Comensal;

import java.util.LinkedList;
import java.util.Queue;

public class ComensalMonitor {
    private final Queue<Comensal> comensalesEsperando;

    public ComensalMonitor() {
        comensalesEsperando = new LinkedList<>();
    }

    public synchronized void addComensal(Comensal comensal) {
        comensalesEsperando.add(comensal);
        System.out.println("Comensal " + comensal.getId() + " añadido a la cola de espera.");
        notifyAll(); // Notificar a meseros que hay un comensal esperando
    }

    public synchronized Comensal getComensal() throws InterruptedException {
        while (comensalesEsperando.isEmpty()) {
            wait(); // Esperar hasta que haya un comensal
        }
        Comensal comensal = comensalesEsperando.poll();
        System.out.println("Comensal " + comensal.getId() + " retirado de la cola de espera.");
        return comensal;
    }

    public synchronized int getComensalesEsperando() {
        return comensalesEsperando.size();
    }
}
