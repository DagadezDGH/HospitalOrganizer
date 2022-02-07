package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class InventarioController implements Initializable{
	
	@FXML
    private GridPane view;

    @FXML
    private ImageView iconoInventario;

    @FXML
    private TableView<?> tablaArticulos;

    @FXML
    private TableColumn<?, ?> articuloColumn;

    @FXML
    private TableColumn<?, ?> nombreColumn;

    @FXML
    private TableColumn<?, ?> descripcionColumn;

    @FXML
    private TableColumn<?, ?> ubicacionColumn;

    @FXML
    private TableColumn<?, ?> cantidadColumn;

    @FXML
    private Button crearButton;

    @FXML
    private Button modificarButton;

    @FXML
    private Button eliminarButton;
  	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public InventarioController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InventarioView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
    @FXML
    void onClickCrear(ActionEvent event) {

    }

    @FXML
    void onClickEliminar(ActionEvent event) {

    }

    @FXML
    void onClickModificar(ActionEvent event) {

    }
	public GridPane getView() {
		return view;
	}
}
