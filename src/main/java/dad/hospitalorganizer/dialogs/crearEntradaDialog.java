package dad.hospitalorganizer.dialogs;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.models.Entrada;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class crearEntradaDialog extends Dialog<Entrada>{
	//model
	Conecciones conections = new Conecciones();
	private ListProperty<String> proveedorProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	//view
	@FXML
	private ComboBox<String> proveedorBox;
	
	@FXML
	private GridPane view;
	
	@FXML
	private DatePicker entradaDatePicker;
	
    @FXML
    private TextField dniTxt;
	
	private ButtonType okButton, cancelButton;
	
	public crearEntradaDialog () throws IOException {
		setTitle("Insertar entrada");
		setHeaderText("Rellena los datos:");
		setContentText("Rellene todos los datos para insertar");

		okButton = new ButtonType("Insertar", ButtonData.OK_DONE);
		cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/crearEntradaDialogView.fxml"));
		fxmlLoader.setController(this);
		getDialogPane().setContent(fxmlLoader.load());
		try {
			PreparedStatement lista = conections.conexion.prepareStatement("select * from Proveedores");
			ResultSet resultado;
			resultado = lista.executeQuery();
			while (resultado.next()) {
				proveedorBox.getItems().add(resultado.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setResultConverter(bt -> {

			if (bt.getButtonData() == ButtonData.OK_DONE) {
				onInsertEntradaAction(ButtonData.OK_DONE);
				Alert confirmation = new Alert(AlertType.CONFIRMATION);
				confirmation.setTitle("CONFIRMACION");
				confirmation.setHeaderText("¿Seguro que quieres crear esta entrada?");
				
				Optional<ButtonType> result = confirmation.showAndWait();
				if (result.get() == ButtonType.OK) {
				} else {
				Alert confirm = new Alert(AlertType.ERROR);
				confirm.setTitle("ERROR");
				confirm.setHeaderText("Error en la entrada");
				confirm.setContentText("Asegúrese de que la escribió correctamente.");}
			}

			else {
				return null;
			}
			return null;
		});
	}
	 private void onInsertEntradaAction(ButtonData data) {
		 try {
			PreparedStatement cod = conections.conexion.prepareStatement("select codProveedor from Proveedores where nombre=" +"'"+ proveedorBox.getSelectionModel().getSelectedItem().toString()+"'");
			ResultSet resultado = cod.executeQuery();
			while (resultado.next()) {
				proveedorProperty.add(resultado.getString("codProveedor"));
				}
		 PreparedStatement prep = conections.conexion.prepareStatement(
					"insert into Entradas (codProveedor, dni, fechaEntrada) values ((?), (?), (?))");
			prep.setString(1, proveedorProperty.get(0));
			prep.setString(2, dniTxt.getText());
			prep.setString(3, entradaDatePicker.getValue()+"");
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
