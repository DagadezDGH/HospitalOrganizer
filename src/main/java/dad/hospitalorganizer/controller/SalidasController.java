package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.hospitalorganizer.main.App;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class SalidasController implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private ImageView iconoSalidas;

	@FXML
	private Button verSalidaButton;

	@FXML
	private ImageView iconoSalidasVer;

	@FXML
	private Button nuevoSalidaButton;

	@FXML
	private ImageView iconoSalidasNuevo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
    /**
     * Genera la interfaz apartir del fxml
     */
	public SalidasController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidasView.fxml"));
		loader.setController(this);
		loader.load();
	}
    /**
     * Viajamos a la vista de Nueva Salida
     */
	@FXML
	void onClickNuevoSalida(ActionEvent event) {
		App.goToSalidasForm();
	}
    /**
     * Viajamos a la vista de Ver Salida
     */
	@FXML
	void onClickVerSalida(ActionEvent event) {
		App.goToSalidasVer();
	}
    /**
     * Devuelve la vista
     */
	public GridPane getView() {
		return view;
	}

}
