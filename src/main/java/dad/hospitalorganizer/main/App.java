package dad.hospitalorganizer.main;


import dad.hospitalorganizer.controller.EntradaController;


import dad.hospitalorganizer.controller.EntradaFormController;
import dad.hospitalorganizer.controller.InventarioController;
import dad.hospitalorganizer.controller.LoginController;
import dad.hospitalorganizer.controller.MainController;
import dad.hospitalorganizer.controller.SalidasController;
import dad.hospitalorganizer.controller.SalidasFormController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	
	private static Stage primaryStage; 
	private EntradaController entradaController;
	private EntradaFormController entradaFormController;
	private InventarioController inventarioControlller;
	private SalidasController salidasController;
	private LoginController loginController;
	private MainController mainController;
	private SalidasFormController salidasFormController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		entradaController=new EntradaController();
		entradaFormController=new EntradaFormController();
		inventarioControlller=new InventarioController();
		salidasController=new SalidasController();
		salidasFormController=new SalidasFormController();
		loginController = new LoginController();
		mainController = new MainController();
		
		//Scene scene = new Scene(entradaController.getView());
		//Scene scene = new Scene(entradaFormController.getView());
		//Scene scene = new Scene(inventarioControlller.getView());
		//Scene scene = new Scene(salidasController.getView());
		//Scene scene = new Scene(salidasFormController.getView());
		Scene scene = new Scene(loginController.getView());
		//Scene scene = new Scene(mainController.getView());
		primaryStage.setTitle("HospitalOrganizer");
		primaryStage.getIcons().add(new Image("/images/icon-64x64.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	public static void error(String header) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.showAndWait();
	}
	
	public static void info(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	public static void confirmation(String header) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Confirmación");
		alert.setHeaderText(header);
		alert.showAndWait();
	}
}
