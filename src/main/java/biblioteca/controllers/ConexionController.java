package biblioteca.controllers;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.Modelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;

public class ConexionController {

    private Controlador controlador;

    // Se ejecuta al cargar la vista
    @FXML
    public void initialize() {
        Modelo modelo = new Modelo();
        controlador = new Controlador(modelo);
    }

    @FXML
    protected void onConectarButtonClick(ActionEvent event) {
        try {
            controlador.comenzar();
            mostrarExito("Conexion realizada correctamente");
            cambiarVista(event, "menu-view.fxml");

        } catch (Exception e) {
            mostrarError("Error al conectar con la base de datos:\n" + e.getMessage());
        }
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Conexion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cambiarVista(ActionEvent event, String fxml) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/biblioteca/" + fxml)
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo cargar la vista");
        }
    }
}