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

public class GameController {

    @FXML
    private Label lblEstado;

    @FXML
    private GridPane gridTablero;

    private Button[][] botonesTablero;
    private Tablero tableroActual;
    private minimax ia;

    private char simboloJugador1;
    private char simboloJugador2;
    private boolean turnoJugador1;
    private boolean esContraComputadora;
    private boolean configuracionInicioJugador1;

    public void iniciarPartida(char simboloJugador1, char simboloJugador2,
                               boolean empiezaJugador1, boolean contraComputadora) {
        this.simboloJugador1 = simboloJugador1;
        this.simboloJugador2 = simboloJugador2;
        this.turnoJugador1 = empiezaJugador1;
        this.configuracionInicioJugador1 = empiezaJugador1;
        this.esContraComputadora = contraComputadora;

        this.tableroActual = new Tablero();
        this.botonesTablero = new Button[3][3];
        if (contraComputadora) {
        this.ia = new minimax(simboloJugador2, simboloJugador1);
        } 
        else {
        this.ia = null;
    }

        construirTableroGrafico();
        actualizarEstado();

        // La computadora puede comenzar si fue seleccionada iniciador
        if (esContraComputadora && !turnoJugador1) {
            ejecutarTurnoComputadora();
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
        // En humano vs humano ambos turnos son controlados por clic y en humano vs computadora solo se permite el turno del humano.
        if (esContraComputadora && !turnoJugador1) {
            return;
        }

        if (tableroActual.getMatriz()[fila][columna] != Simbolo.VACIO) {
            return;
        }

        char simboloActual;
        if (turnoJugador1) {
        simboloActual = simboloJugador1;
        } 
        else {
        simboloActual = simboloJugador2;
        }

        tableroActual = tableroActual.realizarJugada(fila, columna, simboloActual);
        actualizarUI();

        if (verificarFinJuego()) {
            return;
        }

        turnoJugador1 = !turnoJugador1;
        actualizarEstado();

        if (esContraComputadora && !turnoJugador1) {
            ejecutarTurnoComputadora();
        }
    }

    private void ejecutarTurnoComputadora() {
        lblEstado.setText("Pensando la computadora...");

        Movimiento mejorMov = ia.getMejorMovimiento(tableroActual);

        if (mejorMov != null) {
            tableroActual = tableroActual.realizarJugada(
                    mejorMov.getFila(), mejorMov.getColumna(), simboloJugador2);
            actualizarUI();
        }

        if (!verificarFinJuego()) {
            turnoJugador1 = true;
            actualizarEstado();
        }
    }

    private void actualizarEstado() {
        char simbolo;
        if (turnoJugador1) {
        simbolo = simboloJugador1;
        } 
        else {
        simbolo = simboloJugador2;
        }

        if (esContraComputadora) {
            if (turnoJugador1) {
            lblEstado.setText("Turno de: Humano (" + simbolo + ")");
            } 
            else {
            lblEstado.setText("Pensando la computadora...");
            }
        } 
        else {
        lblEstado.setText("Turno del Jugador " + simbolo);
        }
    }

    private void actualizarUI() {
        char[][] matriz = tableroActual.getMatriz();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char val = matriz[i][j];
                botonesTablero[i][j].setText(
                        val == Simbolo.VACIO ? "" : String.valueOf(val));
            }
        }
    }

    private boolean verificarFinJuego() {
        char ganador = tableroActual.verificarGanador();

        if (ganador != Simbolo.VACIO) {
            String mensaje;

            if (esContraComputadora) {
                if (ganador == simboloJugador1) {
                 mensaje = "¡Has Ganado!";
                } 
                else {
                mensaje = "¡Ha Ganado la Computadora!";
                }
            } 
            else {
            mensaje = "¡Ha Ganado el Jugador " + ganador + "!";
            }

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
        iniciarPartida(simboloJugador1, simboloJugador2,
                configuracionInicioJugador1, esContraComputadora);
    }

    @FXML
    private void handleVolverMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/proyecto/primary.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 380, 400));
            stage.setTitle("Tres en Raya - Configuración");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista principal: " + e.getMessage());
        }
    }
}
