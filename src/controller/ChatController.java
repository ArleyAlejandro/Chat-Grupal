package controller;

import model.ChatModel;
import model.DBConnection;
import model.Mensaje;
import view.ChatView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ChatController {
	private ChatView vista;
	public ChatController(ChatView vista) {
		this.vista = vista;

		// Carga de usuarios conectados, al iniciar la app
		loadUsers();

		// listener al botón "Connect"
		this.vista.addConnectListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// listener al botón "Disconnect"
		this.vista.addDisconnectListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					disconect();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// listener al botón "Send"
		this.vista.addSendListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
	}

	/**
	 * Método para conectar a la base de datos a partir de un nombre de usuario.
	 * 
	 * @throws SQLException
	 */
	private void connect() throws SQLException {

		String userName = vista.getUserName();

		if (userName == null || userName.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre de usuario.");
			return;
		}

		ChatModel model = new ChatModel();

		// Ejecutar el procedimiento almacenado con el nombre del usuario
		boolean succesConnection = model.executeConnection(userName);

		if (succesConnection) {
			loadMessages();
			JOptionPane.showMessageDialog(null, "Conectado como: " + userName);
		} else {
			JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
		}
	}

	private void disconect() throws SQLException {
	
		ChatModel model = new ChatModel();
		model.disconnect();

		// Llamar a actualizarUsuariosConectados para reflejarlo en la vista
		DefaultListModel<String> userModel = vista.getModelUsers();
		userModel.clear();
	}

	public void loadUsers() {
		
		ChatModel model = new ChatModel();
		DBConnection dbConnection = DBConnection.getInstance();
		
		// Recibe los usuarios conectados, desde el modelo.
		List<String> users = model.getConnectedUsers();

		// Refleja los usuarios conectados en la vista.
		DefaultListModel<String> modeloUsuarios = vista.getModelUsers();
		modeloUsuarios.clear();
		for (String usuario : users) {
			modeloUsuarios.addElement(usuario);
		}
	}

	public void loadMessages() {
		ChatModel model = new ChatModel();
		DBConnection dbConnection = DBConnection.getInstance();

		List<Mensaje> messageList = model.getMessagesFromDB();

		// Actualizar la vista
		DefaultListModel<String> msgModel = vista.getModelMessages();

		for (Mensaje message : messageList) {
			msgModel.addElement(message.toString());
		}
	}

	public void sendMessage() {
		String message = vista.getMessage();

		if (message == null || message.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, ingrese un mensaje.");
			return;
		}

		ChatModel model = new ChatModel();
		boolean isSendedMessage = model.sendMessage(message);

		if (isSendedMessage) {
			loadMessages();
		} else {
			JOptionPane.showMessageDialog(null, "Error al enviar el mensaje.");
		}
	}

}
