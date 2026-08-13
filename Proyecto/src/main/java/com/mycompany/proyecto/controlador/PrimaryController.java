/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author Jared
 */
public class PrimaryController {

    @FXML
    private ComboBox<String> cmbSimbolo;

    @FXML
    private ComboBox<String> cmbIniciador;

    @FXML
    public void initialize() {
        // Cargar opciones en los ComboBox
        cmbSimbolo.getItems().addAll("X", "O");
        cmbSimbolo.setValue("X");

        cmbIniciador.getItems().addAll("Humano", "Laptop");
        cmbIniciador.setValue("Humano");
    }

    @FXML
    private void handleIniciarJuego(ActionEvent event) throws IOException {
        char simboloHumano = cmbSimbolo.getValue().charAt(0);
        char simboloIA = (simboloHumano == Simbolo.X) ? Simbolo.O : Simbolo.X;
        boolean empiezaHumano = cmbIniciador.getValue().equals("Humano");

        // Cargar la vista del juego
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/proyecto/game.fxml"));
        Parent root = loader.load();

        // Transferir la configuración al GameController
        GameController gameController = loader.getController();
        gameController.iniciarPartida(simboloHumano, simboloIA, empiezaHumano);

        // Cambiar la escena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 400, 450));
        stage.setTitle("Tres en Raya - En juego");
        stage.show();
    }
}