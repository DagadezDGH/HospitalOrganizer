package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.dialogs.MostrarSalidaArticuloDialog;
import dad.hospitalorganizer.dialogs.modificarArticuloDialog;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Salida;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class SalidaVerController implements Initializable {

	private ListProperty<String> lugarProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<String> fechaSalidaProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
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
	private ComboBox<String> fechaSalidaCombo;
	
	@FXML 
	private ComboBox<String> lugarCombo;
	
	@FXML
	private Button volverButton;


	@FXML
	private Button mostrarbtn;

	@FXML
	private Button checkearbtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Database = new Conecciones();
		getLugarBox();
		lugarCombo.itemsProperty().bind(lugarProperty);
		fechaSalidaCombo.itemsProperty().bind(fechaSalidaProperty);
		lugarColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getLugar()));
		motivoColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getMotivo()));
		responsableColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCodPersonal()));
		fechaColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getFechaSalida()));
		tablaSalidaArticulo.itemsProperty().bind(listSalidas);
		lugarCombo.valueProperty().addListener((o,ov,nv) -> onLugarChange(o,ov,nv));
		fechaSalidaCombo.valueProperty().addListener((o,ov,nv) -> onFechaChange(o,ov,nv));
		fechaSalidaCombo.setDisable(true);
		try {
			actualizar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void onFechaChange(ObservableValue<? extends String> o, String ov, String nv) {
		if (ov!=null) {
		}
		if (nv!=null) {
			try {
				actualizar2();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void onLugarChange(ObservableValue<? extends String> o, String ov, String nv) {
		if (ov!=null) {
			fechaSalidaProperty.unbind();
			System.out.println("Valor viejo"+fechaSalidaProperty.getValue());
		}
		if (nv!=null) {
			fechaSalidaProperty.clear();
			fechaSalidaCombo.setDisable(false);
			getFechaSalidaBox();
			fechaSalidaCombo.itemsProperty().bind(fechaSalidaProperty);
			System.out.println("Valor nuevo "+fechaSalidaProperty.getValue());
			try {
				actualizar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		Salida submited=tablaSalidaArticulo.getSelectionModel().getSelectedItem();
		MostrarSalidaArticuloDialog dialog;
		try {
			dialog = new MostrarSalidaArticuloDialog(submited);
			dialog.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public void actualizar2() throws SQLException {
		listSalidas.clear();
		
		try {
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM salidas where lugarSalida=? and fechaSalida=?");
			lista.setString(1, lugarCombo.getSelectionModel().getSelectedItem());	
			lista.setString(2, fechaSalidaCombo.getSelectionModel().getSelectedItem());
			 ResultSet resultado = lista.executeQuery();
			listSalidas.add(new Salida(1,"A","B","C","D",new Date(10),1));
			while (resultado.next()) { 
				
				listSalidas.add(new Salida(
						resultado.getInt("codSalida"),
						resultado.getString("lugarSalida"),
						resultado.getString("motivoSalida"),
						resultado.getString("paciente"),
						resultado.getString("personal"),
						resultado.getDate("fechaSalida")
						,resultado.getInt("comprobar")));
			}
			System.out.println(listSalidas);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	public void getFechaSalidaBox() {
		try {
		Database=new Conecciones();
		PreparedStatement lista = Database.conexion.prepareStatement("select fechaSalida from salidas where lugarSalida=?");
		lista.setString(1, lugarCombo.getSelectionModel().getSelectedItem());	
		ResultSet resultado;
		resultado = lista.executeQuery();
			while (resultado.next()) {	
				fechaSalidaProperty.add(resultado.getString("fechaSalida"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getLugarBox() {
		try {
		Database=new Conecciones();	
		PreparedStatement lista = Database.conexion.prepareStatement("select lugar from lugares");
		ResultSet resultado;
		resultado = lista.executeQuery();
			while (resultado.next()) {	
				lugarProperty.add(resultado.getString("lugar"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
