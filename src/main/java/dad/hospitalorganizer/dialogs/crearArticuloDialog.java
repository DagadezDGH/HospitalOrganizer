package dad.hospitalorganizer.dialogs;

/**
 * @author Carlos Javier Cosme Melian
 */

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.models.Articulo;
import java.sql.CallableStatement;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class crearArticuloDialog extends Dialog<Articulo> {



    @FXML
    private TextArea descripciontxt;

    @FXML
    private TextField nombretxt;

    @FXML
    private TextField cantidadtxt;

    @FXML
    private TextField ubicaciontxt;

    @FXML
    private VBox view;


	private ButtonType okButton, cancelButton;
	


	ObservableList<String> hotelesList = FXCollections.observableArrayList(new ArrayList<String>());
	insertDialogModel model = new insertDialogModel();


	public crearArticuloDialog() throws IOException {

		

		setTitle("Insertar articulo");
		setHeaderText("Rellena los datos:");
		setContentText("Rellene todos los datos para insertar");

                

		okButton = new ButtonType("Insertar", ButtonData.OK_DONE);
		cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/crearArticuloDialogView.fxml"));
		fxmlLoader.setController(this);
		getDialogPane().setContent(fxmlLoader.load());
			
			 
		setResultConverter(bt -> {
                                
				if (bt.getButtonData() == ButtonData.OK_DONE) {
					onInsertBttn(ButtonData.OK_DONE);
				}

				else {
					return null;
				}
				return null;
			});
			

	}


	private void onInsertBttn(ButtonData data) {


		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("CONFIRMACION");
		confirmation.setHeaderText("¿Seguro que quieres insertar este articulo?");
		confirmation.setContentText("Datos: \n  "+ 
				"Nombre del articulo: "+ nombretxt.getText() +
				"\n Ubicacion: " + ubicaciontxt.getText() +
				"\n Cantidad: " + cantidadtxt.getText()+
				"\n Descripcion: " + descripciontxt.getText());

		Optional<ButtonType> result = confirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {

				Conecciones conections = new Conecciones();
				 
				

					PreparedStatement prep = conections.conexion.prepareStatement(
							"insert into articulos ( nombre, ubicacion, cantidad, descripcion) values ((?), (?), (?), (?))");

					prep.setString(1, nombretxt.getText());
					prep.setString(2, ubicaciontxt.getText());
					prep.setInt(3, Integer.parseInt( cantidadtxt.getText()));
					prep.setString(4,   descripciontxt.getText());
					prep.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
