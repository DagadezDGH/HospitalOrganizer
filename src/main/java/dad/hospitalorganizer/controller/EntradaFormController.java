package dad.hospitalorganizer.controller;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Articulo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EntradaFormController implements Initializable {
	
	@FXML
	private GridPane view;

	@FXML
	private ComboBox<?> proveedorBox;

	@FXML
	private ComboBox<Articulo> articulosBox;

	@FXML
	private TextField cantidadText;

	@FXML
	private TableView<?> tablaEntradaArticulo;

	@FXML
	private TableColumn<?, ?> IdColumn;

	@FXML
	private TableColumn<?, ?> articuloColumn;

	@FXML
	private TableColumn<?, ?> entradaColumn;

	@FXML
	private TableColumn<?, ?> cantidadColumn;

	@FXML
	private TableColumn<?, ?> caducidadColumn;

	@FXML
	private Button crearButton;
    
    @FXML
    private Button volverButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public EntradaFormController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasFormView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@FXML
	void onClickCrear(ActionEvent event) {
	}
	public GridPane getView() {
		return view;
	}
    @FXML
    void onVolverAction(ActionEvent event) {
    	App.goToMain();
    }
}
