package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SalidasController implements Initializable{
	

	    @FXML
	    private GridPane view;

	    @FXML
	    private ImageView iconoSalidas;

	    @FXML
	    private Button nuevoButton;

	    @FXML
	    private ImageView iconoSalidasNuevo;

	    @FXML
	    private Button verButton;

	    @FXML
	    private ImageView iconoSalidasVer;

	    @FXML
	    void onclickPedido(ActionEvent event) {

	    }

	    @FXML
	    void onclickPedidoVer(ActionEvent event) {

	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public SalidasController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();

		

	}

	public GridPane getView() {
		return view;
	}

}
