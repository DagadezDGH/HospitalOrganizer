package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.main.App;
import dad.hospitalorganizer.models.Usuario;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class LoginController implements Initializable {
	private Conecciones conect = new Conecciones();
	private String nombre;
	private String dni;
	private String contrasena;
	StringProperty userProperty = new SimpleStringProperty();
	StringProperty passwordProperty = new SimpleStringProperty();
	StringProperty nombreProperty = new SimpleStringProperty();
	@FXML
	private PasswordField contraseñaText;

	@FXML
	private Button entrarButton;

	@FXML
	private TextField usuarioText;

	@FXML
	private GridPane view;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userProperty.bind(usuarioText.textProperty());
		passwordProperty.bind(contraseñaText.textProperty());
	}

	public LoginController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@FXML
	void onClickEntrar(ActionEvent event) throws SQLException {
		int resultado = 0;
		nombre="pepe";
		dni="pepe";
		contrasena="pepe";
		PreparedStatement list = conect.conexion.prepareStatement("select * from Usuarios where dni=? AND contraseña=?");
		list.setString(1, userProperty.getValue());
		list.setString(2, passwordProperty.getValue());
		ResultSet result = list.executeQuery();
		while (result.next()) {
			nombre = result.getString("nombre");	
			dni = result.getString("dni");
			contrasena = result.getString("contraseña");
		}
		if(nombre!="pepe") {
			resultado=1;
			
		}
		System.out.println(resultado);
		System.out.println(nombre+"String");
		System.out.println(dni+"String");
		System.out.println(contrasena+"String");
		System.out.println(userProperty.getValue());
		System.out.println(passwordProperty.getValue());
		
		 
		if (dni != "pepe" && contrasena != "pepe") { //como null no funcionaba nuestro amigo pepe nos lo arreglo
			nombreProperty.setValue(nombre);
			App.usuario.setNombre("Bienvenido " + nombre);
			App.usuario.setDni(dni);
			App.usuario.setContraseña(contrasena);
			App.goToMain();
		}
//		
//		if (dni != userProperty.getValue() && contrasena != passwordProperty.getValue()) { //como null no funcionaba nuestro amigo pepe nos lo arreglo
//			nombreProperty.setValue(nombre);
//			App.usuario.setNombre("Bienvenido " + nombre);
//			App.usuario.setDni(dni);
//			App.usuario.setContraseña(contrasena);
//			App.goToMain();
//		} 
		else {
			Alert confirm = new Alert(AlertType.ERROR);
			confirm.setTitle("ERROR");
			confirm.setHeaderText("Error en el usuario o contraseña");
			confirm.setContentText("Asegúrese de que escribió correctamente los campos.");
			confirm.showAndWait();
		}
	}

	public GridPane getView() {
		return view;
	}
	
}
