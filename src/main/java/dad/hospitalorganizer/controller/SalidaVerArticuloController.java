package dad.hospitalorganizer.controller;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.tablaMostrar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class SalidaVerArticuloController implements Initializable{
	

	    @FXML
	    private GridPane view;

	    @FXML
	    private TableView<tablaMostrar> tablaSalidaArticulo;

	    @FXML
	    private TableColumn<tablaMostrar, String> codArticuloColumn;

	    @FXML
	    private TableColumn<tablaMostrar, String> nomArticuloColumn;

	    @FXML
	    private TableColumn<tablaMostrar, String> ubicacionColumn;

	    @FXML
	    private TableColumn<tablaMostrar, String> cantidadColumn;

	    @FXML
	    private Button volverButton;

	    @FXML
	    private Button backbtn;

	    

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
		public SalidaVerArticuloController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidaVerArticuloView.fxml"));
			loader.setController(this);
			loader.load();
		}
		
		public GridPane getView() {
			return view;
		}
		
		@FXML
	    void onBackAction(ActionEvent event) {
	    	App.goToSalidasVer();
	    }

	    @FXML
	    void onVolverAction(ActionEvent event) {
	    	App.goToMain();
	    }

	}


