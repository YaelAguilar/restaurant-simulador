package com.restaurant.simulador;

import com.restaurant.simulador.business.services.RestauranteService;
import com.restaurant.simulador.presentation.views.RestauranteView;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import static com.almasb.fxgl.dsl.FXGL.*;

public class App extends GameApplication {

    private RestauranteService restauranteService;
    private RestauranteView restauranteView;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Simulador de Restaurante");
        settings.setWidth(1000);
        settings.setHeight(600);
        settings.setVersion("1.0");
        settings.setMainMenuEnabled(false);
    }

    @Override
    protected void initGame() {
        // Primero inicializamos la vista
        restauranteView = new RestauranteView();
        addUINode(restauranteView.getRoot());

        // Luego inicializamos el servicio con la vista ya creada
        int capacidad = 10;
        int cantidadMeseros = (int) Math.ceil(capacidad * 0.10);
        int cantidadCocineros = (int) Math.ceil(capacidad * 0.15);

        restauranteService = new RestauranteService(capacidad, cantidadMeseros, cantidadCocineros, restauranteView);
        restauranteService.iniciarSimulacion();
    }

    @Override
    protected void onUpdate(double tpf) {
        // Implementación de actualizaciones periódicas si es necesario
    }

    public void cleanup() {
        if (restauranteService != null) {
            restauranteService.finalizarSimulacion();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}