package controller;

import model.DBConnection;
import view.ChatView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ChatController {
	private ChatView vista;

	public ChatController(ChatView vista) {
		this.vista = vista;

		// Agregar el listener al botón "Connect"
		this.vista.addConnectListenerConnect(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					conectarBD();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Listener para desconectar
		this.vista.addConnectListenerDisconnect(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					desconectarBD();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void conectarBD() throws SQLException {
		String user = vista.getUser();

		if (user == null || user.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre de usuario.");
			return;
		}

		// Obtener la instancia de DBConnection
		DBConnection dbConnection = DBConnection.getInstance();

		// Ejecutar el procedimiento almacenado con el nombre del usuario
		boolean conexionExitosa = dbConnection.ejecutarConexion(user);

		if (conexionExitosa) {
			JOptionPane.showMessageDialog(null, "Conectado como: " + user);
			cargarUsuariosConectados();
		} else {
			JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
		}
	}

	private void desconectarBD() throws SQLException {
		// Obtener la instancia de DBConnection
		DBConnection dbConnection = DBConnection.getInstance();

		// Ejecutar el procedimiento almacenado para desconectar
		dbConnection.desconectar();

		// Llamar a actualizarUsuariosConectados para reflejarlo en la vista
		DefaultListModel<String> modeloUsuarios = vista.getModeloUsuarios();
		modeloUsuarios.clear(); 
		
		JOptionPane.showMessageDialog(null, "Desconectado de la base de datos.");
	}

	public void cargarUsuariosConectados() {
		DBConnection dbConnection = DBConnection.getInstance();
		List<String> usuarios = dbConnection.obtenerUsuariosConectados();

		// Llamar a actualizarUsuariosConectados para reflejarlo en la vista
		DefaultListModel<String> modeloUsuarios = vista.getModeloUsuarios();
		modeloUsuarios.clear(); 
		for (String usuario : usuarios) {
			modeloUsuarios.addElement(usuario);
		}
	}

}
