package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Articulo;
import dad.hospitalorganizer.models.Entrada;
import dad.hospitalorganizer.models.EntradaArticulo;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

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
		//articulosBox.valueProperty().addListener((o,ov,nv) -> onProveedorChange(o,ov,nv));
		cantidadText.textProperty().bindBidirectional(cantidad,new NumberStringConverter());
	}
	private void onProveedorChange(ObservableValue<? extends String> o, String ov, String nv) {
		if (ov!=null) {
			articuloProperty.unbind();
			System.out.println("Valor viejo"+articuloProperty.getValue());
		}
		if (nv!=null) {
			articuloProperty.clear();
			getArticulos();
			articulosBox.itemsProperty().bind(articuloProperty);
			System.out.println("Valor nuevo "+articuloProperty.getValue());
			
			try {
				actualizar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Valor nuevo "+listEntrada);

		}
	}

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
	public void getArticulos() {
		try {
		Database=new Conecciones();	
		PreparedStatement lista = Database.conexion.prepareStatement("select Articulos.nombre from Articulos INNER JOIN proveedores ON proveedores.codproveedor=articulos.proveedor where proveedores.nombre=?");
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

	public EntradaFormController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasFormView.fxml"));
		loader.setController(this);
		loader.load();
	}
	public void actualizar() throws SQLException {
		listEntrada.clear();
		try {	
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM entradas INNER JOIN proveedores ON entradas.codProveedor=proveedores.codProveedor where proveedores.nombre=?");
			lista.setString(1, proveedorBox.getSelectionModel().getSelectedItem());	
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				listEntrada.add(new Entrada(resultado.getInt("codEntrada"),resultado.getString("nombre"),resultado.getDate("fechaEntrada")));
			}
		} catch (Exception e) {
		 		e.getStackTrace();
		}}
	@FXML
	void onNuevaEntradaAction(ActionEvent event) {
	//insert a la tabla entradas
	}

    @FXML
    void onAnadirAction(ActionEvent event) throws SQLException {
    	System.out.println("Hola buenas tardes");
    	PreparedStatement list = Database.conexion.prepareStatement("select * from Articulos where nombre=?");
		list.setString(1, articulosBox.getSelectionModel().getSelectedItem());
		ResultSet result = list.executeQuery();
		while (result.next()) {
		artic=result.getInt("codArticulo");
		}
    	System.out.println(tablaEntradaArticulo.getSelectionModel().getSelectedItem().getCodEntrada());
    	System.out.println(cantidad.getValue());
    	System.out.println(caducidadDatePicker.getValue()+" "+artic);
    try {
		PreparedStatement lista = Database.conexion.prepareStatement("INSERT INTO entradaarticulo(codArticulo, codEntrada, cantidad, caducidad)values ((?),(?),(?),(?))");
		lista.setInt(1, artic);
		lista.setInt(2, tablaEntradaArticulo.getSelectionModel().getSelectedItem().getCodEntrada());
		lista.setInt(3, cantidad.getValue());
		lista.setString(4, caducidadDatePicker.getValue()+"");
		lista.executeUpdate();
		System.out.println("Insertado");
    	} catch (Exception e) {
    		System.out.println("no insertado");
    	} 
    }
    @FXML
    void onVolverAction(ActionEvent event) {
    	App.goToMain();
    }
    public GridPane getView() {
		return view;
	}
}
