package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChatView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldMsg;
	private JTextField textFieldName;
	private JButton connectButton;
	private JButton disconnectButton;
	private JButton sendButton;
	private ActionListener listener;

	private JPanel userList;
	private DefaultListModel<String> modeloUsuarios;
	private JList<String> listaUsuarios;

	/**
	 * Constructor: Configura la ventana principal
	 */
	public ChatView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);

		// Panel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Botón "Connect"
		connectButton = new JButton("Connect");
		connectButton.setBounds(26, 12, 105, 27);
		contentPane.add(connectButton);

		// Botón "Disconnect"
		disconnectButton = new JButton("Disconnect");
		disconnectButton.setBounds(317, 12, 105, 27);
		contentPane.add(disconnectButton);

		// Panel de chat
		JPanel chatBox = new JPanel();
		chatBox.setBounds(26, 50, 241, 399);
		chatBox.setBorder(new LineBorder(Color.GRAY, 1));
		contentPane.add(chatBox);

		// Lista de usuarios
		userList = new JPanel();
		userList.setBounds(289, 50, 133, 399);
		userList.setBorder(new LineBorder(Color.GRAY, 1));
		contentPane.add(userList);

		inicializarListaUsuarios();

		// Panel de entrada de mensajes
		JPanel msgInput = new JPanel();
		msgInput.setBounds(26, 464, 396, 35);
		msgInput.setLayout(null);
		contentPane.add(msgInput);

		// Campo de texto para mensajes
		textFieldMsg = new JTextField();
		textFieldMsg.setBounds(0, 5, 279, 25);
		msgInput.add(textFieldMsg);
		textFieldMsg.setColumns(10);

		// Botón "Send"
		sendButton = new JButton("Send");
		sendButton.setBounds(291, 4, 105, 27);
		msgInput.add(sendButton);

		// Campo de texto para nombre de usuario
		textFieldName = new JTextField();
		textFieldName.setBounds(143, 13, 118, 25);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);

		// Acción al conectar
		// Dentro del constructor de MainWindow
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = getUser();
				if (!userName.isEmpty()) {
					// Llamar al controlador para manejar la conexión
					if (listener != null) {
						listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, userName));
					}
				} else {
					System.out.println("Por favor, introduce un nombre.");
				}
			}
		});

		// Acción al enviar mensaje
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mensaje enviado: " + textFieldMsg.getText().trim());
			}
		});
	}

	/**
	 * Devuelve el nombre de usuario ingresado en el campo de texto
	 */
	public String getUser() {
		return textFieldName.getText().trim();
	}

	/**
	 * Permite al controlador agregar un listener al botón "Connect"
	 */
	public void addConnectListenerConnect(ActionListener listener) {
		connectButton.addActionListener(listener);
	}

	public void addConnectListenerDisconnect(ActionListener listener) {
		disconnectButton.addActionListener(listener);
	}

	private void inicializarListaUsuarios() {
		modeloUsuarios = new DefaultListModel<>();
		listaUsuarios = new JList<>(modeloUsuarios);

		JScrollPane scrollPane = new JScrollPane(listaUsuarios);
		scrollPane.setPreferredSize(new Dimension(130, 395));

		userList.setLayout(new BorderLayout());
		userList.add(scrollPane, BorderLayout.CENTER);
	}

	public DefaultListModel<String> getModeloUsuarios() {
		return modeloUsuarios;
	}

}
