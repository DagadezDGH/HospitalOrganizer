package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.hospitalorganizer.main.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class EntradaController implements Initializable{
	
		@FXML
		private GridPane view;
		
	    @FXML
	    private ImageView iconoEntradas;

	    @FXML
	    private Button nuevoPedidoButton;

	    @FXML
	    private ImageView iconoEntradasNuevo;

	    @FXML
	    private Button verPedidoButton;

	    @FXML
	    private ImageView iconoEntradasVer;

	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
	    
	    public EntradaController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasView.fxml"));
			loader.setController(this);
			loader.load();
		}

		public GridPane getView() {
			
			return view;
		}
	    
	    
	    @FXML
	    void onClickNuevoPedido(ActionEvent event) {
	    	App.goToEntradaForm();
	    }

	    @FXML
	    void onClickVerPedido(ActionEvent event) {
	    	App.goToEntradaVer();
	    }
}