package dad.hospitalorganizer.controller;

import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Articulo;
import dad.hospitalorganizer.models.EntradaArticulo;
import dad.hospitalorganizer.models.Proveedor;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EntradaFormController implements Initializable {
	private Conecciones Database;
	private ObjectProperty<Articulo> articulo = new SimpleObjectProperty<>();
	private ListProperty<String> articulosProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<Proveedor> proveedorProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
   
	
	@FXML
    private Button anadirButton;

	@FXML
	private GridPane view;

	@FXML
	private ComboBox<Proveedor> proveedorBox;

	@FXML
	private ComboBox<String> articulosBox;

	@FXML
	private TextField cantidadText;

	@FXML
	private TableView<EntradaArticulo> tablaEntradaArticulo;

	@FXML
	private TableColumn<EntradaArticulo, Integer> idColumn;

	@FXML
	private TableColumn<EntradaArticulo, Integer> proveedorColumn;

	@FXML
	private TableColumn<EntradaArticulo, Date> fechaColumn;

	@FXML
	private Button crearButton;
    
    @FXML
    private Button volverButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Database = new Conecciones();
		
		idColumn.setCellValueFactory(null);
		proveedorColumn.setCellValueFactory(null);
		fechaColumn.setCellValueFactory(null);
		
		
		
		
		articulosBox.itemsProperty().bind(articulosProperty);
		proveedorBox.itemsProperty().bind(proveedorProperty);
		
//		articulo.addListener((o, ov, nv) -> onArticuloChanged(o, ov, nv));
		try {
			mostrarArticulos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public EntradaFormController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasFormView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
    @FXML
    void onClickAnadir(ActionEvent event) {

    }
    
	@FXML
	void onClickCrear(ActionEvent event) {
		
	}
	public GridPane getView() {
		return view;
	}
    @FXML
    void onVolverAction(ActionEvent event) {
    	App.goToMain();
    }
	public void mostrarArticulos() throws SQLException {
		PreparedStatement lista = Database.conexion.prepareStatement("select * from Articulos");
		ResultSet resultado = lista.executeQuery();
		while (resultado.next()) {
			 
			articulosProperty.add(resultado.getString("nombre")
					);
		}
	}
}
