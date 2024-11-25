module com.example.restaurantsimulador {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.restaurant.simulador to javafx.fxml;
    exports com.restaurant.simulador;
}