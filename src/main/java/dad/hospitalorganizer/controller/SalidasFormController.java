package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SalidasFormController implements Initializable{

	    @FXML
	    private VBox view;

	    @FXML
	    private TextField Lugar;

	    @FXML
	    private TextField motivo;

	    @FXML
	    private ComboBox<String> articulosBox;

	    @FXML
	    private TextField cantidad;
	    
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
	    private Button crear;
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
	    
	    public SalidasFormController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidasFormView.fxml"));
			loader.setController(this);
			loader.load();


		}

		public VBox getView() {
			return view;
		}

	    @FXML
	    void onClickAñadir(ActionEvent event) {

	    }

	    @FXML
	    void onClickCrear(ActionEvent event) {

	    }
	

}
