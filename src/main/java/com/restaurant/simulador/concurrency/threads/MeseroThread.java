package com.restaurant.simulador.concurrency.threads;

import com.restaurant.simulador.business.actors.Comensal;
import com.restaurant.simulador.business.actors.Mesero;
import com.restaurant.simulador.business.models.Mesa;
import com.restaurant.simulador.business.models.Orden;
import com.restaurant.simulador.concurrency.monitors.ComensalMonitor;
import com.restaurant.simulador.concurrency.monitors.ComidaMonitor;
import com.restaurant.simulador.concurrency.monitors.OrdenMonitor;
import com.restaurant.simulador.presentation.views.RestauranteView;
import com.restaurant.simulador.business.services.RestauranteService;

public class MeseroThread extends Thread {
    private final Mesero mesero;
    private final OrdenMonitor ordenMonitor;
    private final ComidaMonitor comidaMonitor;
    private final ComensalMonitor comensalMonitor;
    private final RestauranteView view;
    private final RestauranteService service;

    public MeseroThread(Mesero mesero, OrdenMonitor ordenMonitor, ComidaMonitor comidaMonitor, ComensalMonitor comensalMonitor, RestauranteView view, RestauranteService service) {
        this.mesero = mesero;
        this.ordenMonitor = ordenMonitor;
        this.comidaMonitor = comidaMonitor;
        this.comensalMonitor = comensalMonitor;
        this.view = view;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Obtener comensal de la cola
                Comensal comensal = comensalMonitor.retirarComensal();
                System.out.println("Comensal " + comensal.getId() + " retirado de la cola de espera.");
                System.out.println("Mesero " + mesero.getId() + " está atendiendo al Comensal " + comensal.getId());

                // Mover mesero a la mesa del comensal
                Mesa mesa = comensal.getMesaAsignada();
                view.moverMesero(mesero.getId(), mesa.getPosX() + 30, mesa.getPosY());

                // Tomar orden
                Thread.sleep(1000); // Simula el tiempo de tomar la orden
                Orden orden = new Orden(comensal.getId(), comensal.getId());
                ordenMonitor.agregarOrden(orden);
                System.out.println("Orden " + orden.getId() + " añadida al buffer de órdenes.");
                view.actualizarComidaLista(orden.getId());

                // Esperar a que la comida esté lista
                Orden comidaLista = comidaMonitor.obtenerComida(orden.getId());
                System.out.println("Comida " + comidaLista.getId() + " retirada del buffer de comidas.");

                // Servir comida
                System.out.println("Mesero " + mesero.getId() + " está sirviendo la Orden " + comidaLista.getId());
                view.actualizarComidaServida(comidaLista.getId());

                // Notificar al comensal que su comida ha llegado
                synchronized (comensal) {
                    comensal.notify();
                }

                // Mover mesero de vuelta a su posición original
                view.moverMesero(mesero.getId(), view.getReceptionX() + 50, view.getReceptionY() + mesero.getId() * 15);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
