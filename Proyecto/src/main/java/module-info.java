module com.mycompany.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.mycompany.proyecto.controlador to javafx.fxml;
    exports com.mycompany.proyecto;
}
