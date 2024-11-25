package com.restaurant.simulador.business.services;

import com.restaurant.simulador.business.actors.Cocinero;
import com.restaurant.simulador.business.actors.Comensal;
import com.restaurant.simulador.business.actors.Mesero;
import com.restaurant.simulador.business.actors.Recepcionista;
import com.restaurant.simulador.concurrency.monitors.ComidaMonitor;
import com.restaurant.simulador.concurrency.monitors.ComensalMonitor;
import com.restaurant.simulador.concurrency.monitors.MesaMonitor;
import com.restaurant.simulador.concurrency.monitors.OrdenMonitor;
import com.restaurant.simulador.concurrency.threads.CocineroThread;
import com.restaurant.simulador.concurrency.threads.ComensalThread;
import com.restaurant.simulador.concurrency.threads.MeseroThread;
import com.restaurant.simulador.presentation.views.RestauranteView;
import com.restaurant.simulador.business.utils.PoissonDistribution;

import java.util.ArrayList;
import java.util.List;

public class RestauranteService {
    private final MesaMonitor mesaMonitor;
    private final OrdenMonitor ordenMonitor;
    private final ComidaMonitor comidaMonitor;
    private final ComensalMonitor comensalMonitor;
    private final Recepcionista recepcionista;
    private final List<Mesero> meseros;
    private final List<Cocinero> cocineros;
    private final List<Thread> threads;
    private final RestauranteView view;
    private volatile boolean running;
    private int totalComensales = 0;
    private int ordenesProcesadas = 0;

    public RestauranteService(int capacidad, int cantidadMeseros, int cantidadCocineros) {
        this.mesaMonitor = new MesaMonitor(capacidad);
        this.ordenMonitor = new OrdenMonitor();
        this.comidaMonitor = new ComidaMonitor();
        this.comensalMonitor = new ComensalMonitor();
        this.recepcionista = new Recepcionista();
        this.meseros = new ArrayList<>();
        this.cocineros = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.view = new RestauranteView(mesaMonitor.getMesas());
        this.running = false;

        for (int i = 1; i <= cantidadMeseros; i++) {
            meseros.add(new Mesero(i));
        }

        for (int i = 1; i <= cantidadCocineros; i++) {
            cocineros.add(new Cocinero(i));
        }
    }

    public void iniciarSimulacion() {
        if (view == null) {
            throw new IllegalStateException("La vista no ha sido inicializada correctamente");
        }
        running = true;

        // Establecer el número total de cocineros en la vista
        view.setTotalCocineros(cocineros.size());

        // Añadir representaciones gráficas de meseros y cocineros
        for (Mesero mesero : meseros) {
            view.añadirMesero(mesero.getId());
        }

        for (Cocinero cocinero : cocineros) {
            view.añadirCocinero(cocinero.getId());
        }

        // Iniciar hilos de meseros
        for (Mesero mesero : meseros) {
            MeseroThread meseroThread = new MeseroThread(mesero, ordenMonitor, comidaMonitor, comensalMonitor, view, this);
            threads.add(meseroThread);
            meseroThread.start();
        }

        // Iniciar hilos de cocineros
        for (Cocinero cocinero : cocineros) {
            CocineroThread cocineroThread = new CocineroThread(cocinero, ordenMonitor, comidaMonitor, view, this);
            threads.add(cocineroThread);
            cocineroThread.start();
        }

        // Iniciar generación de comensales
        Thread generadorComensales = new Thread(() -> {
            int comensalId = 1;
            while (running) {
                try {
                    // Generar llegada de comensal basada en distribución de Poisson
                    double lambda = 1.0; // tasa de llegada, ajustar según necesidad
                    double tiempoEntreLlegadas = PoissonDistribution.generarTiempoLlegada(lambda);
                    Thread.sleep((long) (tiempoEntreLlegadas * 1000));

                    Comensal comensal = new Comensal(comensalId++);
                    ComensalThread comensalThread = new ComensalThread(comensal, recepcionista, mesaMonitor, comensalMonitor, view, ordenMonitor);
                    threads.add(comensalThread);
                    comensalThread.start();

                    totalComensales++;
                    view.actualizarTotalComensales(totalComensales);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        generadorComensales.start();
        threads.add(generadorComensales);
    }

    public void finalizarSimulacion() {
        running = false;
        for (Thread thread : threads) {
            thread.interrupt();
        }
        System.out.println("Simulación finalizada.");
    }

    // Método para incrementar las órdenes procesadas desde CocineroThread
    public synchronized void incrementarOrdenesProcesadas() {
        ordenesProcesadas++;
        view.actualizarOrdenesProcesadas(ordenesProcesadas);
    }

    public RestauranteView getView() {
        return view;
    }
}
