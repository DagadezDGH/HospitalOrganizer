package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable{
	

	

	    @FXML
	    private AnchorPane view;

	    @FXML
	    private TextField usuario;

	    @FXML
	    private TextField contraseña;

	    @FXML
	    private Button entrar;

	    @FXML
	    private Label errorlbl;

	    @FXML
	    void onClickEntrar(ActionEvent event) {

	    }
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public LoginController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
		loader.setController(this);
		loader.load();
	}

}
