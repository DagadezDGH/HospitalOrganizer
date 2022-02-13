package dad.hospitalorganizer.connections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class Conecciones {

	public Connection conexion;
	
	
	public Conecciones() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			conexion = DriverManager.getConnection("jdbc:mysql://atlas.profesionalhosting.com:3306/dmzeytnr_","twodavid","");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Hospitaldb","root","");
		} catch (ClassNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Driver no encontrado");
			alert.setContentText("Consulta con admin para más información");

			alert.showAndWait();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Algo falló en la consulta");
			alert.setContentText("Consulta con admin para más información");

			alert.showAndWait();
		}
	}
}
