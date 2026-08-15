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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class PrimaryController {
@FXML
    private ToggleButton btnX, btnO;
    
    @FXML 
    private ToggleGroup grupoSimbolo;
    
    @FXML
    private ToggleButton btnJvsM, btnJvsJ;
    
    @FXML
    private ToggleGroup grupoModo;

    //@FXML
   // private ComboBox<String> cmbIniciador;
    
    private char getSimboloSeleccionado(){
        return grupoSimbolo.getSelectedToggle()== btnX ? Simbolo.X : Simbolo.O;
    }
    
    @FXML
    private void handleIniciar(ActionEvent event) throws IOException {
        char simbolo= getSimboloSeleccionado();
        boolean vsHumano = btnJvsJ.isSelected();
 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/proyecto/Start.fxml"));
        Parent root = loader.load();

        StartController startController = loader.getController();
        startController.configurar(simbolo, vsHumano);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 400, 500));
        stage.setTitle("Tres en Raya - En juego");
        stage.show();
    }
}
