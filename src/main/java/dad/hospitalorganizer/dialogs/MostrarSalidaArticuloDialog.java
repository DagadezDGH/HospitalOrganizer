package dad.hospitalorganizer.dialogs;

/**
 * @author Carlos Javier Cosme Melian
 */

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Optional;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Articulo;
import dad.hospitalorganizer.models.Salida;
import dad.hospitalorganizer.models.tablaMostrar;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MostrarSalidaArticuloDialog extends Dialog<Articulo> {

	// Cargamos los elementos del FXML

	private ObservableList<tablaMostrar> listSalidaArticulo = FXCollections.observableArrayList();
	private ListProperty<tablaMostrar> listSalidaArticulos = new SimpleListProperty<tablaMostrar>(listSalidaArticulo);
	private Conecciones Database;
	    @FXML
	    private GridPane view;

	    @FXML
	    private TableView<tablaMostrar> tablaSalidaArticulos;

	    @FXML
	    private TableColumn<tablaMostrar, String> codArticuloColumn;

	    @FXML
	    private TableColumn<tablaMostrar, String> nomArticuloColumn;

	    @FXML
	    private TableColumn<tablaMostrar, String> ubicacionColumn;

	    @FXML
	    private TableColumn<tablaMostrar, String> cantidadColumn;


	    

	


	// Creamos los elementos del modelo

	private ButtonType  cancelButton;

	private Salida submited;

	ObservableList<String> hotelesList = FXCollections.observableArrayList(new ArrayList<String>());
	insertDialogModel model = new insertDialogModel();

	public MostrarSalidaArticuloDialog(Salida submited) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SalidaVerArticuloView.fxml"));
		fxmlLoader.setController(this);
		getDialogPane().setContent(fxmlLoader.load());
		
		Database = new Conecciones();
		this.submited = submited;
		
		nomArticuloColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getNomArticulo()));
		codArticuloColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCodArticulo()));
		ubicacionColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getLugar()));
		cantidadColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCantidad()));
		tablaSalidaArticulos.itemsProperty().bind(listSalidaArticulos);
		
		actualizar();
		setTitle("Mostrar articulos de la salida");
		setHeaderText("Muestra los datos:");
		setContentText("");

		cancelButton = new ButtonType("Volver", ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().addAll(cancelButton);

		

	}
	public void actualizar() {
		listSalidaArticulos.clear();
		
		try {
			PreparedStatement lista = Database.conexion.prepareStatement(""
					+ "SELECT S.cantidadSalida,S.codArticulo,A.nombre,A.ubicacion FROM salidaarticulo as S "
					+ "INNER JOIN articulos as A ON S.codArticulo=A.codArticulo "
					+ "where S.codSalida=?");
			lista.setInt(1, submited.getCodSalida());
			 
			 ResultSet resultado = lista.executeQuery();
			while (resultado.next()) { 
				
				listSalidaArticulos.add(
						new tablaMostrar(
						resultado.getInt("codArticulo"), resultado.getInt("cantidadSalida"),
						resultado.getString("nombre"), resultado.getString("ubicacion")));
			}
			System.out.println(listSalidaArticulos);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	
	

}
