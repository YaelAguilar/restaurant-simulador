module com.restaurant.simulador {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires com.almasb.fxgl.all;

    opens com.restaurant.simulador to javafx.fxml;
    exports com.restaurant.simulador;
}
