package com.restaurant.simulador.business.actors;

import com.restaurant.simulador.business.actors.Comensal;
import com.restaurant.simulador.business.models.Mesa;
import com.restaurant.simulador.concurrency.monitors.MesaMonitor;

public class Recepcionista {
    public Mesa registrarComensal(Comensal comensal, MesaMonitor mesaMonitor) throws InterruptedException {
        System.out.println("Recepcionista está registrando al Comensal " + comensal.getId());
        Mesa mesa = mesaMonitor.asignarMesa(comensal);
        asignarMesa(mesa);
        return mesa;
    }

    public void asignarMesa(Mesa mesa) {
        System.out.println("Recepcionista ha asignado la Mesa " + mesa.getNumero());
    }
}
