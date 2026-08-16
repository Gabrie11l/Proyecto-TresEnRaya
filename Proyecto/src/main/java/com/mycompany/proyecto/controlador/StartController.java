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
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author Usr
 */
public class StartController {
    @FXML
    private ToggleButton btn1, btn2;
    
    @FXML
    private ToggleGroup grupoBotones;
    
    @FXML
    private Button btnIniciar;
    
    private char simboloElegido;
    private boolean vsHumano;
    public void configurar(char simbolo, boolean vsHumano ){
        this.simboloElegido = simbolo;
        this.vsHumano = vsHumano;
        
        if(vsHumano){
            btn1.setText("Jugador 1");
            btn2.setText("Jugador 2");
        }else{
            btn1.setText("Jugador");
            btn2.setText("Maquina");
        }
        btn1.setSelected(true);
    }
    
    @FXML
    public void initialize(){
        btnIniciar.disableProperty().bind(grupoBotones.selectedToggleProperty().isNull());
    }
    
    @FXML
    private void handleIniciarJuego(ActionEvent event) throws IOException {
        boolean jugador1 = btn1.isSelected();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/proyecto/game.fxml"));
        Parent root = loader.load();
        GameController gameController = loader.getController();
        
        char simboloOponente = (simboloElegido == Simbolo.X) ? Simbolo.O: Simbolo.X;
        gameController.iniciarPartida(simboloElegido, simboloOponente, jugador1, !vsHumano);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,400,500));
        stage.show();
        
    }
}
