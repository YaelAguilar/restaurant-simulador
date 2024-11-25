package com.restaurant.simulador.data;

import com.restaurant.simulador.business.models.Comida;
import com.restaurant.simulador.business.models.Orden;

import java.util.ArrayList;
import java.util.List;

public class Repositories {
    private final List<Orden> ordenes;
    private final List<Comida> comidas;

    public Repositories() {
        ordenes = new ArrayList<>();
        comidas = new ArrayList<>();
    }

    public synchronized void guardarOrden(Orden orden) {
        ordenes.add(orden);
    }

    public synchronized void guardarComida(Comida comida) {
        comidas.add(comida);
    }

    public synchronized List<Orden> obtenerOrdenes() {
        return new ArrayList<>(ordenes);
    }

    public synchronized List<Comida> obtenerComidas() {
        return new ArrayList<>(comidas);
    }
}
