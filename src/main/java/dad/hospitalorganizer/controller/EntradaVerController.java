package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.informes.GenerarPDF;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.EntradaArticulo;
import javafx.beans.property.ListProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import net.sf.jasperreports.engine.JRException;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class EntradaVerController implements Initializable {
	private Conecciones Database;
	private ListProperty<String> proveedorProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<String> fechaEntradaProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<EntradaArticulo> listEntradaArticulo=new SimpleListProperty<EntradaArticulo>(FXCollections.observableArrayList()); 
	@FXML
    private GridPane view;

    @FXML
    private ComboBox<String> proveedorCombo;

    @FXML
    private ComboBox<String> fechaEntradaCombo;

    @FXML
    private Button informeButton;

    @FXML
    private TableView<EntradaArticulo> entradaArticuloTable;

    @FXML
    private TableColumn<EntradaArticulo, String> nombreArticuloColumn;

    @FXML
    private TableColumn<EntradaArticulo, String> cantidadColumn;

    @FXML
    private TableColumn<EntradaArticulo, String> caducidadColumn;
    
    @FXML
    private Button volverButton;

    /**
     * Inicializa la clase con sus bindeos, listeners, etc
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getProveedorBox();
		proveedorCombo.itemsProperty().bind(proveedorProperty);
		fechaEntradaCombo.itemsProperty().bind(fechaEntradaProperty);
		entradaArticuloTable.itemsProperty().bindBidirectional(listEntradaArticulo);
		nombreArticuloColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getNombreArticulo()));
		cantidadColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getCantidad()+"") );
		caducidadColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getCaducidad().toString()));
		proveedorCombo.valueProperty().addListener((o,ov,nv) -> onProveedorChange(o,ov,nv));
		fechaEntradaCombo.valueProperty().addListener((o,ov,nv) -> onFechaChange(o,ov,nv));
	}
    /**
     * Escucha los cambios en la fecha
     */
	private void onFechaChange(ObservableValue<? extends String> o, String ov, String nv) {
		if (ov!=null) {
		}
		if (nv!=null) {
			try {
				actualizar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    /**
     * Escucha los cambios en el combo Proveedor
     */
	private void onProveedorChange(ObservableValue<? extends String> o, String ov, String nv) {
		if (ov!=null) {
			fechaEntradaProperty.unbind();
		}
		if (nv!=null) {
			fechaEntradaProperty.clear();
			getFechaEntradaBox();
			fechaEntradaCombo.itemsProperty().bind(fechaEntradaProperty);
			try {
				actualizar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    /**
     * Genera la interfaz apartir del fxml
     */
    public EntradaVerController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasVerView.fxml"));
		loader.setController(this);
		loader.load();
	}
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
     * Nos devuelve los fecha en su combobox
     */
	public void getFechaEntradaBox() {
		try {
		Database=new Conecciones();
		String a="";
		PreparedStatement list = Database.conexion.prepareStatement("select codproveedor from Proveedores where nombre=?");
		list.setString(1, proveedorCombo.getSelectionModel().getSelectedItem());	
		ResultSet result;
		result= list.executeQuery();
			while (result.next()) {	
				a=result.getString("codproveedor");
			}
		PreparedStatement lista = Database.conexion.prepareStatement("select fechaEntrada from Entradas where codproveedor=?");
		lista.setString(1, a);	
		ResultSet resultado;
		resultado = lista.executeQuery();
			while (resultado.next()) {	
				fechaEntradaProperty.add(resultado.getString("fechaEntrada"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * Actualiza la tabla con sus datos
     */
	public void actualizar() throws SQLException {
		listEntradaArticulo.clear();
		try {	
			PreparedStatement lista = Database.conexion.prepareStatement(""
					+ "SELECT entradaarticulo.codEntrada, articulos.nombre, entradaarticulo.cantidad, articulos.codArticulo, caducidad FROM entradaarticulo"
					+ "INNER JOIN Articulos ON entradaarticulo.codArticulo=articulos.codArticulo "
					+ "INNER JOIN Entradas ON Entradas.codEntrada=entradaarticulo.codEntrada "
					+ "INNER JOIN proveedores ON Proveedores.codproveedor=Entradas.codproveedor where proveedores.nombre=? AND entradas.fechaEntrada=?");
			lista.setString(1, proveedorCombo.getSelectionModel().getSelectedItem());	
			lista.setString(2, fechaEntradaCombo.getSelectionModel().getSelectedItem());	
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				listEntradaArticulo.add(new EntradaArticulo(resultado.getInt("articulos.codArticulo"), resultado.getString("articulos.nombre"),resultado.getInt("entradaarticulo.cantidad"),resultado.getDate("caducidad")));
			}
		} catch (Exception e) {
		 		e.getStackTrace();
		}}
    /**
     * Actualiza la tabla con sus datos
     */
	public void actualizar2() throws SQLException {
		listEntradaArticulo.clear();
		try {	
			PreparedStatement lista = Database.conexion.prepareStatement(""
					+ "SELECT entradaarticulo.codEntrada, articulos.nombre, entradaarticulo.cantidad, articulos.codArticulo, caducidad FROM entradaarticulo"
					+ "INNER JOIN Articulos ON entradaarticulo.codArticulo=articulos.codArticulo "
					+ "INNER JOIN Entradas ON Entradas.codEntrada=entradaarticulo.codEntrada "
					+ "INNER JOIN proveedores ON Proveedores.codproveedor=Entradas.codproveedor where proveedores.nombre=? AND entradas.fechaEntrada=?");
			lista.setString(1, proveedorCombo.getSelectionModel().getSelectedItem());	
			lista.setString(2, fechaEntradaCombo.getSelectionModel().getSelectedItem());	
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				listEntradaArticulo.add(new EntradaArticulo(resultado.getInt("articulos.codArticulo"), resultado.getString("articulos.nombre"),resultado.getInt("entradaarticulo.cantidad"),resultado.getDate("caducidad")));
			}
		} catch (Exception e) {
		 		e.getStackTrace();
		}
	}
    /**
     * Genera un informe
     */
    @FXML
    void OnInformeAction(ActionEvent event) throws JRException, IOException {
    	if (fechaEntradaCombo.getSelectionModel().isEmpty()==false) {
    		GenerarPDF.generarPdfEntrada(getListEntradaArticulo());
    		} else {
    			Alert confirm = new Alert(AlertType.ERROR);
    			confirm.setTitle("ERROR");
    			confirm.setHeaderText("Error en la entrada");
    			confirm.setContentText("Asegúrese de que ha seleccionado una fecha correctamente.");
    			confirm.showAndWait();
    		}
    }
    /**
     * Volve al menu
     */
    @FXML
    void OnVolverAction(ActionEvent event) {
    	App.goToMain();
    }
    /**
     * Devuelve la vista
     */
    public GridPane getView() {
		return view;
	}
    /**
     * Nos devuelve la lista de articulos
     */
	public List<EntradaArticulo> getListEntradaArticulo() {
		return listEntradaArticulo.get();
	}
}
