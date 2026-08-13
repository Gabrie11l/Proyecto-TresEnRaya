/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.controlador;
import com.mycompany.proyecto.modelo.minimax;
import com.mycompany.proyecto.modelo.Movimiento;
import com.mycompany.proyecto.modelo.Simbolo;
import com.mycompany.proyecto.modelo.Tablero;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 *
 * @author Jared
 */
public class GameController {

    @FXML
    private Label lblEstado;

    @FXML
    private GridPane gridTablero;

    private Button[][] botonesTablero;
    private Tablero tableroActual;
    private minimax ia;
    private char simboloHumano;
    private char simboloIA;
    private boolean esTurnoHumano;
    private boolean configuracionInicioHumano;

    public void iniciarPartida(char simboloHumano, char simboloIA, boolean empiezaHumano) {
    this.simboloHumano = simboloHumano;
    this.simboloIA = simboloIA;
    this.configuracionInicioHumano = empiezaHumano; // Guarda la preferencia fija
    this.esTurnoHumano = empiezaHumano;
    this.tableroActual = new Tablero();
    this.ia = new minimax(simboloIA, simboloHumano);
    this.botonesTablero = new Button[3][3];

    construirTableroGrafico();

    if (!esTurnoHumano) {
        lblEstado.setText("Pensando la computadora...");
        ejecutarTurnoIA();
    } else {
        lblEstado.setText("Turno de: Humano (" + simboloHumano + ")");
    }
}

    private void construirTableroGrafico() {
        gridTablero.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button("");
                btn.setPrefSize(90, 90);
                btn.setFont(new Font("System Bold", 28));

                final int fila = i;
                final int columna = j;

                btn.setOnAction(e -> handleClicCasilla(fila, columna));

                botonesTablero[i][j] = btn;
                gridTablero.add(btn, j, i);
            }
        }
    }

    private void handleClicCasilla(int fila, int columna) {
        if (!esTurnoHumano) return;
        if (tableroActual.getMatriz()[fila][columna] != Simbolo.VACIO) return;

        tableroActual = tableroActual.realizarJugada(fila, columna, simboloHumano);
        actualizarUI();

        if (verificarFinJuego()) return;

        esTurnoHumano = false;
        lblEstado.setText("Pensando la computadora...");
        ejecutarTurnoIA();
    }

    private void ejecutarTurnoIA() {
        Movimiento mejorMov = ia.getMejorMovimiento(tableroActual);

        if (mejorMov != null) {
            tableroActual = tableroActual.realizarJugada(mejorMov.getFila(), mejorMov.getColumna(), simboloIA);
            actualizarUI();
        }

        if (!verificarFinJuego()) {
            esTurnoHumano = true;
            lblEstado.setText("Turno de: Humano (" + simboloHumano + ")");
        }
    }

    private void actualizarUI() {
        char[][] matriz = tableroActual.getMatriz();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char val = matriz[i][j];
                botonesTablero[i][j].setText(val == Simbolo.VACIO ? "" : String.valueOf(val));
            }
        }
    }

    private boolean verificarFinJuego() {
        char ganador = tableroActual.verificarGanador();

        if (ganador != Simbolo.VACIO) {
            String mensaje = (ganador == simboloHumano) ? "¡Has Ganado!" : "¡Ha Ganado la Computadora!";
            lblEstado.setText(mensaje);
            mostrarAlerta("Fin del Juego", mensaje);
            bloquearTablero();
            return true;
        }

        if (tableroActual.estaLleno()) {
            lblEstado.setText("¡Empate!");
            mostrarAlerta("Fin del Juego", "¡Es un Empate!");
            bloquearTablero();
            return true;
        }

        return false;
    }

    private void bloquearTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botonesTablero[i][j].setDisable(true);
            }
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void handleReiniciar() {
        iniciarPartida(simboloHumano, simboloIA, configuracionInicioHumano);
    }

    @FXML
    private void handleVolverMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/proyecto/primary.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 380, 320));
            stage.setTitle("Tres en Raya - Configuración");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista principal: " + e.getMessage());
        }
    }
}