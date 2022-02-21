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
import dad.hospitalorganizer.models.Salida;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class SalidaVerController implements Initializable {
	private ObservableList<Salida> listSalida = FXCollections.observableArrayList();
	private ListProperty<Salida> listSalidas = new SimpleListProperty<Salida>(listSalida);
	private Conecciones Database;
	@FXML
	private GridPane view;

	@FXML
	private TableView<Salida> tablaSalidaArticulo;

	@FXML
	private TableColumn<Salida, String> lugarColumn;

	@FXML
	private TableColumn<Salida, String> motivoColumn;

	@FXML
	private TableColumn<Salida, String> responsableColumn;

	@FXML
	private TableColumn<Salida, String> fechaColumn;

	@FXML
	private Button volverButton;


	@FXML
	private Button mostrarbtn;

	@FXML
	private Button checkearbtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Database = new Conecciones();
		lugarColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getLugar()));
		motivoColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getMotivo()));
		responsableColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCodPersonal()));
		fechaColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getFechaSalida()));
		tablaSalidaArticulo.itemsProperty().bind(listSalidas);
		try {
			actualizar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	public GridPane getView() {
		return view;
	}

	public SalidaVerController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidasVerView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@FXML
	void onCheckAction(ActionEvent event) throws SQLException {
		PreparedStatement lista = Database.conexion.prepareStatement("UPDATE salidas SET comprobar=0 where codSalida=?");
		 lista.setInt(1, tablaSalidaArticulo.getSelectionModel().getSelectedItem().getCodSalida());
		 lista.executeUpdate();
		 actualizar();
	}

	@FXML
	void onMostrarAction(ActionEvent event) {

	}

	@FXML
	void onVolverAction(ActionEvent event) {
		App.goToMain();
	}

	public void actualizar() throws SQLException {
		listSalidas.clear();
		try {
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM salidas where comprobar=1");
			 
			 ResultSet resultado = lista.executeQuery();
			listSalidas.add(new Salida(1,"A","B","C","D",new Date(10),1));
			while (resultado.next()) { 
				
				listSalidas.add(new Salida(
						resultado.getInt("codSalida"), resultado.getString("lugarSalida"),
						resultado.getString("motivoSalida"), resultado.getString("paciente"),
						resultado.getString("personal"), resultado.getDate("fechaSalida")
						,resultado.getInt("comprobar")));
			}
			System.out.println(listSalidas);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
