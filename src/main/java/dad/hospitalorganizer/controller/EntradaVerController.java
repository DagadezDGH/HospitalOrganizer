package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.informes.GenerarPDF;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Entrada;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import net.sf.jasperreports.engine.JRException;

public class EntradaVerController implements Initializable {
	private Conecciones Database=new Conecciones();
	private ListProperty<String> proveedorProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<String> fechaEntradaProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<EntradaArticulo> listEntradaArticulo=new SimpleListProperty<EntradaArticulo>(FXCollections.observableArrayList()); 
	private List<EntradaArticulo> lista = new ArrayList<>();
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
	private void onProveedorChange(ObservableValue<? extends String> o, String ov, String nv) {
		if (ov!=null) {
			fechaEntradaProperty.unbind();
			System.out.println("Valor viejo"+fechaEntradaProperty.getValue());
		}
		if (nv!=null) {
			fechaEntradaProperty.clear();
			getFechaEntradaBox();
			fechaEntradaCombo.itemsProperty().bind(fechaEntradaProperty);
			System.out.println("Valor nuevo "+fechaEntradaProperty.getValue());
			try {
				actualizar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
    public EntradaVerController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntradasVerView.fxml"));
		loader.setController(this);
		loader.load();
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
    
	public void actualizar() throws SQLException {
		listEntradaArticulo.clear();
		try {	
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT entradaarticulo.codEntrada, articulos.nombre, entradaarticulo.cantidad, caducidad FROM entradaarticulo INNER JOIN Articulos ON entradaarticulo.codArticulo=articulos.codArticulo INNER JOIN Entradas ON Entradas.codEntrada=entradaarticulo.codEntrada INNER JOIN proveedores ON Proveedores.codproveedor=Entradas.codproveedor where proveedores.nombre=? AND entradas.fechaEntrada=?");
			lista.setString(1, proveedorCombo.getSelectionModel().getSelectedItem());	
			lista.setString(2, fechaEntradaCombo.getSelectionModel().getSelectedItem());	
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				listEntradaArticulo.add(new EntradaArticulo(resultado.getString("articulos.nombre"),resultado.getInt("entradaarticulo.cantidad"),resultado.getDate("caducidad")));
			}
		} catch (Exception e) {
		 		e.getStackTrace();
		}}
    @FXML
    void OnInformeAction(ActionEvent event) throws JRException, IOException {
    	
    	GenerarPDF.generarPdf(getListEntradaArticulo());
    	
    }
    @FXML
    void OnVolverAction(ActionEvent event) {
    	App.goToMain();
    }
    public GridPane getView() {
		return view;
	}
	
	public List<EntradaArticulo> getListEntradaArticulo() {
		return listEntradaArticulo.get();
	}
}
