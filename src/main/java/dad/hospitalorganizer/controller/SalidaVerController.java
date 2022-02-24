package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.dialogs.MostrarSalidaArticuloDialog;
import dad.hospitalorganizer.informes.GenerarPDF;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Lugar;
import dad.hospitalorganizer.models.Salida;
import dad.hospitalorganizer.models.tablaMostrar;
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
public class SalidaVerController implements Initializable {

	private ListProperty<Lugar> lugarProperty=new SimpleListProperty<Lugar>(FXCollections.observableArrayList());
	private ListProperty<String> fechaSalidaProperty=new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ObservableList<Salida> listSalida = FXCollections.observableArrayList();
	private ListProperty<Salida> listSalidas = new SimpleListProperty<Salida>(listSalida);
	private ListProperty<tablaMostrar> listSalidaArticulos=new SimpleListProperty<tablaMostrar>(FXCollections.observableArrayList()); 
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
	private ComboBox<Lugar> lugarCombo;
	
	@FXML
	private Button volverButton;


	@FXML
	private Button mostrarbtn;

	@FXML
	private Button checkearbtn;
    
	@FXML
    private Button informeButton;
	/**
     * Inicializa la clase con sus bindeos, listeners, etc
     */
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
    /**
     * Escucha los cambios en el combo de Fecha
     */
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
    /**
     * Escucha los cambios en el combo de Lugares
     */
	private void onLugarChange(ObservableValue<? extends Lugar> o, Lugar ov, Lugar nv) {
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
	
    /**
     * Devuelve la vista
     */
	public GridPane getView() {
		return view;
	}

	/**
	 * Genera la interfaz apartir del fxml
	 */
	public SalidaVerController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalidasVerView.fxml"));
		loader.setController(this);
		loader.load();
	}
	/**
	 * Chekea si la salida ya ha salido
	 */
	@FXML
	void onCheckAction(ActionEvent event) throws SQLException {
		PreparedStatement lista = Database.conexion.prepareStatement("UPDATE Salidas SET comprobar=0 where codSalida=?");
		 lista.setInt(1, tablaSalidaArticulo.getSelectionModel().getSelectedItem().getCodSalida());
		 lista.executeUpdate();
		 actualizar();
	}
	/**
	 * Muestros en un dialog los articulos de esa salida
	 */
	@FXML
	void onMostrarAction(ActionEvent event) {
		Salida submited=tablaSalidaArticulo.getSelectionModel().getSelectedItem();
		MostrarSalidaArticuloDialog dialog;
		try {
			dialog = new MostrarSalidaArticuloDialog(submited);
			dialog.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Genera un informe con los articulos de esa salida
	 */
	@FXML
    void onInformeAction(ActionEvent event) throws JRException, IOException {
    	if (fechaSalidaCombo.getSelectionModel().isEmpty()==false) {
    		try {
    			PreparedStatement lista = Database.conexion.prepareStatement(""
    					+ "SELECT S.cantidadSalida,S.codArticulo,A.nombre,A.ubicacion FROM SalidaArticulo as S "
    					+ "INNER JOIN Articulos as A ON S.codArticulo=A.codArticulo "
    					+ "where S.codSalida=?");
    			lista.setInt(1, tablaSalidaArticulo.getSelectionModel().getSelectedItem().getCodSalida());
    			 
    			 ResultSet resultado = lista.executeQuery();
    			while (resultado.next()) { 
    				
    				listSalidaArticulos.add(
    						new tablaMostrar(
    						resultado.getInt("codArticulo"), resultado.getInt("cantidadSalida"),
    						resultado.getString("nombre"), resultado.getString("ubicacion")));
    			}
 
    		} catch (Exception e) {
    			e.getStackTrace();
    		}
    		GenerarPDF.generarPdfSalida(getListSalidaArticulo());
		} else {
			Alert confirm = new Alert(AlertType.ERROR);
			confirm.setTitle("ERROR");
			confirm.setHeaderText("Error en la entrada");
			confirm.setContentText("Asegúrese de que ha seleccionado una fecha correctamente.");
			confirm.showAndWait();
		}
	
    }
	/**
	 * Volvemos al menu del programa
	 */
	@FXML
	void onVolverAction(ActionEvent event) {
		App.goToMain();
	}
	/**
	 * Actualiza la tabla
	 */
	public void actualizar() throws SQLException {
		listSalidas.clear();
		
		try {
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM Salidas INNER JOIN Lugares ON Lugares.codLugar=Salidas.lugar where comprobar=1");
			 ResultSet resultado = lista.executeQuery();
			while (resultado.next()) { 
				
				listSalidas.add(new Salida(
						resultado.getInt("codSalida"), resultado.getString("Lugares.lugar"),
						resultado.getString("motivoSalida"), resultado.getString("paciente"),
						resultado.getString("personal"), resultado.getDate("fechaSalida")
						,resultado.getInt("comprobar")));
			}
			System.out.println(listSalidas);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	/**
	 * Actualiza la tabla
	 */
	public void actualizar2() throws SQLException {
		listSalidas.clear();
		
		try {
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM Salidas INNER JOIN Lugares ON Lugares.codLugar=Salidas.lugar where Salidas.lugar=? and fechaSalida=? ");
			lista.setInt(1, lugarCombo.getSelectionModel().getSelectedItem().getCodLugar());	
			lista.setString(2, fechaSalidaCombo.getSelectionModel().getSelectedItem());
			 ResultSet resultado = lista.executeQuery();
			
			while (resultado.next()) { 
				
				listSalidas.add(new Salida(
						resultado.getInt("codSalida"),
						resultado.getString("Lugares.lugar"),
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
    /**
     * Nos devuelve las fechas y las mete en el combo
     */
	public void getFechaSalidaBox() {
		try {
		Database=new Conecciones();
		PreparedStatement lista = Database.conexion.prepareStatement("SELECT DISTINCT fechaSalida from Salidas where lugar=?");
		lista.setInt(1, lugarCombo.getSelectionModel().getSelectedItem().getCodLugar());	
		ResultSet resultado;
		resultado = lista.executeQuery();
			while (resultado.next()) {	
				fechaSalidaProperty.add(resultado.getString("fechaSalida"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * Nos devuelve los lugares y los mete en el combo
     */
	public void getLugarBox() {
		try {
		Database=new Conecciones();	
		PreparedStatement lista = Database.conexion.prepareStatement("select * from Lugares");
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
     * Getter de la lista de los articulos
     */
	public List<tablaMostrar> getListSalidaArticulo() {
		return listSalidaArticulos.get();
	}
}
