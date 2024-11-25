package com.restaurant.simulador.business.actors;

import com.restaurant.simulador.business.models.Comida;
import com.restaurant.simulador.business.models.Orden;
import com.restaurant.simulador.business.services.CocinaService;
import com.restaurant.simulador.business.services.ComidaService;

public class Cocinero {
    private final int id;

    public Cocinero(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Comida prepararComida(Orden orden) {
        System.out.println("Cocinero " + id + " está preparando la Orden " + orden.getId());
        // Simular tiempo de preparación
        Comida comida = new Comida(ComidaService.getNextComidaId(), orden, CocinaService.getTiempoPreparacion());
        comida.preparar();
        orden.cambiarEstado(com.restaurant.simulador.business.models.EstadoOrden.LISTO);
        System.out.println("Cocinero " + id + " ha terminado de preparar la Orden " + orden.getId());
        return comida;
    }
}
