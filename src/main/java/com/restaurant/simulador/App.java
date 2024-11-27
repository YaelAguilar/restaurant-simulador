package com.restaurant.simulador;

import com.restaurant.simulador.business.services.RestauranteService;
import com.restaurant.simulador.presentation.views.RestauranteView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear instancia de RestauranteService
        int capacidadMesas = 40; // Ajusta según necesidad
        int cantidadMeseros = 4; // Ajusta según necesidad
        int cantidadCocineros = 6; // Ajusta según necesidad

        // Inicializar RestauranteService
        RestauranteService restauranteService = new RestauranteService(capacidadMesas, cantidadMeseros, cantidadCocineros);

        // Obtener la vista desde RestauranteService
        RestauranteView view = restauranteService.getView();

        // Configurar la escena con la vista
        Scene scene = new Scene(view.getRoot());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulador de Restaurante");
        primaryStage.show();

        // Iniciar la simulación
        restauranteService.iniciarSimulacion();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
