package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {
	
	    @FXML
	    private GridPane view;

	    @FXML
	    private ImageView pacientesIcono;

	    @FXML
	    private ImageView inventarioIcono;

	    @FXML
	    private ImageView entradasIcono;

	    @FXML
	    private VBox Infobox;

	    @FXML
	    private Label nombrelbl;


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
    void onClickEntradas(MouseEvent event) {

    }

    @FXML
    void onClickInventario(MouseEvent event) {

    }

    @FXML
    void onClickPacientes(MouseEvent event) {

    }
	public GridPane getView() {
		return view;
	}

}
