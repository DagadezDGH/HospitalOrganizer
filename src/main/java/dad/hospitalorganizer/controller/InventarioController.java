package dad.hospitalorganizer.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.models.Articulo;
import dialogs.crearArticuloDialog;
import dialogs.modificarArticuloDialog;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class InventarioController implements Initializable{
	
	@FXML
    private GridPane view;

    @FXML
    private ImageView iconoInventario;

    @FXML
    private TableView<Articulo> tablaArticulos;

    @FXML
    private TableColumn<Articulo, String> articuloColumn;

    @FXML
    private TableColumn<Articulo, String> nombreColumn;

    @FXML
    private TableColumn<Articulo, String> descripcionColumn;

    @FXML
    private TableColumn<Articulo, String> ubicacionColumn;

    @FXML
    private TableColumn<Articulo, String> cantidadColumn;

    @FXML
    private Button crearButton;

    @FXML
    private Button modificarButton;

    @FXML
    private Button eliminarButton;
    Conecciones Database ;
    String codigo;
    
    private ObservableList<Articulo> listArticulos = FXCollections.observableArrayList();
	private ListProperty<Articulo> listaArticulos = new SimpleListProperty<Articulo>(listArticulos);
  	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Database = new Conecciones();
		
		articuloColumn.setCellValueFactory(v -> new SimpleStringProperty(""+v.getValue().getCodArticulo()));
		nombreColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getNombre()));
		descripcionColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getDescripcion()));
		ubicacionColumn.setCellValueFactory(v -> new SimpleStringProperty( v.getValue().getUbicacion()));
		cantidadColumn.setCellValueFactory(v -> new SimpleStringProperty(""+v.getValue().getCantidad()));
		
		
		tablaArticulos.itemsProperty().bind(listaArticulos);
		tablaArticulos.getSelectionModel().selectedItemProperty().addListener((ob, ol, n) -> {
			if (n != null) {
				codigo="" + tablaArticulos.getSelectionModel().getSelectedItem().getCodArticulo();
			}
		});
		
		try {
			actualizar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public InventarioController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InventarioView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
    @FXML
    void onClickCrear(ActionEvent event) throws SQLException {
    	try {
			
    		crearArticuloDialog dialog = new crearArticuloDialog();
			dialog.showAndWait();
			actualizar();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void onClickEliminar(ActionEvent event) {
    	Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("CONFIRMACION");
		confirmation.setHeaderText("¿Seguro que quieres eliminar este articulo?");
		confirmation.setContentText("El articulo con codigo " + codigo + " será eliminado.");
		
		Optional<ButtonType> result = confirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
		try {
			Conecciones conections = new Conecciones();
			PreparedStatement delete = conections.conexion
					.prepareStatement("delete from articulos where codArticulo = (?)");
			delete.setString(1, codigo);
			delete.executeUpdate();
			
			conections.conexion.close();
			actualizar();
    } catch (SQLException e) {
		Alert confirm = new Alert(AlertType.ERROR);
		confirm.setTitle("ERROR");
		confirm.setHeaderText("El articulo no existe");
		confirm.setContentText("Asegúrese de que la escribió correctamente.");
		}}

	}

    @FXML
    void onClickModificar(ActionEvent event) throws SQLException {
    	Articulo submited = null;
        
        for(int i = 0; i < listArticulos.size(); i++){
            if(listArticulos.get(i).getCodArticulo()==Integer.parseInt(codigo)){
                submited = listArticulos.get(i);
            }
        }
        if(submited != null){
            try {
			
            	modificarArticuloDialog dialog = new modificarArticuloDialog(submited);
			dialog.showAndWait();
			actualizar();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }else{
                Alert error = new Alert(AlertType.ERROR);
                error.setTitle("ERROR");
                error.setHeaderText("Algo está mal");
                error.setContentText("Asegúrate de que has puesto bien el codigo del articulo.");
                
                error.showAndWait();
            }
        
    }
    
	public GridPane getView() {
		return view;
	}
	
	public void actualizar() throws SQLException {
		
	
		PreparedStatement lista = Database.conexion.prepareStatement("select * from articulos");
		ResultSet resultado = lista.executeQuery();
		while (resultado.next()) {
				listArticulos.add(new Articulo(resultado.getInt("codArticulo"),
						resultado.getString("nombre"), 
						resultado.getString("descripcion"),
						resultado.getString("ubicacion"),
						resultado.getInt("cantidad")));
		}
		
		
		
	}
}
