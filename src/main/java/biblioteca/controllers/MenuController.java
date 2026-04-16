package biblioteca.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    private void cambiarVista(ActionEvent event, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/biblioteca/" + fxml)
            );

            Scene scene = new Scene(loader.load());

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
        System.exit(0);
    }

    private void cambiarVista(String fxml) {
        System.out.println("Cargar vista: " + fxml);
        // luego metemos aqui el cambio real
    }
}