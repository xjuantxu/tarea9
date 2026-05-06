package biblioteca.controllers;

import biblioteca.modelo.dominio.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LibroController {

    @FXML private TextField txtIsbn;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAnio;
    @FXML private ComboBox<Categoria> cmbCategoria;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtFormato;
    @FXML private TextField txtDuracion;

    @FXML private TextField txtNombreAutor;
    @FXML private TextField txtApellidosAutor;
    @FXML private TextField txtNacionalidadAutor;
    @FXML private ListView<Autor> listaAutores;

    private List<Autor> autores = new ArrayList<>();

    @FXML
    public void initialize() {
        cmbCategoria.setItems(FXCollections.observableArrayList(Categoria.values()));
        cmbTipo.setItems(FXCollections.observableArrayList("Libro", "Audiolibro"));
        cmbTipo.setValue("Libro");
    }

    @FXML
    private void onAddAutor() {
        Autor autor = new Autor(
                txtNombreAutor.getText(),
                txtApellidosAutor.getText(),
                txtNacionalidadAutor.getText()
        );

        autores.add(autor);
        listaAutores.setItems(FXCollections.observableArrayList(autores));

        txtNombreAutor.clear();
        txtApellidosAutor.clear();
        txtNacionalidadAutor.clear();
    }

    public Libro getLibro() {

        String isbn = txtIsbn.getText();
        String titulo = txtTitulo.getText();
        int anio = Integer.parseInt(txtAnio.getText());
        Categoria categoria = cmbCategoria.getValue();

        Libro libro;

        if ("Audiolibro".equals(cmbTipo.getValue())) {

            long segundos = Long.parseLong(txtDuracion.getText());

            libro = new Audiolibro(
                    isbn,
                    titulo,
                    anio,
                    categoria,
                    Duration.ofSeconds(segundos),
                    txtFormato.getText()
            );

        } else {
            libro = new Libro(isbn, titulo, anio, categoria);
        }

        for (Autor a : autores) {
            libro.addAutor(a);
        }

        return libro;
    }
}