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
	
	static Scene inventario;
	static Scene main;
	static Scene entrada;
	static Scene entradaForm;
	static Scene salidas;
	static Scene salidasForm;
	static Scene login;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		App.primaryStage = primaryStage;
		
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
		login = new Scene(loginController.getView());
		main = new Scene(mainController.getView());
		inventario = new Scene(inventarioControlller.getView());
		entrada = new Scene(entradaController.getView());
		entradaForm = new Scene(entradaFormController.getView());
		salidas = new Scene(salidasController.getView());
		salidasForm = new Scene(salidasFormController.getView());
		
		primaryStage.setScene(main);
		primaryStage.setTitle("HospitalOrganizer");
		primaryStage.getIcons().add(new Image("/images/icon-64x64.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
	public static void goToMain() {
		primaryStage.setScene(main);
	}
	public static void goToInventario() {
		primaryStage.setScene(inventario);
	}
	public static void goToEntrada() {
		primaryStage.setScene(entrada);
	}
	public static void goToEntradaForm() {
		primaryStage.setScene(entradaForm);
	}
	public static void goToSalidas() {
		primaryStage.setScene(salidas);
	}
	public static void goToSalidasForm() {
		primaryStage.setScene(salidasForm);
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
