package dad.hospitalorganizer.bot;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;

import dad.hospitalorganizer.connections.Conecciones;
import dad.hospitalorganizer.controller.EntradaVerController;
import dad.hospitalorganizer.controller.SalidaVerController;
/**
 * @author David Castellano David Garrido Carlos Cosme
 */
public class BotHospital {
	private Conecciones Database;
	TelegramBot bot = new TelegramBot("token");
	boolean esperandoVariable = false;
	/**
	 * Inicia el hilo del bot y pone un listener para los comandos
	 */
	public BotHospital() {
		
		Database= new Conecciones();
		
		bot.setUpdatesListener(updates -> {

			updates.stream().forEach(u -> {
				
				if (u.message().text().equals("Hola")) {

					bot.execute(new SendMessage(u.message().chat().id(), "Hola"));
					
				}
				if (u.message().text().equals("Adios")) {

					bot.execute(new SendMessage(u.message().chat().id(), "Buenas noches"));
					
				}
				if (u.message().text().equals("/ultimoinformesalida")) {
					enviarSalida(u.message().chat().id());
				}
				if (u.message().text().equals("/ultimoinformeentrada")) {
					enviarEntrada(u.message().chat().id());
				}
				if (u.message().text().equals("/queryarticulos")) {
					try {
						queryArticulos(u.message().chat().id());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if (u.message().text().equals("/queryultimasalida")) {
					try {
						querySalida(u.message().chat().id());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}if (u.message().text().equals("/queryultimaentrada")) {
					try {
						queryEntrada(u.message().chat().id());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
				

			});
			return UpdatesListener.CONFIRMED_UPDATES_ALL;
		});

	}
	/**
	 * @param id es el identidicador del chat
	 * Envia el ultimo pdf de salida
	 */
	public void enviarSalida(Long id) {
		bot.execute(new SendDocument(id, new File("Salida.pdf")));
	}
	/**
	 * @param id es el identidicador del chat
	 * Envia el ultimo pdf de entrada
	 */
	public void enviarEntrada(Long id) {
		bot.execute(new SendDocument(id, new File("Entrada.pdf")));
		System.out.println(id);
	}
	/**
	 * @param id es el identidicador del chat
	 * Devuelve los articulos que queden pocas unidades
	 */
	public void queryArticulos(Long id) throws SQLException {
		
		PreparedStatement lista = Database.conexion.prepareStatement("Select * from articulos where cantidad< 30");
		ResultSet resultado; 
		String respuesta;
		resultado = lista.executeQuery();
			while (resultado.next()) {
				respuesta="El articulo "+resultado.getString("nombre") + " con codigo "+resultado.getInt("codArticulo")+" le quedan "+resultado.getInt("cantidad") +" existencias." ;
				bot.execute(new SendMessage(id,respuesta));
			}
		
	}
	/**
	 * @param id es el identidicador del chat
	 * Devuelve la ultima salida
	 */
	public void querySalida(Long id) throws SQLException {
		
		PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM salidas ORDER BY salidas.codSalida DESC LIMIT 1");
		ResultSet resultado; 
		String respuesta;
		resultado = lista.executeQuery();
			while (resultado.next()) {
				respuesta="La ultima salida fue "+resultado.getString("fechaSalida") + 
						" con codigo "+resultado.getInt("codSalida")+" en direccion a "+resultado.getInt("lugar") +" para "+resultado.getString("motivoSalida") ;
				bot.execute(new SendMessage(id,respuesta));
			}
		
	}
	/**
	 * @param id es el identidicador del chat
	 * Devuelve la ultima entrada
	 */
	public void queryEntrada(Long id) throws SQLException {
		
		PreparedStatement lista = Database.conexion.prepareStatement("SELECT * FROM entradas INNER JOIN proveedores on entradas.codProveedor=proveedores.codProveedor ORDER BY entradas.codEntrada DESC LIMIT 1");
		ResultSet resultado; 
		String respuesta;
		resultado = lista.executeQuery();
			while (resultado.next()) {
				respuesta="La ultima entrada fue "+resultado.getString("fechaEntrada") + 
						" con codigo "+resultado.getInt("codEntrada")+" hecho por "+resultado.getString("nombre") ;
				bot.execute(new SendMessage(id,respuesta));
			}
		
	}
}
