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
import dad.hospitalorganizer.models.Lugar;
import dad.hospitalorganizer.models.Paciente;
import dad.hospitalorganizer.models.Proveedorcombo;
import dad.hospitalorganizer.models.SalidaArticulo;
import javafx.fxml.Initializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class SalidasFormController implements Initializable {
	private ListProperty<SalidaArticulo> listSalidaArticulos = new SimpleListProperty<SalidaArticulo>(FXCollections.observableArrayList());
	private ListProperty<Articulocombo> articuloProperty = new SimpleListProperty<Articulocombo>(FXCollections.observableArrayList());
	private ListProperty<Proveedorcombo> proveedorProperty = new SimpleListProperty<Proveedorcombo>(FXCollections.observableArrayList());
	private ListProperty<Lugar> lugarProperty = new SimpleListProperty<Lugar>(FXCollections.observableArrayList());
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
	private ComboBox<Lugar> lugarCombo;

	@FXML
	private TextField motivoText;

	@FXML
	private DatePicker salidaDatePicker;

	@FXML
	private TextField cantidadText;

	@FXML
	private Button volverButton;

	int codSalida;
	/**
     * Inicializa la clase con sus bindeos, listeners, etc
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Database = new Conecciones();

		articuloColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getNomArticulo()));
		cantidadColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCantidad()));
		ProveedorColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getNombreProveedor()));
		tableArticulos.itemsProperty().bind(listSalidaArticulos);
		proveedorCombo.valueProperty().addListener((o, ov, nv) -> onProveedorChange(o, ov, nv));
		proveedorCombo.itemsProperty().bind(proveedorProperty);
		articulosCombo.itemsProperty().bind(articuloProperty);
		lugarCombo.itemsProperty().bind(lugarProperty);

		try {
			getLugarBox();
			getProveedor();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
    /**
     * Genera la interfaz apartir del fxml
     */
	public SalidasFormController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidasFormView.fxml"));
		loader.setController(this);
		loader.load();
	}
    /**
     * Escucha los cambios en el combo Proveedor
     */
	private void onProveedorChange(ObservableValue<? extends Proveedorcombo> o, Proveedorcombo ov, Proveedorcombo nv) {
		if (ov != null) {
			articuloProperty.unbind();
		}
		if (nv != null) {
			cantidadText.clear();
			articuloProperty.clear();
			try {
				getArticulos();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			articulosCombo.itemsProperty().bind(articuloProperty);
		}
	}
    /**
     * Devuelve la vista
     */
	public GridPane getView() {
		return view;
	}
    /**
     * Añade el articulo a la tabla
     */
	@FXML
	void onClickAñadir(ActionEvent event) {
		if (lugarCombo.getSelectionModel().isEmpty()==false && proveedorCombo.getSelectionModel().isEmpty()==false && articulosCombo.getSelectionModel().isEmpty()==false 
				&& cantidadText.getText().isEmpty()==false && motivoText.getText().isEmpty()==false && salidaDatePicker.getValue()!=null ) {
		listSalidaArticulos.add(new SalidaArticulo(articulosCombo.getSelectionModel().getSelectedItem().getCodArticulo(),
						Integer.parseInt(cantidadText.getText()),
						articulosCombo.getSelectionModel().getSelectedItem().getNombre(),
						proveedorCombo.getSelectionModel().getSelectedItem().getNombre()));
		}else {
			Alert confirm = new Alert(AlertType.ERROR);
			confirm.setTitle("ERROR");
			confirm.setHeaderText("Error en la entrada");
			confirm.setContentText("Asegúrese de ha escrito todos los campos correctamente.");
			confirm.showAndWait();}
	}
	/**
     * Crea los articulos añadidos a la tabla
     */
	@FXML
	void onClickCrear(ActionEvent event) throws SQLException {
		PreparedStatement lista;
		lista = Database.conexion.prepareStatement(
				"INSERT INTO `salidas`( `lugar`, `motivoSalida`, `paciente`, `personal`, `fechaSalida`, `comprobar`)"
						+ " VALUES ((?),(?),(?),(?),(?),(?))");
		lista.setInt(1, lugarCombo.getSelectionModel().getSelectedItem().getCodLugar());
		lista.setString(2, motivoText.getText());
		lista.setString(3, App.usuario.getDni());
		lista.setString(4, App.usuario.getDni());
		lista.setString(5, salidaDatePicker.getValue() + "");
		lista.setInt(6, 1);
		lista.executeUpdate();
		lista = Database.conexion.prepareStatement(
				"select codSalida from salidas where lugar=(?) and motivoSalida=(?) and personal =(?) and fechaSalida=(?) and comprobar=1");
		ResultSet resultado;
		lista.setInt(1, lugarCombo.getSelectionModel().getSelectedItem().getCodLugar());
		lista.setString(2, motivoText.getText());
		lista.setString(3, App.usuario.getDni());
		lista.setString(4, salidaDatePicker.getValue() + "");
		resultado = lista.executeQuery();
		while (resultado.next()) {

			codSalida = resultado.getInt("codSalida");
			System.out.println(codSalida);
		}
		for (int i = 0; i < listSalidaArticulos.size(); i++) {
			lista = Database.conexion.prepareStatement(
					"INSERT INTO `salidaarticulo`(`codArticulo`, `codSalida`, `cantidadSalida`) VALUES ((?),(?),(?))");

			lista.setInt(1, listSalidaArticulos.get(i).getCodArticulo());
			lista.setInt(2, codSalida);
			lista.setInt(3, listSalidaArticulos.get(i).getCantidad());
			lista.executeUpdate();

			// actualiza la cantidad en la tabla de inventario
			int cantid = 0;
			PreparedStatement list = Database.conexion.prepareStatement("select * from Articulos where codArticulo=?");
			list.setInt(1, listSalidaArticulos.get(i).getCodArticulo());

			ResultSet result = list.executeQuery();
			while (result.next()) {
				cantid = result.getInt("cantidad");
			}

			PreparedStatement updatecantida = Database.conexion
					.prepareStatement("UPDATE articulos SET cantidad=? where codArticulo=?");
			updatecantida.setInt(1, cantid - listSalidaArticulos.get(i).getCantidad());
			updatecantida.setInt(2, listSalidaArticulos.get(i).getCodArticulo());
			updatecantida.executeUpdate();
		}
		limpiar();
	}
    /**
     * Viajamos a la vista del menu
     */
	@FXML
	void OnVolverAction(ActionEvent event) {
		limpiar();
		try {
			getProveedor();
			getLugarBox();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		App.goToMain();
	}
    /**
     * Nos devuelve los proveedores y los mete en el combo
     */
	public void getProveedor() throws SQLException {
		proveedorProperty.clear();
		PreparedStatement lista = Database.conexion.prepareStatement("select codProveedor,nombre from proveedores");
		ResultSet resultado;
		resultado = lista.executeQuery();
		while (resultado.next()) {
			proveedorProperty.add(new Proveedorcombo(resultado.getInt("codProveedor"), resultado.getString("nombre")));
		}

	}
    /**
     * Nos devuelve los articulos y los mete en su respectivo combo
     */
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
    /**
     * Nos devuelve los lugares y los mete en su respectivo combo
     */
	public void getLugarBox() {
		lugarProperty.clear();
		try {
			Database = new Conecciones();
			PreparedStatement lista = Database.conexion.prepareStatement("select * from lugares");
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				lugarProperty.add(new Lugar(resultado.getInt("codLugar"), resultado.getString("lugar")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * Limpia los datos de la vista
     */
	public void limpiar() {
		motivoText.setText("");
		salidaDatePicker.setValue(null);
		cantidadText.setText("");
		listSalidaArticulos.clear();
		proveedorProperty.clear();
		articuloProperty.clear();
		lugarProperty.clear();
	}

}
