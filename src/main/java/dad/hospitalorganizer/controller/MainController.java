package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import dad.hospitalorganizer.main.App;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class MainController implements Initializable {
	StringProperty bienvenido = new SimpleStringProperty();
    @FXML
    private GridPane view;

    @FXML
    private Button pacientesButton;

    @FXML
    private ImageView pacientesIcono;

    @FXML
    private Button inventarioButton;

    @FXML
    private ImageView inventarioIcono;

    @FXML
    private Button entradasButton;

    @FXML
    private ImageView entradasIcono;

    @FXML
    private Button salidasButton;

    @FXML
    private ImageView salidasIcono;

    @FXML
    private Label bienvenidoLabel;
    /**
     * Genera la interfaz apartir del fxml
     */
	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}
	/**
     * Inicializa la clase con sus bindeos, listeners, etc
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	bienvenido.bind(App.usuario.nombreProperty());
	bienvenidoLabel.textProperty().bind(bienvenido);
	}
    /**
     * Viajamos a la vista de entradas
     */
    @FXML
    void onClickEntradas(ActionEvent event) {
    	App.goToEntrada();
    	
    }
    /**
     * Viajamos a la vista de inventario
     */
    @FXML
    void onClickInventario(ActionEvent event) {
    	App.goToInventario();
    	
    }

    @FXML
    void onClickPacientes(ActionEvent event) {
 
    }
    /**
     * Viajamos a la vista Salidas
     */
    @FXML
    void onClickSalidas(ActionEvent event) {
    	App.goToSalidas();
    }
    /**
     * Devuelve la vista
     */
	public GridPane getView() {
		return view;
	}

}
