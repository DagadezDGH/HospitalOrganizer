package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EntradaFormController implements Initializable{
	
	  	@FXML
	    private VBox view;

	    @FXML
	    private ComboBox<?> proveedorBox;

	    @FXML
	    private ComboBox<?> articulosBox;

	    @FXML
	    private TextField cantidadText;
	    
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
	    private TableColumn<?, ?> cantidad1;

	    @FXML
	    private Button crearButton;


		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
		public EntradaFormController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasFormView.fxml"));
			loader.setController(this);
			loader.load();

			

		}

		public VBox getView() {
			return view;
		}
		

	    @FXML
	    void onClickCrear(ActionEvent event) {

	    }
}
