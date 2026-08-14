package com.mycompany.proyecto.controlador;

import com.mycompany.proyecto.modelo.Simbolo;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private ComboBox<String> cmbModo;

    @FXML
    private ComboBox<String> cmbSimbolo;

    @FXML
    private ComboBox<String> cmbIniciador;

    @FXML
    public void initialize() {
        cmbModo.getItems().addAll("Humano vs Computadora", "Humano vs Humano");
        cmbModo.setValue("Humano vs Computadora");

        cmbSimbolo.getItems().addAll("X", "O");
        cmbSimbolo.setValue("X");

        actualizarOpcionesInicio();
        cmbModo.setOnAction(e -> actualizarOpcionesInicio());
    }

    private void actualizarOpcionesInicio() {
        cmbIniciador.getItems().clear();

        if ("Humano vs Humano".equals(cmbModo.getValue())) {
            cmbIniciador.getItems().addAll("Jugador X", "Jugador O");
            cmbIniciador.setValue("Jugador X");
        } else {
            cmbIniciador.getItems().addAll("Humano", "Computadora");
            cmbIniciador.setValue("Humano");
        }
    }

    @FXML
    private void handleIniciarJuego(ActionEvent event) throws IOException {
        boolean vsHumano = "Humano vs Humano".equals(cmbModo.getValue());
        char simboloHumano = cmbSimbolo.getValue().charAt(0);
        char simboloOponente = (simboloHumano == Simbolo.X) ? Simbolo.O : Simbolo.X;

        boolean empiezaJugador1;
        if (vsHumano) {
            empiezaJugador1 = "Jugador X".equals(cmbIniciador.getValue());
        } else {
            empiezaJugador1 = "Humano".equals(cmbIniciador.getValue());
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/proyecto/game.fxml"));
        Parent root = loader.load();

        GameController gameController = loader.getController();
        gameController.iniciarPartida(simboloHumano, simboloOponente, empiezaJugador1, !vsHumano);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 400, 500));
        stage.setTitle("Tres en Raya - En juego");
        stage.show();
    }
}
