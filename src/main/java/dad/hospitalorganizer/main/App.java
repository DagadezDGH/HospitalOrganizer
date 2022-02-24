package dad.hospitalorganizer.main;


import dad.hospitalorganizer.bot.BotHospital;
import dad.hospitalorganizer.controller.EntradaController;


import dad.hospitalorganizer.controller.EntradaFormController;
import dad.hospitalorganizer.controller.EntradaVerController;
import dad.hospitalorganizer.controller.InventarioController;
import dad.hospitalorganizer.controller.LoginController;
import dad.hospitalorganizer.controller.MainController;
import dad.hospitalorganizer.controller.SalidaVerController;
import dad.hospitalorganizer.controller.SalidasController;
import dad.hospitalorganizer.controller.SalidasFormController;
import dad.hospitalorganizer.models.Usuario;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author David Castellano David Garrido Carlos Cosme
 * Controla todas las escenas y los controllers
 */
public class App extends Application {
	private static Stage primaryStage; 
	
	public static Usuario usuario = new Usuario();
	
	private EntradaController entradaController;
	private EntradaFormController entradaFormController;
	private EntradaVerController entradaVerController; 
	private InventarioController inventarioControlller;
	private SalidasController salidasController;
	private LoginController loginController;
	private MainController mainController;
	private SalidasFormController salidasFormController;
	private SalidaVerController salidasVerController;
	
	static Scene inventario;
	static Scene main;
	static Scene entrada;
	static Scene entradaForm;
	static Scene entradaVer;
	static Scene salidas;
	static Scene salidasForm;
	static Scene salidasVer;
	static Scene login;
	/**
	 * Instancia las escenas con sus vistas
	 * @param primaryStage 
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		App.primaryStage = primaryStage;
		
		entradaController=new EntradaController();
		entradaFormController=new EntradaFormController();
		entradaVerController=new EntradaVerController();
		inventarioControlller=new InventarioController();
		salidasController=new SalidasController();
		salidasVerController=new SalidaVerController();
		salidasFormController=new SalidasFormController();
		loginController = new LoginController();
		mainController = new MainController();

		login = new Scene(loginController.getView());
		main = new Scene(mainController.getView());
		inventario = new Scene(inventarioControlller.getView());
		entrada = new Scene(entradaController.getView());
		entradaForm = new Scene(entradaFormController.getView());
		entradaVer = new Scene(entradaVerController.getView());
		salidas = new Scene(salidasController.getView());
		salidasForm = new Scene(salidasFormController.getView());
		salidasVer =new Scene(salidasVerController.getView());
		
		primaryStage.setScene(login);
		primaryStage.setTitle("HospitalOrganizer");
		primaryStage.getIcons().add(new Image("/images/icon-64x64.png"));
		primaryStage.show();
		BotHospital bot = new BotHospital();
	}

	public static void main(String[] args) {
		launch(args);

	}
	/**
	 * Cambia a la escena Main
	 * */
	public static void goToMain() {
		primaryStage.setScene(main);
	}
	/**
	 * Cambia a la escena Inventario
	 * */
	public static void goToInventario() {
		primaryStage.setScene(inventario);
	}
	/**
	 * Cambia a la escena Entrada
	 * */
	public static void goToEntrada() {
		primaryStage.setScene(entrada);
	}
	/**
	 * Cambia a la escena rellenar Entrada
	 * */
	public static void goToEntradaForm() {
		primaryStage.setScene(entradaForm);
	}
	/**
	 * Cambia a la escena ver Entrada
	 * */
	public static void goToEntradaVer() {
		primaryStage.setScene(entradaVer);
	}
	/**
	 * Cambia a la escena Salidas
	 * */
	public static void goToSalidas() {
		primaryStage.setScene(salidas);
	}
	/**
	 * Cambia a la escena rellenar Salidas
	 * */
	public static void goToSalidasForm() {
		primaryStage.setScene(salidasForm);
	}
	/**
	 * Cambia a la escena ver Salidas
	 * */
	public static void goToSalidasVer() {
		primaryStage.setScene(salidasVer);
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
