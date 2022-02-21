package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Articulocombo;
import dad.hospitalorganizer.models.Paciente;
import dad.hospitalorganizer.models.Proveedorcombo;
import dad.hospitalorganizer.models.SalidaArticulo;
import javafx.fxml.Initializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SalidasFormController implements Initializable {
	private ObservableList<SalidaArticulo> listSalidaArticulo = FXCollections.observableArrayList();
	private ListProperty<SalidaArticulo> listSalidaArticulos = new SimpleListProperty<SalidaArticulo>(
			listSalidaArticulo);
	private ListProperty<Articulocombo> articuloProperty = new SimpleListProperty<Articulocombo>(
			FXCollections.observableArrayList());
	private ListProperty<Proveedorcombo> proveedorProperty = new SimpleListProperty<Proveedorcombo>(
			FXCollections.observableArrayList());

	private ListProperty<String> lugarProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private Conecciones Database;

	@FXML
	private ComboBox<Paciente> pacienteCombo;

	@FXML
	private ComboBox<Articulocombo> articulosCombo;

	@FXML
	private TableView<SalidaArticulo> tableArticulos;

	@FXML
	private TableColumn<SalidaArticulo, String> articuloColumn;

	@FXML
	private TableColumn<SalidaArticulo, String> cantidadColumn;

	@FXML
	private TableColumn<SalidaArticulo, String> ProveedorColumn;

	@FXML
	private ComboBox<Proveedorcombo> proveedorCombo;

	@FXML
	private GridPane view;

	@FXML
	private Button crearButton;

	@FXML
	private ComboBox<String> lugarCombo;

	@FXML
	private TextField motivoText;

	@FXML
	private DatePicker salidaDatePicker;

	@FXML
	private TextField cantidadText;

	@FXML
	private Button volverButton;

	int codSalida;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Database = new Conecciones();
		getLugarBox();

		lugarCombo.itemsProperty().bind(lugarProperty);
		articuloColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getNomArticulo()));
		cantidadColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCantidad()));
		ProveedorColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getNombreProveedor()));
		tableArticulos.itemsProperty().bind(listSalidaArticulos);
		proveedorCombo.valueProperty().addListener((o, ov, nv) -> onProveedorChange(o, ov, nv));
		proveedorCombo.itemsProperty().bind(proveedorProperty);
		articulosCombo.itemsProperty().bind(articuloProperty);
		

		try {
			getProveedor();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public SalidasFormController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidasFormView.fxml"));
		loader.setController(this);
		loader.load();
	}

	private void onProveedorChange(ObservableValue<? extends Proveedorcombo> o, Proveedorcombo ov, Proveedorcombo nv) {
		if (ov != null) {
			articuloProperty.unbind();
			System.out.println("Valor viejo" + articuloProperty.getValue());
		}
		if (nv != null) {
			cantidadText.clear();
			articuloProperty.clear();
			// caducidadDatePicker;
			try {
				getArticulos();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			articulosCombo.itemsProperty().bind(articuloProperty);
			System.out.println("Valor nuevo " + articuloProperty.getValue());

			System.out.println("Valor nuevo " + nv);
		}
	}

	public GridPane getView() {
		return view;
	}

	public void limpiar() {
		motivoText.setText("");
		salidaDatePicker.setValue(null);
		cantidadText.setText("");
		listSalidaArticulos.clear();
		proveedorProperty.clear();
		articuloProperty.clear();
		lugarProperty.clear();
	}

	@FXML
	void onClickAñadir(ActionEvent event) {

		listSalidaArticulos
				.add(new SalidaArticulo(articulosCombo.getSelectionModel().getSelectedItem().getCodArticulo(),
						Integer.parseInt(cantidadText.getText()),
						articulosCombo.getSelectionModel().getSelectedItem().getNombre(),
						proveedorCombo.getSelectionModel().getSelectedItem().getNombre()));

		System.out.println(listSalidaArticulos);

		// listSalidaArticulos.add();
	}

	@FXML
	void onClickCrear(ActionEvent event) throws SQLException {
		PreparedStatement lista;
		lista = Database.conexion.prepareStatement(
				"INSERT INTO `salidas`( `lugarSalida`, `motivoSalida`, `paciente`, `personal`, `fechaSalida`, `comprobar`)"
						+ " VALUES ((?),(?),(?),(?),(?),(?))");
		lista.setString(1, lugarCombo.getSelectionModel().getSelectedItem());
		lista.setString(2, motivoText.getText());
		lista.setString(3, App.usuario.getDni());// pacienteCombo.getSelectionModel().getSelectedItem().toString());
		lista.setString(4, App.usuario.getDni());
		lista.setString(5, salidaDatePicker.getValue() + "");
		lista.setInt(6, 1);
		lista.executeUpdate();
		lista = Database.conexion.prepareStatement(
				"select codSalida from salidas where lugarSalida=(?) and motivoSalida=(?) and personal =(?) and fechaSalida=(?) and comprobar=1");
		ResultSet resultado;
		lista.setString(1, lugarCombo.getSelectionModel().getSelectedItem());
		lista.setString(2, motivoText.getText());
		lista.setString(3, App.usuario.getDni());
		lista.setString(4, salidaDatePicker.getValue() + "");
		resultado = lista.executeQuery();
		while (resultado.next()) {
			codSalida = resultado.getInt("codSalida");
		}
		for (int i = 0; i < listSalidaArticulos.size(); i++) {
			lista = Database.conexion.prepareStatement(
					"INSERT INTO `salidaarticulo`(`codArticulo`, `codSalida`, `cantidadSalida`) VALUES ((?),(?),(?))");

			lista.setInt(1, listSalidaArticulos.get(i).getCodArticulo());
			lista.setInt(2, codSalida);
			lista.setInt(3, listSalidaArticulos.get(i).getCantidad());// caducidadDatePicker.getValue()+"");
			lista.executeUpdate();
		}
	}

	@FXML
	void OnVolverAction(ActionEvent event) {
		limpiar();
		try {
			getProveedor();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		App.goToMain();
	}

	public void getProveedor() throws SQLException {
		proveedorProperty.clear();

		PreparedStatement lista = Database.conexion.prepareStatement("select codProveedor,nombre from proveedores");
		ResultSet resultado;
		resultado = lista.executeQuery();
		while (resultado.next()) {
			proveedorProperty.add(new Proveedorcombo(resultado.getInt("codProveedor"), resultado.getString("nombre")));
		}

	}

	public void getArticulos() throws SQLException {
		articuloProperty.clear();
		PreparedStatement lista = Database.conexion
				.prepareStatement("select Articulos.codArticulo,Articulos.nombre from Articulos INNER JOIN proveedores"
						+ " ON proveedores.codproveedor=articulos.proveedor where proveedores.nombre='"
						+ proveedorCombo.getSelectionModel().getSelectedItem().getNombre() + "'");

		ResultSet resultado = lista.executeQuery();

		while (resultado.next()) {
			articuloProperty.add(new Articulocombo(resultado.getInt("codArticulo"), resultado.getString("nombre")));
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
