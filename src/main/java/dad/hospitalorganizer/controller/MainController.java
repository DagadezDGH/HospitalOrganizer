package dad.hospitalorganizer.controller;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MainController implements Initializable {
	
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

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
    @FXML
    void onClickEntradas(ActionEvent event) {

    }

    @FXML
    void onClickInventario(ActionEvent event) {

    }

    @FXML
    void onClickPacientes(ActionEvent event) {

    }

    @FXML
    void onClickSalidas(ActionEvent event) {

    }
	
	public GridPane getView() {
		return view;
	}

}
