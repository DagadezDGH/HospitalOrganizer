package dad.hospitalorganizer.dialogs;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.models.Articulo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class modificarArticuloDialog extends Dialog<Articulo> {

	// Cargamos los elementos del FXML

	@FXML
	private TextArea descripciontxt;

	@FXML
	private TextField nombretxt;

	@FXML
	private TextField cantidadtxt;

	@FXML
	private TextField ubicaciontxt;

	// Creamos los elementos del modelo

	private ButtonType okButton, cancelButton;

	private Articulo submited;

	ObservableList<String> hotelesList = FXCollections.observableArrayList(new ArrayList<String>());
	insertDialogModel model = new insertDialogModel();

	public modificarArticuloDialog(Articulo submited) throws IOException {

		this.submited = submited;

		setTitle("Modificar articulo");
		setHeaderText("Cambia los datos:");
		setContentText("Cambia todos los datos para modificar");

		okButton = new ButtonType("Modificar", ButtonData.OK_DONE);
		cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/modificarArticuloDialogView.fxml"));
		fxmlLoader.setController(this);
		getDialogPane().setContent(fxmlLoader.load());

		nombretxt.setText(submited.getNombre());
		descripciontxt.setText(submited.getDescripcion());
		cantidadtxt.setText(Integer.toString(submited.getCantidad()));
		ubicaciontxt.setText(submited.getUbicacion());

		setResultConverter(bt -> {

			if (bt.getButtonData() == ButtonData.OK_DONE) {
				onModifyBttn(ButtonData.OK_DONE);
			}

			else {
				return null;
			}
			return null;
		});

	}

	private void onModifyBttn(ButtonData data) {

		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("CONFIRMACION");
		confirmation.setHeaderText("¿Seguro que quieres modificiar esta estancia?");
		confirmation.setContentText("Antiguos valores: " + "\n Nombre del articulo: " + submited.getNombre() + "\n"
				+ "\n Ubicacion: " + submited.getUbicacion() + "\n" + "\n Cantidad: " + submited.getCantidad() + "\n"
				+ "\n Descripcion: " + submited.getDescripcion() + "\n Nuevos valores: " + "\n Nombre del articulo: "
				+ nombretxt.getText() + "\n" + "\n Ubicacion: " + ubicaciontxt.getText() + "\n" + "\n Cantidad: "
				+ cantidadtxt.getText() + "\n" + "\n Descripcion: " + descripciontxt.getText());

		Optional<ButtonType> result = confirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {

				Conecciones conections = new Conecciones();

				PreparedStatement prep = conections.conexion
						.prepareStatement("update Articulos set nombre = (?),ubicacion = (?),"
								+ "cantidad = (?), descripcion = (?) where codArticulo = (?)");
				prep.setString(1, nombretxt.getText());
				prep.setString(2, ubicaciontxt.getText());
				prep.setInt(3, Integer.parseInt(cantidadtxt.getText()));
				prep.setString(4, descripciontxt.getText());
				prep.setInt(5, submited.getCodArticulo());
				prep.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
