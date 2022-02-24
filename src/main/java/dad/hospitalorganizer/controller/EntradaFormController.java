package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.dialogs.crearEntradaDialog;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Entrada;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class EntradaFormController implements Initializable {
	//model
	private ListProperty<String> proveedorProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<String> articuloProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<Entrada> listEntrada=new SimpleListProperty<Entrada>(FXCollections.observableArrayList()); 
	private SimpleIntegerProperty cantidad=new SimpleIntegerProperty();
	private Conecciones Database;
	private int artic;
	//view
	@FXML
	private GridPane view;

	@FXML
	private ComboBox<String> proveedorBox;

	@FXML
	private ComboBox<String> articulosBox;

	@FXML
	private TextField cantidadText;

	@FXML
	private TableView<Entrada> tablaEntradaArticulo;

	@FXML
	private TableColumn<Entrada, String> IdEntradaColumn;

	@FXML
	private TableColumn<Entrada, String> proveedorColumn;

	@FXML
	private TableColumn<Entrada, String> fechaColumn;

	@FXML
	private Button nuevaEntradaButton;
    
    @FXML
    private Button volverButton;
    
    @FXML
    private Button anadirButton;
    
    @FXML
    private DatePicker caducidadDatePicker;
    /**
     * Inicializa la clase con sus bindeos, listeners, etc
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getProveedorBox();
		proveedorBox.itemsProperty().bind(proveedorProperty);
		articulosBox.itemsProperty().bind(articuloProperty);
		tablaEntradaArticulo.itemsProperty().bind(listEntrada);
		IdEntradaColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCodEntrada()));
		proveedorColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getProveedor()));
		fechaColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getFecha().toString()));
		proveedorBox.valueProperty().addListener((o,ov,nv) -> onProveedorChange(o,ov,nv));
		cantidadText.textProperty().bindBidirectional(cantidad,new NumberStringConverter());
	}
    /**
     * Escucha los cambios en el combo Proveedor
     */
	private void onProveedorChange(ObservableValue<? extends String> o, String ov, String nv) {
		if (ov!=null) {
			articuloProperty.unbind();
			System.out.println("Valor viejo"+articuloProperty.getValue());
		}
		if (nv!=null) {
			cantidadText.clear();
			articuloProperty.clear();
			getArticulos();
			articulosBox.itemsProperty().bind(articuloProperty);
			System.out.println("Valor nuevo "+articuloProperty.getValue());
			try {
				actualizar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Valor nuevo "+listEntrada);
		}
	}
    /**
     * Genera la interfaz apartir del fxml
     */
	public EntradaFormController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasFormView.fxml"));
		loader.setController(this);
		loader.load();
	}
    /**
     * Genera un dialog que rellenaremos con datos posteriormente
     */
	@FXML
	void onNuevaEntradaAction(ActionEvent event) throws SQLException, IOException {
		crearEntradaDialog dialog = new crearEntradaDialog();
		dialog.showAndWait();
		actualizar();
	}
    /**
     * Añadir el articulo a la tabla
     */
    @FXML
    void onAnadirAction(ActionEvent event) throws SQLException {
    	if (tablaEntradaArticulo.getSelectionModel().isEmpty()==false) {
    	Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("CONFIRMACION");
		confirmation.setHeaderText("¿Seguro que quieres añadir este articulo?");
		Optional<ButtonType> resultad = confirmation.showAndWait();
		if (resultad.get() == ButtonType.OK && cantidad.getValue()!=0 && caducidadDatePicker.getValue()!=null) {
	    	int cantid=0;
			PreparedStatement list = Database.conexion.prepareStatement("select * from Articulos where nombre=?");
			list.setString(1, articulosBox.getSelectionModel().getSelectedItem());
			
			ResultSet result = list.executeQuery();
			while (result.next()) {
			artic=result.getInt("codArticulo");
			cantid=result.getInt("cantidad");
			}
	    try {
			PreparedStatement lista = Database.conexion.prepareStatement("INSERT INTO EntradaArticulo(codArticulo, codEntrada, cantidad, caducidad)values ((?),(?),(?),(?))");
			lista.setInt(1, artic);
			lista.setInt(2, tablaEntradaArticulo.getSelectionModel().getSelectedItem().getCodEntrada());
			lista.setInt(3, cantidad.getValue());
			lista.setString(4, caducidadDatePicker.getValue()+"");
			lista.executeUpdate();
			
			PreparedStatement updatecantida = Database.conexion.prepareStatement("UPDATE Articulos SET cantidad=? where codArticulo=?");
			updatecantida.setInt(1, cantidad.getValue()+cantid);
			updatecantida.setInt(2, artic);
			updatecantida.executeUpdate();
	    	} catch (Exception e) {
	    		System.out.println("no insertado");
	    	}
		} else {
		Alert confirm = new Alert(AlertType.ERROR);
		confirm.setTitle("ERROR");
		confirm.setHeaderText("Error en la entrada");
		confirm.setContentText("Asegúrese de que la escribió correctamente.");
		confirm.showAndWait();}
		}
		else {
			Alert confirm = new Alert(AlertType.ERROR);
			confirm.setTitle("ERROR");
			confirm.setHeaderText("Error en la entrada");
			confirm.setContentText("Asegúrese de que ha seleccionado un item de la tabla correctamente.");
			confirm.showAndWait();}
    }
    /**
     * Volve al menu
     */
    @FXML
    void onVolverAction(ActionEvent event) {
    	App.goToMain();
    }
    /**
     * Devuelve la vista
     */
    public GridPane getView() {
		return view;
	}
    /**
     * Actualiza la tabla con sus datos
     */
	public void actualizar() throws SQLException {
		listEntrada.clear();
		try {	
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM Entradas INNER JOIN Proveedores ON Entradas.codProveedor=Proveedores.codProveedor where Proveedores.nombre=?");
			lista.setString(1, proveedorBox.getSelectionModel().getSelectedItem());	
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				listEntrada.add(new Entrada(resultado.getInt("codEntrada"),resultado.getString("nombre"),resultado.getDate("fechaEntrada")));
			}
		} catch (Exception e) {
		 		e.getStackTrace();
		}}
    /**
     * Nos devuelve los proveedores y los mete en el combo
     */
	public void getProveedorBox() {
		try {
		Database=new Conecciones();	
		PreparedStatement lista = Database.conexion.prepareStatement("select * from Proveedores");
		ResultSet resultado;
		resultado = lista.executeQuery();
			while (resultado.next()) {	
				proveedorProperty.add(resultado.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * Nos devuelve los articulos y los mete en el combo
     */
	public void getArticulos() {
		try {
		Database=new Conecciones();	
		PreparedStatement lista = Database.conexion.prepareStatement("select Articulos.nombre from Articulos INNER JOIN Proveedores ON Proveedores.codproveedor=Articulos.proveedor where Proveedores.nombre=?");
		lista.setString(1, proveedorBox.getSelectionModel().getSelectedItem());	
		ResultSet resultado;
		resultado = lista.executeQuery();
			while (resultado.next()) {
				articuloProperty.add(resultado.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
