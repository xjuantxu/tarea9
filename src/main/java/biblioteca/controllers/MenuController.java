package biblioteca.controllers;

import biblioteca.controlador.Controlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void cambiarVista(ActionEvent event, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/biblioteca/" + fxml)
            );

            Scene scene = new Scene(loader.load());

            Object controller = loader.getController();

            if (controller instanceof LibrosController lc) {
                lc.setControlador(controlador);
            } else if (controller instanceof UsuariosController uc) {
                uc.setControlador(controlador);
            } else if (controller instanceof PrestamosController pc) {
                pc.setControlador(controlador);
            }

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLibrosClick(ActionEvent event) {
        cambiarVista(event, "libros-view.fxml");
    }

    public void onUsuariosClick(ActionEvent event) {
        cambiarVista(event, "usuarios-view.fxml");
    }

    public void onPrestamosClick(ActionEvent event) {
        cambiarVista(event, "prestamos-view.fxml");
    }

    public void onSalirClick(ActionEvent event) {

        try {
            if (controlador != null) {
                controlador.terminar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mostrarInfo("Conexión cerrada. Cerrando aplicación.");

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                .getScene()
                .getWindow();

        stage.close();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
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
}