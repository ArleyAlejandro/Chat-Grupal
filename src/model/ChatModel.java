package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatModel {

	private Connection connection;

	public ChatModel() {
		DBConnection db = DBConnection.getInstance();
		connection = db.getConnection();

	}

	/**
	 * Método para conectar con la base de datos usando un nombre de usaurio.
	 * @param userName
	 * @return true o false, según el exito de la ejecución
	 */
	public boolean executeConnection(String userName) {
		if (connection == null) {
			System.out.println("No hay conexión a la base de datos.");
			return false;
		}

		String query = "{ CALL connect(?) }";
		try (CallableStatement stmt = connection.prepareCall(query)) {
			stmt.setString(1, userName);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al ejecutar el procedimiento 'connect'.");
			return false;
		}
	}

	/**
	 * Método para hacer la desconexión a la base de datos
	 */
	public void disconnect() {
		String query = "{ CALL disconnect() }";
		try (CallableStatement stmt = connection.prepareCall(query)) {
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al ejecutar el procedimiento 'disconnect'.");
		}
	}

	/**
	 * Método para devolver la lista de usuarios recibida desde la base de datos
	 * @return List<String> users
	 */
	public List<String> getConnectedUsers() {
		List<String> users = new ArrayList<>();
		String query = "{ CALL getConnectedUsers() }";
		try (CallableStatement stmt = connection.prepareCall(query); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				users.add(rs.getString("nick"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al ejecutar el procedimiento 'getConnectedUsers'.");
		}
		return users;
	}

	/**
	 * Devuelve una lista con los mensajes recibidos desde la base de datos
	 * 
	 * @return List<String> messages
	 */
	public List<Mensaje> getMessagesFromDB() {
	    List<Mensaje> messages = new ArrayList<>();
	    String query = "{ CALL getMessages() }";

	    try (CallableStatement stmt = connection.prepareCall(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {  
	            String user = rs.getString("nick");  
	            String message = rs.getString("message"); 
	            String timestamp = rs.getString("ts");  

	            Mensaje fullMessage = new Mensaje(user, message, timestamp);
	            messages.add(fullMessage);

	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println(" Error al ejecutar el procedimiento 'getMessages'.");
	    }

	    return messages;
	}



	/**
	 * Envía un mensaje a la base de datos
	 * 
	 * @param message mensaje recibido desde el input de la vista
	 */
	public boolean sendMessage(String message) {

		String query = "{ CALL send(?) }";
		try (CallableStatement stmt = connection.prepareCall(query)) {
			stmt.setString(1, message);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al ejecutar el procedimiento 'send'.");
			return false;
		}

	}

}
