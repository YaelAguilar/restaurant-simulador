package com.restaurant.simulador.business.services;

import java.util.concurrent.atomic.AtomicInteger;

public class OrdenService {
    private static final AtomicInteger ordenIdGenerator = new AtomicInteger(1);

    public static int getNextOrdenId() {
        return ordenIdGenerator.getAndIncrement();
    }

    // Métodos adicionales para gestionar órdenes si es necesario
}
