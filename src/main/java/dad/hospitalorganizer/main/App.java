package dad.hospitalorganizer.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class App extends Application {
	private static Stage primaryStage; 
//	private HospitalController hospitalController;
	@Override
	public void start(Stage primaryStage) throws Exception {
//		Scene scene = new Scene(hospitalController.getView());
		
		primaryStage.setTitle("HospitalOrganizer");
//		primaryStage.getIcons().add(new Image("/images/icon-64x64.png"));
//		primaryStage.setScene(scene);
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

}
