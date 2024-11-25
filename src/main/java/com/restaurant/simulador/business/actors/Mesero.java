package com.restaurant.simulador.business.actors;

import com.restaurant.simulador.business.models.Orden;
import com.restaurant.simulador.concurrency.monitors.ComensalMonitor;
import com.restaurant.simulador.business.services.OrdenService;

public class Mesero {
    private final int id;

    public Mesero(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Orden atenderComensal(ComensalMonitor comensalMonitor) throws InterruptedException {
        Comensal comensal = comensalMonitor.getComensal();
        System.out.println("Mesero " + id + " está atendiendo al Comensal " + comensal.getId());
        // Crear una orden para el comensal
        Orden orden = new Orden(OrdenService.getNextOrdenId(), comensal);
        comensal.setOrden(orden);
        return orden;
    }

    public void servirComida(int ordenId) {
        System.out.println("Mesero " + id + " está sirviendo la Orden " + ordenId);
    }
}
