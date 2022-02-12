package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.hospitalorganizer.main.App;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SalidasFormController implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private TableView<?> tableArticulos;

	@FXML
	private TableColumn<?, ?> codArticulo;

	@FXML
	private TableColumn<?, ?> nombre;

	@FXML
	private TableColumn<?, ?> descripcion;

	@FXML
	private TableColumn<?, ?> ubicacion;

	@FXML
	private TableColumn<?, ?> cantidad;

	@FXML
	private Button crearButton;

	@FXML
	private TextField lugarText;

	@FXML
	private TextField motivoText;

	@FXML
	private ComboBox<?> pacienteCombo;

	@FXML
	private ComboBox<?> articulosCombo;

	@FXML
	private TextField cantidadText;
    
	@FXML
    private Button volverButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public SalidasFormController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidasFormView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void onClickAnadir(ActionEvent event) {

	}

	@FXML
	void onClickCrear(ActionEvent event) {

	}
    @FXML
    void OnVolverAction(ActionEvent event) {
    	App.goToMain();
    }

}
