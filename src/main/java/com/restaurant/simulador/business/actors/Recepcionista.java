package com.restaurant.simulador.business.actors;

import com.restaurant.simulador.business.models.Mesa;

public class Recepcionista {

    public void asignarMesa(Comensal comensal, Mesa mesa) {
        comensal.setMesaAsignada(mesa);
    }
}
