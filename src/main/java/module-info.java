module com.example.restaurantsimulador {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.restaurantsimulador to javafx.fxml;
    exports com.example.restaurantsimulador;
}