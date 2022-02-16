package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Entrada;
import dad.hospitalorganizer.models.Salida;
import dad.hospitalorganizer.models.SalidaArticulo;
import javafx.fxml.Initializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SalidasFormController implements Initializable {
	private ListProperty<Salida> listSalida=new SimpleListProperty<Salida>(FXCollections.observableArrayList()); 
	private Conecciones Database;
	@FXML
	private GridPane view;

	@FXML
	private TableView<Salida> tableArticulos;

	@FXML
	private TableColumn<Salida, String> codSalidaColumn;

	@FXML
	private TableColumn<Salida, String> lugarSalidaColumn;

	@FXML
	private TableColumn<Salida, String> motivoColumn;

	@FXML
	private TableColumn<Salida, String> pacienteColumn;

	@FXML
	private TableColumn<Salida, String> fechaColumn;

	@FXML
	private Button crearButton;

	@FXML
	private TextField lugarText;

	@FXML
	private TextField motivoText;

	@FXML
	private ComboBox<?> pacienteCombo;

	@FXML
	private ComboBox<?> articulosCombo;

	@FXML
	private TextField cantidadText;
    
	@FXML
    private Button volverButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Database = new Conecciones();
		codSalidaColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getCodSalida()));
		lugarSalidaColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getLugar()));
		motivoColumn.setCellValueFactory(v -> new SimpleStringProperty("" + v.getValue().getMotivo()));
		pacienteColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getCodPaciente()));
		fechaColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getFechaSalida().toString()));
		tableArticulos.itemsProperty().bind(listSalida);
		try {
			actualizar();
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

	public GridPane getView() {
		return view;
	}

	@FXML
	void onClickAnadir(ActionEvent event) {

	}

	@FXML
	void onClickCrear(ActionEvent event) {

	}
    @FXML
    void OnVolverAction(ActionEvent event) {
    	App.goToMain();
    }
    public void actualizar() throws SQLException {
		listSalida.clear();
		try {	
			PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM salidas");
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				listSalida.add(new Salida(resultado.getInt("codSalida"),resultado.getString("lugarSalida"),resultado.getString("motivoSalida"),resultado.getString("paciente"), resultado.getDate("fechaSalida")));
			}
		} catch (Exception e) {
		 		e.getStackTrace();
		}
		}
}
