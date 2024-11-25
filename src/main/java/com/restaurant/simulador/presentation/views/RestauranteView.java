package com.restaurant.simulador.presentation.views;

import com.restaurant.simulador.business.models.EstadoMesa;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

public class RestauranteView {
    private final VBox root;
    private final GridPane mesasGrid;
    private final VBox comensalesBox;
    private final VBox ordenesBox;
    private final Map<Integer, Circle> mesasVisuales;
    private final Map<Integer, Label> comensalesEstados;
    private final Map<Integer, Label> ordenesEstados;
    private final HBox statusBar;
    private final Label totalComensalesLabel;
    private final Label ordenesProcesadasLabel;

    public RestauranteView() {
        root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Sección de Mesas
        Label mesasLabel = new Label("Mesas");
        mesasLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        mesasGrid = new GridPane();
        mesasGrid.setHgap(20);
        mesasGrid.setVgap(20);
        mesasGrid.setAlignment(Pos.CENTER);
        mesasGrid.setPadding(new Insets(10));

        mesasVisuales = new HashMap<>();
        inicializarMesas();

        // Sección de Comensales
        Label comensalesLabel = new Label("Comensales");
        comensalesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        comensalesBox = new VBox(5);
        comensalesBox.setAlignment(Pos.TOP_LEFT);
        comensalesBox.setPrefWidth(300);

        comensalesEstados = new HashMap<>();

        // Sección de Órdenes
        Label ordenesLabel = new Label("Órdenes");
        ordenesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        ordenesBox = new VBox(5);
        ordenesBox.setAlignment(Pos.TOP_LEFT);
        ordenesBox.setPrefWidth(300);

        ordenesEstados = new HashMap<>();

        // Sección de Estado
        statusBar = new HBox(20);
        statusBar.setPadding(new Insets(10));
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #CCCCCC;");

        totalComensalesLabel = new Label("Total Comensales: 0");
        ordenesProcesadasLabel = new Label("Órdenes Procesadas: 0");

        statusBar.getChildren().addAll(totalComensalesLabel, ordenesProcesadasLabel);

        // Agregar todas las secciones al root
        HBox mainContent = new HBox(50, mesasGrid, comensalesBox, ordenesBox);
        mainContent.setAlignment(Pos.TOP_CENTER);

        root.getChildren().addAll(mesasLabel, mainContent, statusBar);
    }

    private void inicializarMesas() {
        // Supongamos que hay 10 mesas, organizadas en 2 filas de 5 mesas cada una
        int totalMesas = 10;
        int filas = 2;
        int columnas = 5;

        for (int i = 1; i <= totalMesas; i++) {
            int fila = (i - 1) / columnas;
            int columna = (i - 1) % columnas;

            Circle mesa = new Circle(30, Color.GREEN);
            mesa.setStroke(Color.BLACK);
            Label label = new Label("Mesa " + i + ": Libre");
            label.setStyle("-fx-font-size: 12px;");

            VBox mesaBox = new VBox(5, mesa, label);
            mesaBox.setAlignment(Pos.CENTER);

            mesasGrid.add(mesaBox, columna, fila);
            mesasVisuales.put(i, mesa);
        }
    }

    public VBox getRoot() {
        return root;
    }

    public void actualizarEstadoMesa(int numeroMesa, EstadoMesa estado) {
        Platform.runLater(() -> {
            Circle mesa = mesasVisuales.get(numeroMesa);
            Label label = (Label) ((VBox) mesasGrid.getChildren().get(numeroMesa - 1)).getChildren().get(1);
            if (mesa != null && label != null) {
                if (estado == EstadoMesa.LIBRE) {
                    mesa.setFill(Color.GREEN);
                    label.setText("Mesa " + numeroMesa + ": Libre");
                } else {
                    mesa.setFill(Color.RED);
                    label.setText("Mesa " + numeroMesa + ": Ocupada");
                }
            }
        });
    }

    public void actualizarComensalEstado(int comensalId, String estado) {
        Platform.runLater(() -> {
            Label label;
            if (comensalesEstados.containsKey(comensalId)) {
                label = comensalesEstados.get(comensalId);
                label.setText("Comensal " + comensalId + ": " + estado);
            } else {
                label = new Label("Comensal " + comensalId + ": " + estado);
                comensalesBox.getChildren().add(label);
                comensalesEstados.put(comensalId, label);
            }
        });
    }

    public void actualizarComidaServida(int ordenId) {
        Platform.runLater(() -> {
            Label label;
            if (ordenesEstados.containsKey(ordenId)) {
                label = ordenesEstados.get(ordenId);
                label.setText("Orden " + ordenId + ": Servida");
            } else {
                label = new Label("Orden " + ordenId + ": Servida");
                ordenesBox.getChildren().add(label);
                ordenesEstados.put(ordenId, label);
            }
        });
    }

    public void actualizarComidaLista(int ordenId) {
        Platform.runLater(() -> {
            Label label;
            if (ordenesEstados.containsKey(ordenId)) {
                label = ordenesEstados.get(ordenId);
                label.setText("Orden " + ordenId + ": Lista");
            } else {
                label = new Label("Orden " + ordenId + ": Lista");
                ordenesBox.getChildren().add(label);
                ordenesEstados.put(ordenId, label);
            }
        });
    }

    // Métodos para actualizar estadísticas
    public void actualizarTotalComensales(int total) {
        Platform.runLater(() -> totalComensalesLabel.setText("Total Comensales: " + total));
    }

    public void actualizarOrdenesProcesadas(int total) {
        Platform.runLater(() -> ordenesProcesadasLabel.setText("Órdenes Procesadas: " + total));
    }
}
