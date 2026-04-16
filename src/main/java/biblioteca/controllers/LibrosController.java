package biblioteca.controllers;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.Modelo;
import biblioteca.modelo.dominio.Libro;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import biblioteca.modelo.dominio.Audiolibro;

import java.util.List;

public class LibrosController {

    private Controlador controlador;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private TableColumn<Libro, String> colAutores;

    @FXML
    private TableColumn<Libro, Integer> colAnio;

    @FXML
    private TableColumn<Libro, String> colCategoria;

    @FXML
    private TableColumn<Libro, String> colFormato;

    @FXML
    private TableColumn<Libro, String> colDuracion;

    @FXML
    public void initialize() {

        // Inicializar controlador (temporal, luego lo mejoramos)
        Modelo modelo = new Modelo();
        controlador = new Controlador(modelo);
        controlador.comenzar();

        tablaLibros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaLibros.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Libro libro, boolean empty) {
                super.updateItem(libro, empty);

                if (libro == null || empty) {
                    setStyle("");
                } else if (libro instanceof Audiolibro) {
                    setStyle("-fx-background-color: #e8f5e9;");
                } else {
                    setStyle("");
                }
            }
        });

        // Configurar columnas
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colAutores.setCellValueFactory(cellData -> {
            Libro libro = cellData.getValue();

            StringBuilder autores = new StringBuilder();

            for (var autor : libro.getAutores()) {
                if (autor != null) {
                    if (!autores.isEmpty()) autores.append(", ");
                    autores.append(autor.getNombre()).append(" ").append(autor.getApellidos());
                }
            }

            return new javafx.beans.property.SimpleStringProperty(autores.toString());
        });
        colFormato.setCellValueFactory(cellData -> {
            Libro libro = cellData.getValue();

            if (libro instanceof Audiolibro audio) {
                return new SimpleStringProperty(audio.getFormato());
            }

            return new SimpleStringProperty("");
        });
        colDuracion.setCellValueFactory(cellData -> {
            Libro libro = cellData.getValue();

            if (libro instanceof Audiolibro audio) {

                long segundos = audio.getDuracion().getSeconds();
                long h = segundos / 3600;
                long m = (segundos % 3600) / 60;
                long s = segundos % 60;

                String tiempo = String.format("%02d:%02d:%02d", h, m, s);

                return new SimpleStringProperty(tiempo);
            }

            return new SimpleStringProperty("");
        });
        cargarLibros();
    }

    private void cargarLibros() {
        List<Libro> lista = controlador.listadoLibros();
        tablaLibros.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void onVolverClick(javafx.event.ActionEvent event) {
        // volvemos al menu
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/biblioteca/menu-view.fxml")
            );

            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());

            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}